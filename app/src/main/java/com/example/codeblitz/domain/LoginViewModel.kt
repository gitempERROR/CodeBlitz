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
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {
    private val _loginData: MutableState<LoginData> = mutableStateOf(LoginData("", ""))
    private val _isButtonEnabled = derivedStateOf { _loginData.value.login.isNotBlank() && _loginData.value.password.isNotBlank() }
    private var _isError = mutableStateOf(false)

    val loginData: LoginData get() = _loginData.value
    val isButtonEnabled: Boolean get() = _isButtonEnabled.value
    val isError: Boolean get() = _isError.value

    fun updateLoginData(newLoginData: LoginData) {
        _loginData.value = newLoginData
    }

    fun navigateToRegister() {
        _navigationStateFlow.value = Routes.Register
    }

    fun login(){
        viewModelScope.launch {
            try {
                Constants.supabase.auth.signInWith(Email) {
                    email = loginData.login
                    password = loginData.password
                }
                getUserData()
                _navigationStateFlow.value = Routes.Main
            }
            catch (e: Exception) {
                Log.e("auth", "$e")
                _isError.value = true
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