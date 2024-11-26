package com.example.codeblitz.domain

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.codeblitz.domain.navigation.Routes
import com.example.codeblitz.domain.utils.Constants
import com.example.codeblitz.domain.utils.CurrentUser
import com.example.codeblitz.domain.utils.currentTime
import com.example.codeblitz.model.Languages
import com.example.codeblitz.model.SolutionStatuses
import com.example.codeblitz.model.TaskSolutions
import com.example.codeblitz.model.TaskSolutionsInsert
import com.example.codeblitz.model.TaskSolutionsUpdate
import com.wakaztahir.codeeditor.highlight.model.CodeLang
import com.wakaztahir.codeeditor.highlight.prettify.PrettifyParser
import com.wakaztahir.codeeditor.highlight.theme.CodeThemeType
import com.wakaztahir.codeeditor.highlight.utils.parseCodeAsAnnotatedString
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalTime
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : BaseViewModel() {
    private val _status: MutableState<String> = mutableStateOf("")

    private val _popup: MutableState<Boolean> = mutableStateOf(false)
    val popup: Boolean get() = _popup.value

    private val _title: MutableState<String> = mutableStateOf("")
    val title: String get() = _title.value

    private val _desc: MutableState<String> = mutableStateOf("")
    val desc: String get() = _desc.value

    private val _id: MutableState<String> = mutableStateOf("")
    val id: String get() = _id.value

    private lateinit var startTime: LocalTime

    private val _languageList: MutableState<List<Languages>> = mutableStateOf(mutableListOf())
    private var _selectedLanguage: MutableState<Languages?> = mutableStateOf(null)

    private val _languagesStyles = mapOf(
        "Python" to CodeLang.Python,
        "GO" to CodeLang.Go,
        "C#" to CodeLang.CSharp,
        "JavaScript" to CodeLang.JavaScript,
        "C++" to CodeLang.CPP
    )

    private var _selectedLanguageStyle = _languagesStyles["Python"]

    private val parser = PrettifyParser()
    private var themeState by mutableStateOf(CodeThemeType.Monokai)
    val theme = themeState.theme()

    private var _textFieldValue = mutableStateOf(
            TextFieldValue(
                annotatedString = parseCodeAsAnnotatedString(
                    parser = parser,
                    theme = theme,
                    lang = _selectedLanguageStyle!!,
                    code = ""
                )
            )
        )
    val textFieldValue get() = _textFieldValue.value

    val options: MutableList<String> = mutableListOf()
    val selectedOption: MutableState<String> = mutableStateOf("Python")

    fun changeCode(newValue: TextFieldValue) {
        _textFieldValue.value = newValue.copy(
            annotatedString = parseCodeAsAnnotatedString(
                parser = parser,
                theme = theme,
                lang = _selectedLanguageStyle!!,
                code = newValue.text
            )
        )
    }

    fun switchPopup() {
        _popup.value = !_popup.value
    }

    fun setSelectedLanguage(languageName: String) {
        selectedOption.value = languageName
        _languageList.value.forEach { item ->
            if(item.language_name == languageName) {
                _selectedLanguage.value = item
                _selectedLanguageStyle = _languagesStyles[_selectedLanguage.value!!.language_name]
                changeCode(_textFieldValue.value)
            }
        }
    }

    fun endTask() {
        _status.value = "solved"
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val solutionStatus: SolutionStatuses = Constants.supabase.from("solution_statuses")
                        .select {
                            filter {
                                SolutionStatuses::status_name eq "solved"
                            }
                        }.decodeSingle()

                    val solution = TaskSolutionsInsert(
                        user_id = CurrentUser.userData!!.id,
                        task_id = _id.value.toInt(),
                        language_id = _selectedLanguage.value!!.id,
                        code = _textFieldValue.value.text,
                        current_status = solutionStatus.id,
                        start_time = startTime,
                        end_time = currentTime()
                    )

                    Constants.supabase.from("task_solutions").update(
                        solution
                    ) {
                        filter {
                            TaskSolutions::user_id eq CurrentUser.userData!!.id
                            TaskSolutions::task_id eq _id.value
                        }
                    }
                }
                catch (e: Exception){
                    Log.e("supabase", "endTask: $e")
                }
            }
        }
        _navigationStateFlow.value = Routes.Main
    }

    init {
        _status.value = savedStateHandle.get<String>("status")!!
        _title.value = savedStateHandle.get<String>("title")!!
        _desc.value = savedStateHandle.get<String>("desc")!!
        _id.value = savedStateHandle.get<String>("id")!!

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _languageList.value = Constants.supabase.from("languages")
                        .select().decodeList()
                    _selectedLanguage = mutableStateOf(_languageList.value[0])

                    _languageList.value.forEach { item ->
                        options.add(item.language_name)
                    }

                    try {
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
                        val solution: TaskSolutions = Constants.supabase.from("task_solutions")
                            .select(
                                columns = columns
                            ) {
                                filter {
                                    TaskSolutions::user_id eq CurrentUser.userData!!.id
                                    TaskSolutions::task_id eq _id.value
                                }
                            }.decodeSingle()
                        startTime = solution.start_time!!
                        setSelectedLanguage(solution.language_id.language_name)
                        changeCode(
                            _textFieldValue.value.copy(
                                annotatedString = parseCodeAsAnnotatedString(
                                    parser = parser,
                                    theme = theme,
                                    lang = _selectedLanguageStyle!!,
                                    code = solution.code
                                )
                            )
                        )
                    }
                    catch (e: Exception) {
                        Log.e("supabase", "get solution error $e")
                    }


                    while (true) {
                        if (_status.value == "solved") break
                        Thread.sleep(5000)
                        if (_status.value == "solved") break
                        val solution = TaskSolutionsUpdate(
                            _selectedLanguage.value!!.id,
                            _textFieldValue.value.text,
                            currentTime()
                        )
                        if (_status.value == "solved") break
                        Constants.supabase.from("task_solutions").update(
                            solution
                        ) {
                            filter {
                                TaskSolutions::user_id eq CurrentUser.userData!!.id
                                TaskSolutions::task_id eq _id.value
                            }
                        }
                    }
                }
                catch (e: Exception) {
                    Log.e("supabase", "language list error $e")
                }
            }
        }
    }
}