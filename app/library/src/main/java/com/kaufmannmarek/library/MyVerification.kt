@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.kaufmannmarek.library

import android.content.Context
import android.util.Patterns
import android.view.View
import android.widget.*

/**
 * Creates class with functions which verify user input in ui elements or another conditions. Use available functions to verify user inputs and once you check all required view, call function verifyInput. It will return, if the input was valid according to used functions.
 *
 * @param context of currently displayed activity
 * @param useDynamicFocus is condition. If true, after calling verifyInput it will focus the view, where the first error was found. Else not.
 */
class MyVerification(private val context: Context, private val useDynamicFocus: Boolean) {
    private var noErrorFound = true
    private lateinit var myString: MyString

    fun isValidEmailAddress(emailAddress: EditText) {
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress.text.toString()).matches()) {
            errorFoundInEditText(emailAddress, R.string.emailBadFormat)
        }
    }

    fun getContext(): Context {
        return this.context
    }

    fun getMyString(): MyString {
        if (!::myString.isInitialized)
            this.myString = MyString(getContext())
        return this.myString
    }

    /**
     * Checks if the provided passwords matches and if the password length is at least "length" long
     *
     * @param password is first editText with password
     * @param passwordVerify is second editText with password verification
     * @param length is minimum count of characters in password
     */
    fun isValidPassword(password: EditText, passwordVerify: EditText, length: Int) {
        val pswd = password.text.toString()
        val pswdVerify = passwordVerify.text.toString()
        if (pswd != pswdVerify) {
            errorFoundInEditText(password, R.string.passwordsDoesNotMatch)
            errorFoundInEditText(passwordVerify, R.string.passwordsDoesNotMatch)
        } else if (pswd.length < length) {
            errorFoundInEditText(password, R.string.passwordIsShort)
            errorFoundInEditText(passwordVerify, R.string.passwordIsShort)
        }
    }

    /**
     * Checks if the provided passwords matches and if the password length is at least 8 characters
     *
     * @param firstEditText is first editText with password
     * @param secondEditText is second editText with password verification
     */
    fun isValidPassword(firstEditText: EditText, secondEditText: EditText) {
        isValidPassword(firstEditText, secondEditText, 8)
    }

    /**
     * Checks if provided editText is not empty
     *
     * @param editText is checked editText
     */
    fun editTextIsNotEmpty(editText: EditText) {
        if (editText.text.toString().isEmpty())
            errorFoundInEditText(editText, null)
        else
            editText.error = null
    }

    /**
     * Sets focus to view, if error found in the view is first
     *
     * @param view is examined ui element
     */
    private fun setViewFocus(view: View) {
        if (this.noErrorFound) {
            this.noErrorFound = false
            if (this.useDynamicFocus)
                view.isFocusable = true
        }
    }

    /**
     * Checks, if the provided checkedTextView is checked
     *
     * @param checkedTextView is examined checkedTextView
     * @param errorText is String, which will be displayed in case of error
     */
    fun textViewIsChecked(checkedTextView: CheckedTextView, errorText: String) {
        if (!checkedTextView.isChecked) {
            setViewFocus(checkedTextView)
            checkedTextView.error = errorText
        }
    }

    /**
     * Checks, if the provided checkedTextView is checked
     *
     * @param checkedTextView is examined checkedTextView
     * @param errorText is int reference to String, which will be displayed in case of error
     */
    fun textViewIsChecked(checkedTextView: CheckedTextView, errorText: Int) {
        textViewIsChecked(checkedTextView, getMyString().fromResources(errorText))
    }

    /**
     * Sets proper error text to provided editText
     *
     * @param editText is where the error message will be set
     * @param errorText is int reference to String, which will be displayed in case of error
     */
    private fun errorFoundInEditText(editText: EditText, errorText: Int?) {
        setViewFocus(editText)
        editText.error = getMyString().fromResources(
            when (editText.text.toString().isEmpty()) {
                true -> R.string.fieldIsEmpty
                false -> errorText!!
            }
        )
    }

    /**
     * Checks, if listView contains at least one item
     *
     * @param view is listView or expandableListView
     * @param message is optional reference to String, which will be displayed in snackBar. If is null, no message will be displayed
     */
    fun listIsNotEmpty(view: View, message: Int?) {
        if (view is ListView && view.childCount == 0 || view is ExpandableListView && view.count == 0) {
            if (this.noErrorFound) {
                setViewFocus(view)
                if (message != null)
                    MySnackBarWarning(this.context, getMyString().fromResources(message))
            }
        }
    }

    /**
     * Checks, if the provided boolean is true
     *
     * @param boolean is provided boolean
     * @param message is int reference to String, which will be displayed in snackBar
     */
    fun booleanIsTrue(boolean: Boolean, message: Int) {
        booleanIsTrue(boolean, getMyString().fromResources(message))
    }

    /**
     * Checks, if the provided boolean is true and display snackBar with message
     *
     * @param boolean is provided boolean
     * @param message is optional int reference to String, which will displayed in snackBar. If null, no snackBar will be displayed
     */
    fun booleanIsTrue(boolean: Boolean, message: String?) {
        when (boolean) {
            false -> {
                if (this.noErrorFound) {
                    this.noErrorFound = false
                    if (message != null)
                        MySnackBarWarning(
                            this.context,
                            message
                        )
                }
            }
        }
    }

    /**
     * Checks if the provided radioGroup view has at least one selected radioButton
     *
     * @param radioGroup is examined radioGroup
     * @param message is optional String, which will be displayed in snackBar. If null, no snackBar will be displayed
     */
    fun radioGroupHasCheckedItem(radioGroup: RadioGroup, message: String?) {
        when (radioGroup.checkedRadioButtonId) {
            -1 -> if (this.noErrorFound) {
                this.noErrorFound = false
                if (message != null)
                    MySnackBarWarning(this.context, message)
            }
        }
    }

    /**
     * Checks if the provided radioGroup view has at least one selected radioButton
     *
     * @param radioGroup is examined radioGroup
     * @param message is int reference to String, which will be displayed in snackBar
     */
    fun radioGroupHasCheckedItem(radioGroup: RadioGroup, message: Int) {
        radioGroupHasCheckedItem(radioGroup, getMyString().fromResources(message))
    }

    /**
     * Call this function after checking all user inputs
     */
    fun isErrorFree(): Boolean {
        val condition = this.noErrorFound
        this.noErrorFound = true
        return condition
    }
}