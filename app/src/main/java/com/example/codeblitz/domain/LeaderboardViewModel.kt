package com.example.codeblitz.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.codeblitz.domain.navigation.Routes
import com.example.codeblitz.domain.utils.Constants
import com.example.codeblitz.domain.utils.CurrentUser
import com.example.codeblitz.model.Days
import com.example.codeblitz.model.Languages
import com.example.codeblitz.model.TaskSolutions
import com.example.codeblitz.model.Tasks
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class LeaderboardViewModel @Inject constructor() : BaseViewModel() {
    val optionsTask: MutableList<String> = mutableListOf("Задание 1", "Задание 2")
    val selectedOptionTask: MutableState<String> = mutableStateOf("Задание 1")

    private var days: List<Days> = listOf()
    private var languages: List<Languages> = listOf()

    val dayTask = mapOf(
        "Задание 1" to 1,
        "Задание 2" to 2
    )

    val solutionsList: MutableState<MutableList<TaskSolutions>> = mutableStateOf(mutableListOf())

    val optionsDate: MutableList<String> = mutableListOf()
    val selectedOptionDate: MutableState<String> = mutableStateOf("")

    val optionsLang: MutableList<String> = mutableListOf()
    val selectedOptionLang: MutableState<String> = mutableStateOf("")

    fun navigateToProfile() {
        _navigationStateFlow.value = Routes.Profile
    }

    fun navigateToMain() {
        _navigationStateFlow.value = Routes.Main
    }

    private fun setFilters() {
        days.forEach { item ->
            optionsDate.add(item.day_date.toString())
        }
        languages.forEach { item ->
            optionsLang.add(item.language_name)
        }

        selectedOptionDate.value = optionsDate[0]
        selectedOptionLang.value = optionsLang[0]
    }

    fun updateFilters() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                updateTasks()
            }
        }
    }

    private suspend fun updateTasks() {
        val columns = Columns.raw(
            """
                id,
                user_id (
                  id,
                  firstname,
                  surname,
                  nickname,
                  role_id
                ),
                task_id,
                language_id (
                  id,
                  language_name
                ),
                code,
                current_status (
                  id,
                  status_name
                ),
                start_time,
                end_time
            """.trimIndent()
        )

        var dayFilter: Days? = days.find { it.day_date.toString() == selectedOptionDate.value }
        val langFilter: Languages? = languages.find { it.language_name == selectedOptionLang.value }

        if (CurrentUser.isAdmin) {
            dayFilter = Constants.supabase.from("days").select() {
                filter {
                    Days::day_date eq Date()
                }
            }.decodeSingleOrNull()
        }

        val task: Tasks = Constants.supabase.from("tasks").select() {
            filter {
                if (dayFilter != null) {
                    Tasks::day_id eq dayFilter.id
                }
                Tasks::day_task_id eq dayTask[selectedOptionTask.value]
            }
        }.decodeSingle()

        val list = Constants.supabase.from("task_solutions").select(
            columns = columns
        ) {
            filter {
                TaskSolutions::task_id eq task.id
                TaskSolutions::language_id eq langFilter!!.id
            }
        }.decodeList<TaskSolutions>().toMutableList()

        val sortedList: MutableList<TaskSolutions> = mutableListOf()

        list.forEach { item ->
            val difference = item.end_time!!.toSecondOfDay() - item.start_time!!.toSecondOfDay()
            item.spent_time = round((difference / 60f) * 100) / 100f
            item.task_desc = task.task_description
            item.task_title = if (task.day_task_id == 1) "Задание 1" else "Задание 2"
            item.date = selectedOptionDate.value
            if (item.current_status.status_name == "approved"
                || (item.current_status.status_name == "solved" && CurrentUser.isAdmin)
            )
                sortedList.add(item)
        }

        sortedList.sortBy { it.spent_time }

        sortedList.forEachIndexed { id, _ ->
            sortedList[id].place = id + 1
        }

        solutionsList.value = sortedList
    }

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                days = Constants.supabase.from("days")
                    .select()
                    .decodeList()

                languages = Constants.supabase.from("languages")
                    .select()
                    .decodeList()
                setFilters()
                updateTasks()
            }
        }
    }
}