package com.example.codeblitz.domain

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewModelScope
import com.example.codeblitz.domain.navigation.Routes
import com.example.codeblitz.domain.utils.Constants
import com.example.codeblitz.domain.utils.CurrentUser
import com.example.codeblitz.model.ColorScheme
import com.example.codeblitz.model.SettingsData
import com.example.codeblitz.model.Themes
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

//ViewModel настроек
@HiltViewModel
class SettingsViewModel @Inject constructor() : BaseViewModel() {
    //Поле списка тем
    private val _themeList: MutableState<MutableList<Themes>> = mutableStateOf(mutableListOf())
    //Поле выбранной темы
    private var _selectedTheme: MutableState<Themes?> = mutableStateOf(null)

    //Поле вариантов для выпадающего списка
    val options: MutableList<ColorScheme> = mutableListOf()
    //Выбранный вариант выпадающего списка
    val selectedOption: MutableState<ColorScheme?> = mutableStateOf(null)

    //Преобразование текстовых значений цветов к цветам приложения
    private fun setOptions(
        selectedTheme: Themes
    ) {
        _themeList.value.forEach { item ->
            options.add(
                ColorScheme(
                    name = item.theme_name,
                    background = Color(item.color_1.toColorInt()),
                    primary = Color(item.color_2.toColorInt()),
                    secondaryContainer = Color(item.color_3.toColorInt()),
                    tertiary = Color(item.color_4.toColorInt()),
                    secondary = Color(item.color_5.toColorInt()),
                    onBackground = Color(item.color_6.toColorInt())
                )
            )
        }
        selectedOption.value = options.find { it.name == selectedTheme.theme_name }
    }

    //Навигация в профиль
    fun navigateToProfile() {
        _navigationStateFlow.value = Routes.Profile
    }

    //Навигация на главную
    fun navigateToMain() {
        _navigationStateFlow.value = Routes.Main
    }

    //Смена темы
    fun changeTheme(newTheme: ColorScheme) {
        //Смена темы в приложении
        CodeBlitzTheme.changeTheme(newTheme)

        //Смена выбранной темы
        _selectedTheme.value = _themeList.value.find { it.theme_name == newTheme.name }

        //Запись информации о смене темы в БД
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val userSettings = SettingsData(
                        selected_theme = _selectedTheme.value!!.id,
                        user_id = CurrentUser.userData!!.id
                    )
                    Constants.supabase.from("user_settings").update(
                        userSettings
                    ) {
                        filter {
                            SettingsData::user_id eq CurrentUser.userData!!.id
                        }
                    }
                }
            }
            catch (e: Exception) {
                Log.e("supabase", "error inserting theme: $e")
            }
        }
    }

    init {
        //Запись Списка тем и выбранной темы
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _themeList.value = Constants.supabase.from("themes")
                        .select {
                            order(column = "id", order = Order.ASCENDING)
                        }
                        .decodeList<Themes>()
                        .toMutableList()
                    val settings: SettingsData? = Constants.supabase.from("user_settings")
                        .select {
                            filter {
                                SettingsData::user_id eq CurrentUser.userData!!.id
                            }
                        }.decodeSingleOrNull()
                    _selectedTheme.value =
                            //Установка темы по умолчанию
                        if (settings!!.selected_theme != null) _themeList.value.find { it.id == settings.selected_theme } else _themeList.value[0]
                    _selectedTheme.value?.let { setOptions(it) }
                }
                catch (e: Exception) {
                    Log.e("supabase", "error setting theme: $e")
                }
            }
        }
    }
}