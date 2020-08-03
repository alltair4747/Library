package com.kaufmannmarek.library

import android.content.Context
import android.util.DisplayMetrics
import kotlin.math.roundToInt

class MyScreen(private val context: Context) {

    fun pxToDp(pixels: Int): Int {
        return (pixels / (this.context.resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }

    fun dpToPx(densityPoints: Int): Int {
        return (densityPoints * (this.context.resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }

    fun getScreenWidth(): Float {
        return this.context.resources.displayMetrics.xdpi
    }

    fun getScreenHeightDensityPoints(): Int {
        return pxToDp(getScreenHeight2())
    }

    fun getScreenHeight(): Float {
        return this.context.resources.displayMetrics.ydpi
    }

    fun getScreenHeight2(): Int {
        return this.context.resources.displayMetrics.heightPixels
    }

}