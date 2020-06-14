package com.kaufmannmarek.library

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat

open class MyView(private val context: Context, private val parent: View) {
    private lateinit var view: View

    fun set(view: View) {
        this.view = view
    }

    open fun get(): View {
        return this.view
    }

    fun getParent(): View {
        return this.parent
    }

    fun getContext(): Context {
        return this.context
    }

}

@SuppressLint("ClickableViewAccessibility")
open class MyEditText(
    context: Context,
    hint: String,
    parent: View,
    errorText: String,
    drawableReference: Int?,
    isOnEditTextStart: Boolean?
) : MyView(context, parent), OnDrawableClick {
    private val editText = EditText(getContext())

    init {
        set(this.editText)
        setErrorText(errorText)
        setHint(hint)
        if (drawableReference != null) {
            if (isOnEditTextStart!!)
                get().setCompoundDrawables(
                    ContextCompat.getDrawable(
                        getContext(),
                        drawableReference
                    ), null, null, null
                )
            else
                get().setCompoundDrawables(
                    null,
                    null,
                    ContextCompat.getDrawable(getContext(), drawableReference),
                    null
                )
            get().setOnTouchListener { _, event ->
                when (isOnEditTextStart) {
                    true -> {
                        event.action == MotionEvent.ACTION_UP && event.rawX >= get().compoundDrawables[0].bounds.width()
                    }

                    else -> event.rawX <= get().compoundDrawables[2].bounds.width()
                }
            }
        }
    }

    override fun get(): EditText {
        return super.get() as EditText
    }

    fun setFontSize(textSize: Int) {
        get().setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat())
    }

    fun setTextColor(color: Int) {
        get().setTextColor(color)
    }

    fun setHint(text: String) {
        get().hint = text
    }

    fun setHint(stringReference: Int) {
        setHint(MyString(getContext()).fromResources(stringReference))
    }

    fun setErrorText(errorText: String) {
        get().error = errorText
    }

    fun setErrorText(errorTextReference: Int) {
        setErrorText(MyString(getContext()).fromResources(errorTextReference))
    }

    open fun onDrawableClick() {

    }

    private fun setWithDrawable() {

    }

    override fun onClick() {
        TODO("Not yet implemented")
    }


}

interface OnDrawableClick {

    fun onClick()

}