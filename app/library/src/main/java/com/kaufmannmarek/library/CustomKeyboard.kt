@file:Suppress("unused")
package com.kaufmannmarek.library

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager


class CustomKeyboard(private val context: Context) {

    /**
     * Will hide virtual keyBoard
     */
    fun hide() {
        ((this.context as Activity).getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
            InputMethodManager.SHOW_IMPLICIT,
            0
        )
    }
}