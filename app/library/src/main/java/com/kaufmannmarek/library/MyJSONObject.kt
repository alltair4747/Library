package com.kaufmannmarek.library

import android.content.Context
import org.json.JSONObject

class MyJSONObject(private val context: Context, private val jsonObject: JSONObject) {

    fun getString(paramName: String): String? {
        return this.jsonObject.getString(paramName)
    }

    fun getString(paramName: Int): String? {
        return getString(MyString(this.context).fromResources(paramName))
    }

    fun getBoolean(paramName: String): Boolean? {
        return this.jsonObject.getBoolean(paramName)
    }

    fun getBoolean(paramName: Int): Boolean? {
        return getBoolean(MyString(this.context).fromResources(paramName))
    }

    fun getInt(paramName: String): Int? {
        return this.jsonObject.getInt(paramName)
    }

    fun getInt(paramName: Int): Int? {
        return getInt(MyString(this.context).fromResources(paramName))
    }

}