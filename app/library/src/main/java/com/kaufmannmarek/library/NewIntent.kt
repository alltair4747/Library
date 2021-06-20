@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.kaufmannmarek.library

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import java.io.Serializable

/**
 * This class is used to switch from current activity to another activity
 *
 * @param context of currently displayed activity
 */
class NewIntent internal constructor(private val context: Context) {
    private lateinit var newIntent: Intent

    /**
     * This class is used to switch from current activity to another activity
     *
     * @param context of currently displayed activity
     * @param intent is defined intent, which will be displayed after transaction
     */
    constructor(context: Context, intent: Intent) : this(context) {
        this.newIntent = intent
    }

    /**
     * This class is used to switch from current activity to another activity
     *
     * @param context of currently displayed activity
     * @param destinationActivity is class, which will be displayed after transaction
     */
    constructor(context: Context, destinationActivity: Class<*>) : this(context) {
        this.newIntent = Intent(this.context, destinationActivity)
    }

    /**
     * Put int value to intent
     *
     * @param paramName is String used to retrieve int value
     * @param value is value, which will be put in intent
     */
    fun putInt(paramName: String, value: Int) {
        getIntent().putExtra(paramName, value)
    }

    /**
     * Put int value to intent
     *
     * @param paramName is int reference to String used to retrieve int value
     * @param value is value, which will be put in intent
     */
    fun putInt(paramName: Int, value: Int) {
        putInt(MyString(this.context).fromResources(paramName), value)
    }

    /**
     * @return intent of a new activity
     */
    fun getIntent(): Intent {
        return this.newIntent
    }

    /**
     * @return bundle of the activity
     */
    fun getExtras(): Bundle {
        return getIntent().extras!!
    }

    /**
     * Sets object, which will be passed to a following activity
     *
     * @param paramName is String used to retrieve serializable
     * @param serializable is object to passed
     */
    private fun putSerializable(paramName: String, serializable: Serializable) {
        getIntent().putExtra(paramName, serializable)
    }

    /**
     * Sets object, which will be passed to a following activity
     *
     * @param paramName is int reference to String used to retrieve serializable
     * @param serializable is object to passed
     */
    private fun putSerializable(paramName: Int, serializable: Serializable) {
        putSerializable(MyString(this.context).fromResources(paramName), serializable)
    }

    /**
     * Sets activity code, which will be passed to a following activity
     *
     * @param activityCode is code to be passed
     */
    private fun putActivityCode(activityCode: Int) {
        putInt(R.string.keyActivityCode, activityCode)
    }

    /**
     * Starts a new activity with correct animation, puts serializable object and may finish a default activity
     *
     * @param finishCurrentActivity is condition, which will finish a default activity if true
     * @param isOnBackPressed is condition, which selects correct animation
     * @param activityCode is code, which can be retrieved in following activity
     * @param serializableName is name, under which you can later retrieve serializable object
     * @param serializable is object to be passed
     */
    fun start(
        finishCurrentActivity: Boolean,
        isOnBackPressed: Boolean,
        activityCode: Int,
        serializableName: String,
        serializable: Serializable
    ) {
        putSerializable(serializableName, serializable)
        start(finishCurrentActivity, isOnBackPressed, activityCode)
    }

    /**
     * Starts a new activity with correct animation, puts serializable object and may finish a default activity
     *
     * @param finishCurrentActivity is condition, which will finish a default activity if true
     * @param isOnBackPressed is condition, which selects correct animation
     * @param activityCode is code, which can be retrieved in following activity
     * @param serializableName is int reference to String, which holds name, under which you can later retrieve serializable object
     * @param serializable is object to be passed
     */
    fun start(
        finishCurrentActivity: Boolean,
        isOnBackPressed: Boolean,
        activityCode: Int,
        serializableName: Int,
        serializable: Serializable
    ) {
        start(
            finishCurrentActivity,
            isOnBackPressed,
            activityCode,
            MyString(this.context).fromResources(serializableName),
            serializable
        )
    }

    /**
     * Starts a new activity with correct animation and may finish a default activity
     *
     * @param finishCurrentActivity is condition, which will finish a default activity if true
     * @param isOnBackPressed is condition, which selects correct animation
     * @param activityCode is code, which can be retrieved in following activity
     */
    fun start(
        finishCurrentActivity: Boolean,
        isOnBackPressed: Boolean,
        activityCode: Int
    ) {
        putActivityCode(activityCode)
        start(finishCurrentActivity, isOnBackPressed)
    }

    /**
     * Starts a new activity with animation entering from left and exiting to right and may finish a default activity
     *
     * @param finishCurrentActivity is condition, which will finish a default activity if true
     */
    fun start(finishCurrentActivity: Boolean) {
        start(finishCurrentActivity, false, 0)
    }

    /**
     * Starts a new activity with correct animation and may finish a default activity
     *
     * @param finishCurrentActivity is condition, which will finish a default activity if true
     * @param isOnBackPressed is condition, which selects correct animation
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun start(finishCurrentActivity: Boolean, isOnBackPressed: Boolean) {
        val currentActivity = this.context as Activity
        currentActivity.startActivity(getIntent())
        if (finishCurrentActivity)
            currentActivity.finish()
        if (isOnBackPressed)
            currentActivity.overridePendingTransition(
                R.anim.enter_left_to_right,
                R.anim.exit_left_to_right
            )
        else
            currentActivity.overridePendingTransition(
                R.anim.enter_right_to_left,
                R.anim.exit_right_to_left
            )
    }

    /**
     * Starts a new activity with provided request code. After finishing the new activity, a default activity can handle on response action
     *
     * @param requestCode is code, which will passed as activity code to another activity.
     */
    fun startForResult(requestCode: Int) {
        putActivityCode(requestCode)
        val currentActivity = this.context as Activity
        currentActivity.startActivityForResult(getIntent(), requestCode)
        currentActivity.overridePendingTransition(
            R.anim.enter_right_to_left,
            R.anim.exit_right_to_left
        )
    }

    /**
     * Starts a new activity with provided request code and passed object. After finishing the new activity, a default activity can handle on response action
     *
     * @param requestCode is code, which will passed as activity code to another activity
     * @param serializable is object, which will passed to new activity
     */
    fun startForResult(requestCode: Int, paramName: Int, serializable: Serializable) {
        putSerializable(paramName, serializable)
        startForResult(requestCode)
    }

    /**
     * Put String value to intent
     *
     * @param paramName is  String used to retrieve int value
     * @param value is value, which will be put in intent
     */
    fun putString(paramName: String, value: String) {
        getIntent().putExtra(paramName, value)
    }

    /**
     * Put String value to intent
     *
     * @param paramName is int reference to String used to retrieve int value
     * @param value is value, which will be put in intent
     */
    fun putString(paramName: Int, value: String) {
        putString(MyString(this.context).fromResources(paramName), value)
    }

    /**
     * Put String value to intent
     *
     * @param paramName is  String used to retrieve int value
     * @param value is int reference to String, which will be put in intent
     */
    fun putString(paramName: String, value: Int) {
        putString(paramName, MyString(this.context).fromResources(value))
    }

    /**
     * Put String value to intent
     *
     * @param paramName is int reference to String used to retrieve int value
     * @param value is int reference to String, which will be put in intent
     */
    fun putString(paramName: Int, value: Int) {
        putString(
            MyString(this.context).fromResources(paramName),
            MyString(this.context).fromResources(value)
        )
    }

    /**
     * Put boolean value to intent
     *
     * @param paramName is int reference to String used to retrieve int value
     * @param value is value, which will be put in intent
     */
    fun putBoolean(paramName: String, value: Boolean) {
        getIntent().putExtra(paramName, value)
    }

    /**
     * Put boolean value to intent
     *
     * @param paramName is int reference to String used to retrieve int value
     * @param value is value, which will be put in intent
     */
    fun putBoolean(paramName: Int, value: Boolean) {
        putBoolean(MyString(this.context).fromResources(paramName), value)
    }
}