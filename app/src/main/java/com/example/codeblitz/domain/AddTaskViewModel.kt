package com.example.codeblitz.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.codeblitz.domain.navigation.Routes
import com.example.codeblitz.domain.utils.Constants
import com.example.codeblitz.model.Days
import com.example.codeblitz.model.TasksInsert
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor() : BaseViewModel() {
    private val _taskDesc: MutableState<String> = mutableStateOf("")
    val taskDesk: String get() = _taskDesc.value

    fun setTaskDesc(neValue: String) {
        _taskDesc.value = neValue
    }

    fun navigateToMain() {
        _navigationStateFlow.value = Routes.Main
    }

    fun navigateToProfile() {
        _navigationStateFlow.value = Routes.Profile
    }

    fun addNewTask() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val tomorrow = LocalDate.now().plusDays(1)

                val day: Days? = Constants.supabase.from("days").select() {
                    filter {
                        Days::day_date eq tomorrow
                    }
                }.decodeSingleOrNull()

                val newTask = TasksInsert(
                    day_id = day!!.id,
                    task_description = _taskDesc.value
                )

                Constants.supabase.from("tasks").insert(
                    newTask
                )

                navigateToMain()
            }
        }
    }
}