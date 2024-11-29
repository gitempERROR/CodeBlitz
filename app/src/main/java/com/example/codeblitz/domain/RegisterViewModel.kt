package com.example.codeblitz.domain

import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

//ViewModel для страницы регистрации
@HiltViewModel
class RegisterViewModel @Inject constructor() : BaseViewModel() {
    //Поле данных для регистрации
    private val _registerData: MutableState<LoginData> = mutableStateOf(LoginData("", ""))
    //Поле повторенного пароля
    private val _confirmPass: MutableState<String> = mutableStateOf("")
    //Поле информации о слабом пароле и проверка пароля
    private val _passwordWeak = derivedStateOf { _registerData.value.password.length <= 6 }
    //Поле информации о корректной почте и проверка почты
    private val _emailValid = derivedStateOf { isValidEmail(_registerData.value.login) }
    //Поле определяющее активность кнопки
    private val _isButtonEnabled = derivedStateOf {
        _registerData.value.password == _confirmPass.value && !_passwordWeak.value && _emailValid.value
    }
    //Поле информации об ошибке
    private var _isError = mutableStateOf(false)

    //Свойства для доступа к данным
    val registerData: LoginData get() = _registerData.value
    val confirmPass: String get() = _confirmPass.value
    val passwordWeak: Boolean get() = _passwordWeak.value
    val isButtonEnabled: Boolean get() = _isButtonEnabled.value
    val isError: Boolean get() = _isError.value
    val emailValid: Boolean get() = _emailValid.value

    //Проверка почты
    fun isValidEmail(email: String): Boolean {
        return EMAIL_ADDRESS.matcher(email).matches()
    }

    //Обновление данных текстового поля
    fun updateRegisterData(newRegisterData: LoginData) {
        _registerData.value = newRegisterData
    }

    //Обновление данных текстового поля
    fun updateConfirmPassword(newConfirmPassword: String) {
        _confirmPass.value = newConfirmPassword
    }

    //Навигация на страницу входа
    fun navigateToLogin() {
        _navigationStateFlow.value = Routes.Login
    }

    //Навигация на главную
    fun navigateToMain() {
        _navigationStateFlow.value = Routes.Main
    }

    //Регистрация
    fun register() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                //Регистрация в supabase
                Constants.supabase.auth.signUpWith(Email) {
                    email = registerData.login
                    password = registerData.password
                }
                val role: UserRoles

                try {
                    //Получение ID роли
                    role = Constants.supabase.from("user_roles")
                        .select() {
                            filter {
                                UserRoles::role_name eq "user"
                            }
                        }.decodeSingle()
                    val userId: String = Constants.supabase.auth.currentUserOrNull()!!.id

                    //Составление новых данных о пользователе
                    val newUser = UserData(
                        userId,
                        firstname = "",
                        surname = "",
                        nickname = "",
                        role_id = role.id
                    )

                    //Вставка новых данных о пользователе
                    Constants.supabase.from("user_data").insert(
                        newUser
                    )

                    //Создание записи о пользователе в таблице настроек
                    val newUserSettings = mapOf("user_id" to userId)

                    Constants.supabase.from("user_settings").insert(newUserSettings)

                    //Обновление данных о пользователе в приложении
                    getUserData()
                    //Переход на главную
                    navigateToMain()
                    //Установка стандартной темы
                    setTheme()
                } catch (e: Exception) {
                    Log.e("register", "$e")
                    _isError.value = true
                }
            }
        }
    }

    //Функция обновления данных о пользователе в приложении
    private suspend fun getUserData() {
        try {
            //Получение ID
            val userId = Constants.supabase.auth.currentUserOrNull()!!.id
            //Получение данных
            val userData: UserData = Constants.supabase.from("user_data").select {
                filter {
                    UserData::id eq userId
                }
            }.decodeSingle()
            //Получение роли
            val roleAdmin: UserRoles = Constants.supabase.from("user_roles")
                .select() {
                    filter {
                        UserRoles::role_name eq "admin"
                    }
                }.decodeSingle()
            //Запись данных
            CurrentUser.setUserData(userData)
            //Установка роли
            CurrentUser.setRole(roleAdmin.id == userData.role_id)
        } catch (e: Exception) {
            Log.e("supabase", "getUserData: $e")
        }
    }
}