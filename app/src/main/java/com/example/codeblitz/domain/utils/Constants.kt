package com.example.codeblitz.domain.utils


import android.util.DisplayMetrics
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.example.codeblitz.model.UserData
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import kotlin.math.sqrt

object ScreenDimensions {

    private lateinit var displayMetrics: DisplayMetrics

    fun init(displayMetrics: DisplayMetrics) {
        this.displayMetrics = displayMetrics
    }

    fun getScreenRatio(): Double {
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels
        val referenceArea = 2769 * 1344
        val screenArea = width * height
        return sqrt(screenArea.toDouble() / referenceArea)
    }
}

object Constants {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://eiwtxcsrwsrmscoytegf.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVpd3R4Y3Nyd3NybXNjb3l0ZWdmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Mjk3MDkxMjYsImV4cCI6MjA0NTI4NTEyNn0.71xBhsfi3ckDbjBdG9YqWPvrhb5w5YqfLBKhQWVB3cw"
    ) {
        install(Auth)
        install(Postgrest)
        install(Realtime)
    }
}

object CurrentUser {

    private var _userData: MutableState<UserData?> = mutableStateOf(null)
    val userData by _userData

    private var _isAdmin: MutableState<Boolean> = mutableStateOf(false)
    val isAdmin by _isAdmin

    fun setUserData(userData: UserData) {
        _userData.value = userData
    }

    fun setRole(isAdmin: Boolean) {
        _isAdmin.value = isAdmin
    }
}