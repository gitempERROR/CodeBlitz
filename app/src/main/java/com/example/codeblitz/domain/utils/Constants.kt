package com.example.codeblitz.domain.utils

import android.util.DisplayMetrics
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