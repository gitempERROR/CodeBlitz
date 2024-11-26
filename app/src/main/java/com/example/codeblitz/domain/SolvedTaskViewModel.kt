package com.example.codeblitz.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.codeblitz.domain.navigation.Routes
import com.example.codeblitz.domain.utils.Constants
import com.example.codeblitz.model.SolutionStatuses
import com.example.codeblitz.model.TaskSolutions
import com.wakaztahir.codeeditor.highlight.model.CodeLang
import com.wakaztahir.codeeditor.highlight.prettify.PrettifyParser
import com.wakaztahir.codeeditor.highlight.theme.CodeThemeType
import com.wakaztahir.codeeditor.highlight.utils.parseCodeAsAnnotatedString
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SolvedTaskViewModel @Inject constructor(savedStateHandle: SavedStateHandle) :
    BaseViewModel() {
    private val _title: MutableState<String> = mutableStateOf("")
    private val _nickname: MutableState<String> = mutableStateOf("")
    private val _date: MutableState<String> = mutableStateOf("")
    private val _lang: MutableState<String> = mutableStateOf("")
    private val _time: MutableState<String> = mutableStateOf("")
    private val _code: MutableState<AnnotatedString> = mutableStateOf(AnnotatedString(""))
    private val _desc: MutableState<String> = mutableStateOf("")
    private var _id: Int = 0

    private val _languagesStyles = mapOf(
        "Python" to CodeLang.Python,
        "GO" to CodeLang.Go,
        "C#" to CodeLang.CSharp,
        "JavaScript" to CodeLang.JavaScript,
        "C++" to CodeLang.CPP
    )

    val parser = PrettifyParser()
    val themeState by mutableStateOf(CodeThemeType.Monokai)
    val theme = themeState.theme()

    val title: String get() = _title.value
    val nickname: String get() = _nickname.value
    val date: String get() = _date.value
    val lang: String get() = _lang.value
    val time: String get() = _time.value
    val code: AnnotatedString get() = _code.value
    val desc: String get() = _desc.value

    fun navigateToLeaderBoard() {
        _navigationStateFlow.value = Routes.Leaderboard
    }

    fun approveSolution() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val statusId = Constants.supabase.from("solution_statuses")
                    .select {
                        filter {
                            SolutionStatuses::status_name eq "approved"
                        }
                    }.decodeSingle<SolutionStatuses>()
                val newStatus = mapOf(
                    "current_status" to statusId.id
                )
                Constants.supabase.from("task_solutions").update(
                    newStatus
                ) {
                    filter {
                        TaskSolutions::id eq _id
                    }
                }
                navigateToLeaderBoard()
            }
        }
    }

    fun denySolution() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val statusId = Constants.supabase.from("solution_statuses")
                    .select {
                        filter {
                            SolutionStatuses::status_name eq "denied"
                        }
                    }.decodeSingle<SolutionStatuses>()
                val newStatus = mapOf(
                    "current_status" to statusId.id
                )
                Constants.supabase.from("task_solutions").update(
                    newStatus
                ) {
                    filter {
                        TaskSolutions::id eq _id
                    }
                }
                navigateToLeaderBoard()
            }
        }
    }

    init {
        _title.value = savedStateHandle.get<String>("title")!!
        _nickname.value = savedStateHandle.get<String>("nickname")!!
        _date.value = savedStateHandle.get<String>("date")!!
        _lang.value = savedStateHandle.get<String>("lang")!!
        _time.value = savedStateHandle.get<String>("time")!!
        _desc.value = savedStateHandle.get<String>("desc")!!
        _id = savedStateHandle.get<String>("id")!!.toInt()
        val code = savedStateHandle.get<String>("code")!!

        _code.value = parseCodeAsAnnotatedString(
            parser = parser,
            theme = theme,
            lang = _languagesStyles[_lang.value]!!,
            code = code
        )
    }
}