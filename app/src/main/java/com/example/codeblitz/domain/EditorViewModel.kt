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

//ViewModel редактора
@HiltViewModel
class EditorViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : BaseViewModel() {
    //Статус решения
    private val _status: MutableState<String> = mutableStateOf("")

    //Отображение всплывающего окна
    private val _popup: MutableState<Boolean> = mutableStateOf(false)
    val popup: Boolean get() = _popup.value

    //Отображение заголовка
    private val _title: MutableState<String> = mutableStateOf("")
    val title: String get() = _title.value

    //Отображение описания
    private val _desc: MutableState<String> = mutableStateOf("")
    val desc: String get() = _desc.value

    //Id решения
    private val _id: MutableState<String> = mutableStateOf("")
    val id: String get() = _id.value

    //Время начала решения
    private lateinit var startTime: LocalTime

    //Список языков из БД
    private val _languageList: MutableState<List<Languages>> = mutableStateOf(mutableListOf())
    //Выбранный язык из БД
    private var _selectedLanguage: MutableState<Languages?> = mutableStateOf(null)

    //Словарь языков к стилям подсветки
    private val _languagesStyles = mapOf(
        "Python" to CodeLang.Python,
        "GO" to CodeLang.Go,
        "C#" to CodeLang.CSharp,
        "JavaScript" to CodeLang.JavaScript,
        "C++" to CodeLang.CPP
    )

    //Выбранный стиль подсветки
    private var _selectedLanguageStyle = _languagesStyles["Python"]

    //Объекты для аннотации строки
    private val parser = PrettifyParser()
    private var themeState by mutableStateOf(CodeThemeType.Monokai)
    val theme = themeState.theme()

    //Значение текстового поля
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

    //Список языков для выпадающего списка
    val options: MutableList<String> = mutableListOf()
    val selectedOption: MutableState<String> = mutableStateOf("Python")

    //Создание новой аннотированной строки при написании кода
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

    //Переключение всплывающего окна
    fun switchPopup() {
        _popup.value = !_popup.value
    }

    //Установка другого языка
    fun setSelectedLanguage(languageName: String) {
        selectedOption.value = languageName
        _languageList.value.forEach { item ->
            if (item.language_name == languageName) {
                _selectedLanguage.value = item
                _selectedLanguageStyle = _languagesStyles[_selectedLanguage.value!!.language_name]
                changeCode(_textFieldValue.value)
            }
        }
    }

    //Завершение задачи
    fun endTask() {
        //Статус - решенная
        _status.value = "solved"
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    //Получение id решения
                    val solutionStatus: SolutionStatuses =
                        Constants.supabase.from("solution_statuses")
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

                    //Обновление решения в БД
                    Constants.supabase.from("task_solutions").update(
                        solution
                    ) {
                        filter {
                            TaskSolutions::user_id eq CurrentUser.userData!!.id
                            TaskSolutions::task_id eq _id.value
                        }
                    }
                } catch (e: Exception) {
                    Log.e("supabase", "endTask: $e")
                }
            }
        }
        //Переход на главную
        _navigationStateFlow.value = Routes.Main
    }

    init {
        //Получение аргументов навигации
        _status.value = savedStateHandle.get<String>("status")!!
        _title.value = savedStateHandle.get<String>("title")!!
        _desc.value = savedStateHandle.get<String>("desc")!!
        _id.value = savedStateHandle.get<String>("id")!!

        //Получение списка языков и id статуса решения
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
                        //id статуса решения
                        val solution: TaskSolutions = Constants.supabase.from("task_solutions")
                            .select(
                                columns = columns
                            ) {
                                filter {
                                    TaskSolutions::user_id eq CurrentUser.userData!!.id
                                    TaskSolutions::task_id eq _id.value
                                }
                            }.decodeSingle()
                        //Ниже идет получение данных уже начатого решения
                        //Получение времени начала
                        startTime = solution.start_time!!
                        //Установка языка решения
                        setSelectedLanguage(solution.language_id.language_name)
                        //Установка кода из решения
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
                    } catch (e: Exception) {
                        Log.e("supabase", "get solution error $e")
                    }

                    //Постоянное обновление решения в БД на случай закрытия
                    while (true) {
                        //Выход из цикла при завершении решения
                        if (_status.value == "solved") break
                        Thread.sleep(5000)
                        if (_status.value == "solved") break
                        val solution = TaskSolutionsUpdate(
                            _selectedLanguage.value!!.id,
                            _textFieldValue.value.text,
                            currentTime()
                        )
                        if (_status.value == "solved") break
                        try {
                            Constants.supabase.from("task_solutions").update(
                                solution
                            ) {
                                filter {
                                    TaskSolutions::user_id eq CurrentUser.userData!!.id
                                    TaskSolutions::task_id eq _id.value
                                }
                            }
                        }
                        catch(e: Exception) {
                            Log.e("supabase", "error updating solution $e")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("supabase", "language list error $e")
                }
            }
        }
    }
}