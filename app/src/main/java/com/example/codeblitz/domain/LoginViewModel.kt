package com.example.codeblitz.domain

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.codeblitz.domain.navigation.Routes
import com.example.codeblitz.domain.utils.Constants
import com.example.codeblitz.domain.utils.CurrentUser
import com.example.codeblitz.model.LoginData
import com.example.codeblitz.model.UserData
import com.example.codeblitz.model.UserRoles
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch
import javax.inject.Inject

//ViewModel страницы входа
@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {
    //Данные для входа
    private val _loginData: MutableState<LoginData> = mutableStateOf(LoginData("", ""))
    //Доступность кнопки
    private val _isButtonEnabled =
        derivedStateOf { _loginData.value.login.isNotBlank() && _loginData.value.password.isNotBlank() }
    //Отображение ошибки
    private var _isError = mutableStateOf(false)

    //Свойства для получения данных
    val loginData: LoginData get() = _loginData.value
    val isButtonEnabled: Boolean get() = _isButtonEnabled.value
    val isError: Boolean get() = _isError.value

    //Обновление данных для входа
    fun updateLoginData(newLoginData: LoginData) {
        _loginData.value = newLoginData
    }

    //Переход к регистрации
    fun navigateToRegister() {
        _navigationStateFlow.value = Routes.Register
    }

    //Функция входа
    fun login() {
        viewModelScope.launch {
            try {
                Constants.supabase.auth.signInWith(Email) {
                    email = loginData.login
                    password = loginData.password
                }
                //Получение данных пользователя
                getUserData()
                //Переход на главную
                _navigationStateFlow.value = Routes.Main
                //Установка темы
                setTheme()
            } catch (e: Exception) {
                Log.e("auth", "$e")
                _isError.value = true
            }
        }
    }

    //Функция получения данных о пользователе (подробнее комментарии в RegisterViewModel)
    private suspend fun getUserData() {
        try {
            val userId = Constants.supabase.auth.currentUserOrNull()!!.id
            val userData: UserData = Constants.supabase.from("user_data").select {
                filter {
                    UserData::id eq userId
                }
            }.decodeSingle()
            val roleAdmin: UserRoles = Constants.supabase.from("user_roles")
                .select() {
                    filter {
                        UserRoles::role_name eq "admin"
                    }
                }.decodeSingle()
            CurrentUser.setUserData(userData)
            CurrentUser.setRole(roleAdmin.id == userData.role_id)
        } catch (e: Exception) {
            Log.e("supabase", "getUserData: $e")
        }
    }
}