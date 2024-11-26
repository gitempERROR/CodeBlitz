package com.example.codeblitz.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.codeblitz.domain.navigation.Routes
import com.example.codeblitz.domain.utils.Constants
import com.example.codeblitz.domain.utils.CurrentUser
import com.example.codeblitz.model.Days
import com.example.codeblitz.model.DaysInsert
import com.example.codeblitz.model.TaskSolutions
import com.example.codeblitz.model.Tasks
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDate
import java.time.LocalDate

import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {
    private val _tasks: MutableState<MutableList<Tasks>> = mutableStateOf(mutableListOf())
    val tasks: List<Tasks> get() = _tasks.value.toList()

    fun navigateToProfile() {
        _navigationStateFlow.value = Routes.Profile
    }

    init {
        viewModelScope.launch {
            val tomorrow = LocalDate.now().plusDays(1)

            val date = if (CurrentUser.isAdmin) tomorrow.toKotlinLocalDate() else Date()

            var day: Days? = Constants.supabase.from("days").select() {
                filter {
                    Days::day_date eq date
                }
            }.decodeSingleOrNull()

            if (day == null) {
                if (CurrentUser.isAdmin) {
                    val nextDay = DaysInsert(
                        day_date = tomorrow.toKotlinLocalDate(),
                        0
                    )
                    Constants.supabase.from("days").insert(
                        nextDay
                    )
                    day = Constants.supabase.from("days").select() {
                        filter {
                            Days::day_date eq tomorrow.toKotlinLocalDate()
                        }
                    }.decodeSingleOrNull()
                }
            }
            if (day != null) {
                _tasks.value = Constants.supabase.from("tasks").select() {
                    filter {
                        Tasks::day_id eq day.id
                    }
                }.decodeList<Tasks>().toMutableList()
            }
            if (!CurrentUser.isAdmin) {
                _tasks.value.forEachIndexed { id, element ->
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
                    val solution = Constants.supabase.from("task_solutions").select(
                        columns = columns
                    ) {
                        filter {
                            TaskSolutions::user_id eq CurrentUser.userData!!.id
                            TaskSolutions::task_id eq element.id
                        }
                    }.decodeSingleOrNull<TaskSolutions>()

                    if (solution == null) {
                        _tasks.value[id].task_status = "not started"
                    } else {
                        _tasks.value[id].task_status = solution.current_status.status_name
                    }
                }
            }
            val buf = _tasks.value
            _tasks.value = mutableListOf()
            _tasks.value = buf
        }
    }
}