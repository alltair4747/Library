@file:Suppress("unused", "MemberVisibilityCanBePrivate")
package com.kaufmannmarek.library

import android.app.Activity
import android.content.Context
import android.content.Intent
import java.io.Serializable

/**
 * This class is used to switch from current activity to another activity
 *
 * @param context of currently displayed activity
 * @param destinationActivity is class, which will be displayed after transaction
 */
class NewIntent(private val context: Context, destinationActivity: Class<*>) {
    private val newIntent = Intent(this.context, destinationActivity)

    @Suppress("SameParameterValue")
    /**
     * Put int value to intent
     *
     * @param paramName is int reference to String used to retrieve int value
     * @param value is value, which will be put in intent
     */
    private fun setIntExtra(paramName: Int, value: Int) {
        getIntent().putExtra(MyString(this.context).getStringFromInt(paramName), value)
    }

    /**
     * @return intent of a new activity
     */
    private fun getIntent(): Intent {
        return this.newIntent
    }

    /**
     * Sets object, which will be passed to a following activity
     *
     * @param paramName is int reference to String used to retrieve serializable
     * @param serializable is object to passed
     */
    private fun setSerializable(paramName: Int ,serializable: Serializable) {
        getIntent().putExtra(
            MyString(this.context).getStringFromInt(paramName),
            serializable
        )
    }

    /**
     * Sets activity code, which will be passed to a following activity
     *
     * @param activityCode is code to be passed
     */
    private fun setActivityCode(activityCode: Int) {
        setIntExtra(R.string.keyActivityCode, activityCode)
    }

    /**
     * Starts a new activity with provided activity code and with animation entering from left and exiting to right, while finishing a default activity
     *
     * @param activityCode is code, which can be retrieved in following activity
     */
    fun startWithActivityCode(activityCode: Int) {
        startWithActivityCode(activityCode, false)
    }

    /**
     * Starts a new activity with provided activity code and with correct animation, while finishing a default activity
     *
     * @param activityCode is code, which can be retrieved in following activity
     * @param isOnBackPressed is condition, which selects correct animation
     */
    fun startWithActivityCode(activityCode: Int, isOnBackPressed: Boolean) {
        setActivityCode(activityCode)
        startNewActivity(true, isOnBackPressed)
    }

    /**
     * Starts a new activity with correct animation and may finish a default activity
     *
     * @param finishCurrentActivity is condition, which will finish a default activity if true
     * @param isOnBackPressed is condition, which selects correct animation
     * @param activityCode is code, which can be retrieved in following activity
     */
    fun startNewActivity(
        finishCurrentActivity: Boolean,
        isOnBackPressed: Boolean,
        activityCode: Int
    ) {
        setActivityCode(activityCode)
        startNewActivity(finishCurrentActivity, isOnBackPressed)
    }

    /**
     * Starts a new activity with animation entering from left and exiting to right and may finish a default activity
     *
     * @param finishCurrentActivity is condition, which will finish a default activity if true
     */
    fun startNewActivity(finishCurrentActivity: Boolean) {
        startNewActivity(finishCurrentActivity, false, 0)
    }

    /**
     * Starts a new activity with correct animation and may finish a default activity
     *
     * @param finishCurrentActivity is condition, which will finish a default activity if true
     * @param isOnBackPressed is condition, which selects correct animation
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun startNewActivity(finishCurrentActivity: Boolean, isOnBackPressed: Boolean) {
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
     * Starts a new activity with provided object, while finishing a default activity
     *
     * @param paramName is int reference to String used to retrieve serializable
     * @param serializable is object, which will passed to new activity
     */
    fun startActivityWithSerializable(paramName: Int, serializable: Serializable) {
        setSerializable(paramName, serializable)
        startNewActivity(false)
    }

    /**
     * Starts a new activity with provided request code and passed object, while finishing a default activity
     *
     * @param requestCode is code, which will passed as activity code to another activity
     * @param paramName is int reference to String used to retrieve serializable
     * @param serializable is object, which will passed to new activity
     */
    fun startActivityWithSerializable(requestCode: Int, paramName: Int, serializable: Serializable) {
        setActivityCode(requestCode)
        startActivityWithSerializable(paramName, serializable)
        startNewActivity(false)
    }

    /**
     * Starts a new activity with provided request code. After finishing the new activity, a default activity can handle on response action
     *
     * @param requestCode is code, which will passed as activity code to another activity.
     */
    fun startActivityForResult(requestCode: Int) {
        setActivityCode(requestCode)
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
    fun startActivityForResult(requestCode: Int, paramName: Int, serializable: Serializable) {
        setSerializable(paramName, serializable)
        startActivityForResult(requestCode)
    }
}