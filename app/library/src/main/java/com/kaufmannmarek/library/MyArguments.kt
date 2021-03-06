@file:Suppress("unused", "MemberVisibilityCanBePrivate", "UNUSED_VALUE")

package com.kaufmannmarek.library

import android.content.Context
import android.os.Bundle
import java.io.Serializable

/**
 * Creates class, which offers easier retrieve of the arguments passed to fragment transaction
 *
 * @param bundle are passed arguments during fragment transaction
 * @param context of currently displayed activity
 */
class MyArguments(private val bundle: Bundle, private val context: Context) {

    /**
     * @return serializable object put in transaction
     * @param paramName is key, under which the object was added in transaction
     */
    fun getSerializable(paramName: String): Serializable? {
        return this.bundle.getSerializable(paramName)
    }

    /**
     * @return serializable object put in transaction
     * @param paramName is int reference to String, which is key, under which the object was added in transaction
     */
    fun getSerializable(paramName: Int): Serializable? {
        return getSerializable(MyString(this.context).fromResources(paramName))
    }

    /**
     * @return int put in transaction
     * @param paramName is key, under which the int was added in transaction
     */
    fun getInt(paramName: String): Int {
        return this.bundle.getInt(paramName)
    }

    /**
     * @return int put in transaction
     * @param paramName is int reference to String, which is key, under which the int was added in transaction
     */
    fun getInt(paramName: Int): Int {
        return getInt(MyString(this.context).fromResources(paramName))
    }

    /**
     * @return value saved under String "activityCode"
     */
    fun getActivityCode(): Int {
        return getInt(R.string.keyActivityCode)
    }

    /**
     * @return String put in transaction
     * @param paramName is key, under which the String was added in transaction
     */
    fun getString(paramName: String): String {
        return try {
            this.bundle.getString(paramName)!!
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * @return String put in transaction
     * @param paramName is int reference to String, which is key, under which the String was added in transaction
     */
    fun getString(paramName: Int): String? {
        return getString(MyString(this.context).fromResources(paramName))
    }


}