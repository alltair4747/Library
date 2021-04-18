@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.kaufmannmarek.library

import android.app.Activity
import android.content.Context
import android.content.Intent
import java.io.Serializable

/**
 * Create intent class from intent of current activity
 *
 * @param context of currently displayed activity
 * @param intent is intent of currently displayed activity
 */
class ThisIntent(private val context: Context, intent: Intent?) {
    private val activity = (this.context as Activity)
    private val intent: Intent
    private var intentHasData = false

    init {
        if (intent == null)
            this.intent = getActivity().intent
        else
            this.intent = intent
    }

    /**
     * This class will create intent of current activity
     *
     * @param context of currently displayed activity
     */
    constructor(context: Context) : this(context, null)

    /**
     * @return activity code passed to this intent
     */
    fun getActivityCode(): Int {
        return getInt(R.string.keyActivityCode)
    }

    /**
     * @return intent of this class
     */
    fun getIntent(): Intent {
        this.intentHasData = true
        return this.intent
    }

    /**
     * @return context used to create this class
     */
    fun getContext(): Context {
        return this.context
    }

    /**
     * @return instance of activity
     */
    private fun getActivity(): Activity {
        return this.activity
    }

    /**
     * Adds object to this intent
     *
     * @param paramName is string, which holds name of the required object
     * @param serializable is object, which should be passed to another activity
     */
    fun setSerializable(paramName: String, serializable: Serializable) {
        getIntent().putExtra(paramName, serializable)
    }

    /**
     * Adds object to this intent
     *
     * @param paramName is int reference from string resource, which holds name of the required object
     * @param serializable is object, which should be passed to another activity
     */
    fun setSerializable(paramName: Int, serializable: Serializable) {
        setSerializable(MyString(getContext()).fromResources(paramName), serializable)
    }

    /**
     * @return serializable object with provided name
     * @param paramName is int reference from string resource, which holds name of the required object
     */
    fun getSerializable(paramName: Int): Serializable? {
        return getIntent().getSerializableExtra(
            MyString(getContext()).fromResources(
                paramName
            )
        )
    }

    /**
     * Will set canceled result of current activity
     */
    fun setCanceledResult() {
        setResult(Activity.RESULT_CANCELED)
    }

    /**
     * Will set positive result of current activity
     */
    fun setPositiveResult() {
        setResult(Activity.RESULT_OK)
    }

    /**
     * Will set positive result of current activity and put serializable to intent which can be used in default activity
     * @param paramName is String under which the value will retrievable in default activity
     * @param value is serializable variable which will be put to intent
     */
    fun setPositiveResult(paramName: String, value: Serializable) {
        setResult(Activity.RESULT_OK, paramName, value)
    }

    /**
     * Will set positive result of current activity and put serializable to intent which can be used in default activity
     * @param paramName is String under which the value will retrievable in default activity
     * @param value is serializable variable which will be put to intent
     */
    fun setPositiveResult(paramName: Int, value: Serializable) {
        setPositiveResult(MyString(getContext()).fromResources(paramName), value)
    }

    /**
     * Will set result of current activity and finish it. If any data were put in the intent of this class or it was accessed, the intent will passed to default class
     * @param resultCode is code, which will be delivered to default activity
     */
    fun setResult(resultCode: Int) {
        if(this.intentHasData)
            getActivity().setResult(resultCode, getIntent())
        else
            getActivity().setResult(resultCode)
        finish(true)
    }

    /**
     * Will set result of current activity and finish it
     *
     * @param resultCode is code, which will be delivered to default activity
     * @param paramName is String under which the serializable will be saved
     * @param value is serializable which will be put in intent
     */
    fun setResult(resultCode: Int, paramName: String, value: Serializable) {
        setSerializable(paramName, value)
        setResult(resultCode)
    }

    /**
     * Will set result of current activity and finish it
     *
     * @param resultCode is code, which will be delivered to default activity
     * @param paramName is int reference to String, under which the serializable will be saved
     * @param value is serializable which will be put in intent
     */
    fun setResult(resultCode: Int, paramName: Int, value: Serializable) {
        setResult(resultCode, MyString(getContext()).fromResources(paramName), value)
    }

    /**
     * Put Boolean value to intent
     *
     * @param paramName is String under which the boolean will be saved
     * @param value is boolean which will be put in intent
     */
    fun setBoolean(paramName: String, value: Boolean) {
        getIntent().putExtra(paramName, value)
    }

    /**
     * Put Boolean value to intent
     *
     * @param paramName is int reference to String, under which the boolean will be saved
     * @param value is boolean which will be put in intent
     */
    fun setBoolean(paramName: Int, value: Boolean) {
        setBoolean(MyString(getContext()).fromResources(paramName), value)
    }

    /**
     * Put Int value to intent
     *
     * @param paramName is String, under which the boolean will be saved
     * @param value is boolean which will be put in intent
     */
    fun setInt(paramName: String, value: Int) {
        getIntent().putExtra(paramName, value)
    }

    /**
     * Put Int value to intent
     *
     * @param paramName is int reference to int, under which the boolean will be saved
     * @param value is boolean which will be put in intent
     */
    fun setInt(paramName: Int, value: Int) {
        setInt(MyString(getContext()).fromResources(paramName), value)
    }

    /**
     * Put String value to intent
     *
     * @param paramName is String, under which the boolean will be saved
     * @param value is String which will be put in intent
     */
    fun setString(paramName: String, value: String) {
        getIntent().putExtra(paramName, value)
    }

    /**
     * Put String value to intent
     *
     * @param paramName is int reference to String, under which the boolean will be saved
     * @param value is String which will be put in intent
     */
    fun setString(paramName: Int, value: String) {
        setString(MyString(getContext()).fromResources(paramName), value)
    }

    /**
     * Put Double value to intent
     *
     * @param paramName is String, under which the boolean will be saved
     * @param value is Double which will be put in intent
     */
    fun setDouble(paramName: String, value: Double) {
        getIntent().putExtra(paramName, value)
    }

    /**
     * Put Double value to intent
     *
     * @param paramName is int reference to String, under which the boolean will be saved
     * @param value is Double which will be put in intent
     */
    fun setDouble(paramName: Int, value: Double) {
        setDouble(MyString(getContext()).fromResources(paramName), value)
    }

    /**
     * Finish activity with animation entering from left and exiting to right
     */
    private fun finish() {
        finish(false)
    }

    /**
     * Finish activity with correct animation
     *
     * @param isOnBackPressed animation will be entering from right. Else from left
     */
    fun finish(isOnBackPressed: Boolean) {
        getActivity().finish()
        when (isOnBackPressed) {
            false -> getActivity().overridePendingTransition(
                R.anim.enter_right_to_left,
                R.anim.exit_right_to_left
            )
            true -> getActivity().overridePendingTransition(
                R.anim.enter_left_to_right,
                R.anim.exit_left_to_right
            )
        }
    }

    /**
     * @return saved String under paramName
     * @param paramName is String, under which the value is stored
     */
    fun getString(paramName: String): String {
        return try {
            getIntent().getStringExtra(paramName)!!
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * @return saved String under paramName
     * @param paramName is int reference to String, under which the value is stored
     */
    fun getString(paramName: Int): String {
        return getString(MyString(getContext()).fromResources(paramName))
    }

    /**
     * @return saved Boolean under paramName
     * @param paramName is String, under which the value is stored
     */
    fun getBoolean(paramName: String): Boolean {
        return getIntent().getBooleanExtra(paramName, false)
    }

    /**
     * @return saved Boolean under paramName
     * @param paramName is int reference to String, under which the value is stored
     */
    fun getBoolean(paramName: Int): Boolean {
        return getBoolean(MyString(getContext()).fromResources(paramName))
    }

    /**
     * @return saved int under paramName
     * @param paramName is String, under which the value is stored
     */
    fun getInt(paramName: String): Int {
        return getIntent().getIntExtra(paramName, 0)
    }

    /**
     * @return saved int under paramName
     * @param paramName is int reference to String, under which the value is stored
     */
    fun getInt(paramName: Int): Int {
        return getInt(MyString(getContext()).fromResources(paramName))
    }

    /**
     * @return saved int under paramName
     * @param paramName is String, under which the value is stored
     */
    fun getDouble(paramName: String): Double {
        return getIntent().getDoubleExtra(paramName, 0.0)
    }

    /**
     * @return saved int under paramName
     * @param paramName is int reference to String, under which the value is stored
     */
    fun getDouble(paramName: Int): Double {
        return getDouble(MyString(getContext()).fromResources(paramName))
    }


}