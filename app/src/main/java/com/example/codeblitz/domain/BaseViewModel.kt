package com.example.codeblitz.domain

import androidx.lifecycle.ViewModel
import com.example.codeblitz.domain.navigation.Routes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel : ViewModel() {
    protected val _navigationStateFlow: MutableStateFlow<Routes?> = MutableStateFlow(null)
    val navigationStateFlow: StateFlow<Routes?> = _navigationStateFlow.asStateFlow()
}