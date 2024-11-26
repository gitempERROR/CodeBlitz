package com.example.codeblitz.domain

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.codeblitz.domain.navigation.Routes
import com.example.codeblitz.domain.utils.Constants
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
import android.util.Patterns.EMAIL_ADDRESS
import com.example.codeblitz.domain.utils.CurrentUser

@HiltViewModel
class RegisterViewModel @Inject constructor() : BaseViewModel() {
    private val _registerData: MutableState<LoginData> = mutableStateOf(LoginData("", ""))
    private val _confirmPass: MutableState<String> = mutableStateOf("")
    private val _passwordWeak = derivedStateOf { _registerData.value.password.length <= 6 }
    private val _emailValid = derivedStateOf { isValidEmail(_registerData.value.login) }
    private val _isButtonEnabled = derivedStateOf {
        _registerData.value.password == _confirmPass.value && !_passwordWeak.value && _emailValid.value
    }
    private var _isError = mutableStateOf(false)

    val registerData: LoginData get() = _registerData.value
    val confirmPass: String get() = _confirmPass.value
    val passwordWeak: Boolean get() = _passwordWeak.value
    val isButtonEnabled: Boolean get() = _isButtonEnabled.value
    val isError: Boolean get() = _isError.value
    val emailValid: Boolean get() = _emailValid.value

    private fun isValidEmail(email: String): Boolean {
        return EMAIL_ADDRESS.matcher(email).matches()
    }

    fun updateRegisterData(newRegisterData: LoginData) {
        _registerData.value = newRegisterData
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        _confirmPass.value = newConfirmPassword
    }

    fun navigateToLogin() {
        _navigationStateFlow.value = Routes.Login
    }

    fun navigateToMain() {
        _navigationStateFlow.value = Routes.Main
    }

    fun register() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    Constants.supabase.auth.signUpWith(Email) {
                        email = registerData.login
                        password = registerData.password
                    }
                    val role: UserRoles

                    try {
                        role = Constants.supabase.from("user_roles")
                            .select() {
                                filter {
                                    UserRoles::role_name eq "user"
                                }
                            }.decodeSingle()
                        val userId: String = Constants.supabase.auth.currentUserOrNull()!!.id

                        val newUser = UserData(
                            userId,
                            firstname = "",
                            surname = "",
                            nickname = "",
                            role_id = role.id
                        )

                        Constants.supabase.from("user_data").insert(
                            newUser
                        )

                        val newUserSettings = mapOf("user_id" to userId)

                        Constants.supabase.from("user_settings").insert(newUserSettings)

                        getUserData()
                        navigateToMain()
                        setTheme()
                    }
                    catch (e: Exception) {
                        Log.e("register", "$e")
                        _isError.value = true
                    }
                }
            }
    }

    private suspend fun getUserData(){
        try {
            val userId = Constants.supabase.auth.currentUserOrNull()!!.id
            val userData: UserData = Constants.supabase.from("user_data").select {
                filter {
                    UserData::id eq userId
                }
            }.decodeSingle()
            CurrentUser.setUserData(userData)
        }
        catch (e: Exception) {
            Log.e("supabase", "getUserData: $e")
        }
    }
}