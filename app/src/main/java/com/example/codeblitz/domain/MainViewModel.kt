package com.example.codeblitz.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.codeblitz.domain.navigation.Routes
import com.example.codeblitz.domain.utils.Constants
import com.example.codeblitz.model.Days
import com.example.codeblitz.model.Tasks
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {
    private val _tasks: MutableState<List<Tasks>> = mutableStateOf(listOf())
    val tasks: List<Tasks> get() = _tasks.value

    fun navigateToProfile() {
        _navigationStateFlow.value = Routes.Profile
    }

    init {
        viewModelScope.launch {
            val day: Days? = Constants.supabase.from("days").select() {
                filter {
                    Days::day_date eq Date()
                }
            }.decodeSingleOrNull()
            if (day != null) {
                _tasks.value = Constants.supabase.from("tasks").select() {
                    filter {
                        Tasks::day_id eq day.id
                    }
                }.decodeList()
            }
        }
    }
}