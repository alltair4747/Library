package com.kaufmannmarek.library

import android.content.Context
import android.util.DisplayMetrics
import kotlin.math.roundToInt

@Suppress("unused", "MemberVisibilityCanBePrivate")
class MyScreen(context: Context) {
    private val displayResources = context.resources.displayMetrics

    /**
     * @return rounded number of density points, which are created by provided number of pixels on the device display
     * @param pixels is number pixels, that will be converted to density points
     */
    fun pxToDp(pixels: Int): Int {
        return (pixels / (this.displayResources.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }

    /**
     * @return rounded number of pixels, which creates provided number of density points on the device display
     * @param densityPoints is number dpi, that will be converted to pixels
     */
    fun dpToPx(densityPoints: Int): Int {
        return (densityPoints * (this.displayResources.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }

    /**
     * @return number of pixels in one inch of the display
     */
    fun getDisplayDensity(): Float {
        return this.displayResources.ydpi
    }

    /**
     * @return number of pixels, which creates the height of the device display
     */
    fun getScreenHeight(): Int {
        return this.displayResources.heightPixels
    }

    /**
     * @return number of density points, which creates the height of the device display
     */
    fun getScreenHeightDensityPoints(): Int {
        return pxToDp(getScreenHeight())
    }

    /**
     * @return number of pixels, which creates the width of the device display
     */
    fun getScreenWidth(): Int {
        return this.displayResources.widthPixels
    }

    /**
     * @return number of density points, which creates the height of the device display
     */
    fun getScreenWidthDensityPoints(): Int {
        return pxToDp(getScreenWidth())
    }

}