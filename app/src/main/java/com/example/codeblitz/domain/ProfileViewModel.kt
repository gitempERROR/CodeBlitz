package com.example.codeblitz.domain

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.codeblitz.domain.navigation.Routes
import com.example.codeblitz.domain.utils.Constants
import com.example.codeblitz.domain.utils.CurrentUser
import com.example.codeblitz.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

//ViewModel профиля
@HiltViewModel
class ProfileViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : BaseViewModel() {
    //Планировалось реализовать возможность переходить на предыдущую страницу, но из-за аргументов навигации это было решено оставить
    private val _backRoute: MutableState<String> = mutableStateOf("")
    val backRoute: String get() = _backRoute.value

    init {
        _backRoute.value = savedStateHandle.get<String>("backRoute")!!
    }

    //Данные о пользователе
    private val _userData: MutableState<UserData> = mutableStateOf(CurrentUser.userData!!)
    val userData: UserData get() = _userData.value

    //Навигация на главную
    fun navigateBack() {
        _navigationStateFlow.value = Routes.Profile
    }

    //Обновление данных пользователя
    fun setUserData(newUserData: UserData) {
        _userData.value = newUserData
    }

    //Обновление отображаемой информации в полях
    fun refreshFields() {
        _userData.value = CurrentUser.userData!!
    }

    //Выход из аккаунта
    fun exit() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Constants.supabase.auth.signOut()
            }
        }
        _navigationStateFlow.value = Routes.Login
    }

    //Вставка новых данных в БД
    fun updateUserData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    CurrentUser.setUserData(_userData.value)
                    Constants.supabase.from("user_data").update(
                        CurrentUser.userData!!
                    ) {
                        filter {
                            UserData::id eq CurrentUser.userData!!.id
                        }
                    }
                } catch (e: Exception) {
                    Log.e("supabase", "error updating user data$e")
                }
            }
        }
    }
}