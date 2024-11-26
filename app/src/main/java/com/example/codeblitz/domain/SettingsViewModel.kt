package com.example.codeblitz.domain

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

@HiltViewModel
class SettingsViewModel @Inject constructor() : BaseViewModel() {
    private val _themeList: MutableState<MutableList<Themes>> = mutableStateOf(mutableListOf())
    private var _selectedTheme: MutableState<Themes?> = mutableStateOf(null)

    val options: MutableList<ColorScheme> = mutableListOf()
    val selectedOption: MutableState<ColorScheme?> = mutableStateOf(null)

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

    fun navigateToProfile() {
        _navigationStateFlow.value = Routes.Profile
    }

    fun navigateToMain() {
        _navigationStateFlow.value = Routes.Main
    }

    fun changeTheme(newTheme: ColorScheme) {
        CodeBlitzTheme.changeTheme(newTheme)

        _selectedTheme.value = _themeList.value.find { it.theme_name == newTheme.name }

        viewModelScope.launch {
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
    }

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
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
                _selectedTheme.value = if (settings!!.selected_theme != null) _themeList.value.find { it.id == settings.selected_theme } else _themeList.value[0]
                _selectedTheme.value?.let { setOptions(it) }
            }
        }
    }
}