package com.example.codeblitz.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.SavedStateHandle
import com.wakaztahir.codeeditor.highlight.model.CodeLang
import com.wakaztahir.codeeditor.highlight.prettify.PrettifyParser
import com.wakaztahir.codeeditor.highlight.theme.CodeThemeType
import com.wakaztahir.codeeditor.highlight.utils.parseCodeAsAnnotatedString
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SolvedTaskViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : BaseViewModel() {
    private val _title: MutableState<String> = mutableStateOf("")
    private val _nickname: MutableState<String> = mutableStateOf("")
    private val _date: MutableState<String> = mutableStateOf("")
    private val _lang: MutableState<String> = mutableStateOf("")
    private val _time: MutableState<String> = mutableStateOf("")
    private val _code: MutableState<AnnotatedString> = mutableStateOf(AnnotatedString(""))
    private val _desc: MutableState<String> = mutableStateOf("")

    private val _languagesStyles = mapOf(
        "Python" to CodeLang.Python,
        "GO" to CodeLang.Go,
        "C#" to CodeLang.CSharp,
        "JavaScript" to CodeLang.JavaScript,
        "C++" to CodeLang.CPP
    )

    val language = CodeLang.Python

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

    init {
        _title.value = savedStateHandle.get<String>("title")!!
        _nickname.value = savedStateHandle.get<String>("nickname")!!
        _date.value = savedStateHandle.get<String>("date")!!
        _lang.value = savedStateHandle.get<String>("lang")!!
        _time.value = savedStateHandle.get<String>("time")!!
        _desc.value = savedStateHandle.get<String>("desc")!!
        val code = savedStateHandle.get<String>("code")!!

        _code.value = parseCodeAsAnnotatedString(
            parser = parser,
            theme = theme,
            lang = _languagesStyles[_lang.value]!!,
            code = code
        )
    }
}