package com.example.codeblitz.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.codeblitz.domain.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TaskDescViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : BaseViewModel() {
    private val _status: MutableState<String> = mutableStateOf("")
    val status: String get() = _status.value

    private val _title: MutableState<String> = mutableStateOf("")
    val title: String get() = _title.value

    private val _desc: MutableState<String> = mutableStateOf("")
    val desc: String get() = _desc.value

    private val _id: MutableState<String> = mutableStateOf("")
    val id: String get() = _id.value

    init {
        _status.value = savedStateHandle.get<String>("status")!!
        _title.value = savedStateHandle.get<String>("title")!!
        _desc.value = savedStateHandle.get<String>("desc")!!
        _id.value = savedStateHandle.get<String>("id")!!
    }

    fun navigateToMain() {
        _navigationStateFlow.value = Routes.Main
    }

    fun navigateToProfile() {
        _navigationStateFlow.value = Routes.Profile
    }

    fun navigateToEditor() {
        _navigationStateFlow.value = Routes.Editor
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
            }
        }
    }
}