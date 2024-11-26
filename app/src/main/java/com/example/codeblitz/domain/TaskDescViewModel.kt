package com.example.codeblitz.domain

import android.icu.util.Calendar
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.codeblitz.domain.navigation.Routes
import com.example.codeblitz.domain.utils.Constants
import com.example.codeblitz.domain.utils.CurrentUser
import com.example.codeblitz.domain.utils.currentTime
import com.example.codeblitz.model.Languages
import com.example.codeblitz.model.SolutionStatuses
import com.example.codeblitz.model.TaskSolutionsInsert
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TaskDescViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : BaseViewModel() {
    private val _status: MutableState<String> = mutableStateOf("")
    val status: String get() = _status.value

    private val _title: MutableState<String> = mutableStateOf("")
    val title: String get() = _title.value

    private val _desc: MutableState<String> = mutableStateOf("")
    val desc: String get() = _desc.value

    private val _id: MutableState<String> = mutableStateOf("")
    val id: String get() = _id.value

    init {
        _status.value = savedStateHandle.get<String>("status")!!
        _title.value = savedStateHandle.get<String>("title")!!
        _desc.value = savedStateHandle.get<String>("desc")!!
        _id.value = savedStateHandle.get<String>("id")!!
    }

    fun navigateToMain() {
        _navigationStateFlow.value = Routes.Main
    }

    fun navigateToProfile() {
        _navigationStateFlow.value = Routes.Profile
    }

    fun navigateToEditor() {
        _navigationStateFlow.value = Routes.Editor
        if (status != "not started") return
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val currentTime = currentTime()

                val firstLanguage: Languages = Constants.supabase.from("languages")
                    .select().decodeSingle()
                val solutionStatus: SolutionStatuses = Constants.supabase.from("solution_statuses")
                    .select {
                        filter {
                            SolutionStatuses::status_name eq "started"
                        }
                    }.decodeSingle()

                val solution = TaskSolutionsInsert(
                    CurrentUser.userData!!.id,
                    _id.value.toInt(),
                    firstLanguage.id,
                    "",
                    solutionStatus.id,
                    currentTime,
                    null
                )
                Constants.supabase.from("task_solutions").insert(
                    solution
                )
            }
        }
    }
}