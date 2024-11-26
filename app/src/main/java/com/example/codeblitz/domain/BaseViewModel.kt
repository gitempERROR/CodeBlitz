package com.example.codeblitz.domain

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codeblitz.domain.navigation.Routes
import com.example.codeblitz.domain.utils.Constants
import com.example.codeblitz.domain.utils.CurrentUser
import com.example.codeblitz.model.ColorScheme
import com.example.codeblitz.model.SettingsData
import com.example.codeblitz.model.Themes
import com.example.codeblitz.view.ui.theme.CodeBlitzTheme
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel : ViewModel() {
    protected val _navigationStateFlow: MutableStateFlow<Routes?> = MutableStateFlow(null)
    val navigationStateFlow: StateFlow<Routes?> = _navigationStateFlow.asStateFlow()

    fun setTheme() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val themes = Constants.supabase.from("themes")
                    .select {
                        order(column = "id", order = Order.ASCENDING)
                    }
                    .decodeList<Themes>()
                val settings: SettingsData? = Constants.supabase.from("user_settings")
                    .select {
                        filter {
                            SettingsData::user_id eq CurrentUser.userData!!.id
                        }
                    }.decodeSingleOrNull()
                val selectedTheme =
                    if (settings!!.selected_theme != null) themes.find { it.id == settings.selected_theme } else themes[0]

                val selectedColorScheme = ColorScheme(
                    name = selectedTheme!!.theme_name,
                    background = Color(selectedTheme.color_1.toColorInt()),
                    primary = Color(selectedTheme.color_2.toColorInt()),
                    secondaryContainer = Color(selectedTheme.color_3.toColorInt()),
                    tertiary = Color(selectedTheme.color_4.toColorInt()),
                    secondary = Color(selectedTheme.color_5.toColorInt()),
                    onBackground = Color(selectedTheme.color_6.toColorInt())
                )
                CodeBlitzTheme.changeTheme(selectedColorScheme)
            }
        }
    }
}