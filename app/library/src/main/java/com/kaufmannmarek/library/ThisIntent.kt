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
        return this.intent
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
     * @param paramName is int reference from string resource, which holds name of the required object
     * @param serializable is object, which should be passed to another activity
     */
    fun setSerializable(paramName: Int, serializable: Serializable) {
        getIntent().putExtra(
            MyString(this.context).fromResources(paramName),
            serializable
        )
    }

    /**
     * @return serializable object with provided name
     * @param paramName is int reference from string resource, which holds name of the required object
     */
    fun getSerializable(paramName: Int): Serializable? {
        return getIntent().getSerializableExtra(
            MyString(this.context).fromResources(
                paramName
            )
        )
    }

    /**
     * Will set canceled result of current activity
     */
    fun setCanceledResult() {
        setResult(Activity.RESULT_CANCELED, null)
    }

    /**
     * Will set positive result of current activity
     */
    fun setPositiveResult() {
        setResult(Activity.RESULT_OK, getIntent())
    }

    /**
     * Will set result of current activity
     *
     * @param resultCode is code, which will be delivered to default activity
     * @param intent are data, from current activity
     */
    private fun setResult(resultCode: Int, intent: Intent?) {
        getActivity().setResult(resultCode, intent)
        finish()
    }

    /**
     * Finish correct activity with animation entering from left and exiting to right
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
            true -> getActivity().overridePendingTransition(
                R.anim.enter_right_to_left,
                R.anim.exit_right_to_left
            )
            false -> getActivity().overridePendingTransition(
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
        return getString(MyString(this.context).fromResources(paramName))
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
        return getBoolean(MyString(this.context).fromResources(paramName))
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
        return getInt(MyString(this.context).fromResources(paramName))
    }
}