package com.example.codeblitz.domain

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

@HiltViewModel
class ProfileViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : BaseViewModel() {
    private val _backRoute: MutableState<String> = mutableStateOf("")
    val backRoute: String get() = _backRoute.value

    init {
        _backRoute.value = savedStateHandle.get<String>("backRoute")!!
    }

    private val _userData: MutableState<UserData> = mutableStateOf(CurrentUser.userData!!)
    val userData: UserData get() = _userData.value

    fun navigateBack() {
        _navigationStateFlow.value = Routes.Profile
    }

    fun setUserData(newUserData: UserData) {
        _userData.value = newUserData
    }

    fun refreshFields() {
        _userData.value = CurrentUser.userData!!
    }

    fun exit() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Constants.supabase.auth.signOut()
            }
        }
        _navigationStateFlow.value = Routes.Login
    }

    fun updateUserData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                CurrentUser.setUserData(_userData.value)
                Constants.supabase.from("user_data").update(
                    CurrentUser.userData!!
                ) {
                    filter {
                        UserData::id eq CurrentUser.userData!!.id
                    }
                }
            }
        }
    }
}