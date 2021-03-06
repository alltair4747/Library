@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.kaufmannmarek.library

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

/**
 * Allows basic operations with already existing jsonObject
 *
 * @param context of currently displayed activity
 * @param jsonObject
 */
class MyJSONObject(private val context: Context, private val jsonObject: JSONObject) {
    private val myString = MyString(this.context)

    /**
     * Creates new JSONObject and offers all basic operations
     *
     * @param context of currently displayed activity
     */
    constructor(context: Context) : this(context, JSONObject())

    /**
     * Creates new JSONObject from jsonObject, which is currently saved as String
     *
     * @param context of currently displayed activity
     */
    constructor(context: Context, jsonObjectAsString: String) : this(
        context,
        JSONObject(jsonObjectAsString)
    )

    /**
     * @return context used to create this object
     */
    fun getContext(): Context {
        return this.context
    }

    /**
     * @return jsonObject
     */
    fun get(): JSONObject {
        return this.jsonObject
    }

    /**
     * @return jsonObject as String
     */
    fun asString(): String {
        return get().toString()
    }

    /**
     * Removes param from jsonObject if it exists
     *
     * @param paramName is String, which represents name of parameter, which will be removed
     */
    fun removeParam(paramName: String) {
        try {
            get().remove(paramName)
        } catch (e: Exception) {
            Log.e("MyJSONObject", "Param with name $paramName was not found in jsonObject")
        }
    }

    /**
     * @return number of params saved in jsonObject
     */
    fun length(): Int {
        return get().length()
    }

    /**
     * Removes param from jsonObject if it exists
     *
     * @param paramName is int reference to String, which represents name of parameter, which will be removed
     */
    fun removeParam(paramName: Int) {
        removeParam(this.myString.fromResources(paramName))
    }

    /**
     * @return String value saved under provided paramName. If the jsonObject does not contain value with paramName, return null
     * @param paramName is String, which represents name, under which is the String saved
     */
    fun getString(paramName: String): String? {
        return get().getString(paramName)
    }

    /**
     * @return String value saved under provided paramName. If the jsonObject does not contain value with paramName, return null
     * @param paramName is int reference to String, which represents name, under which is the String saved
     */
    fun getString(paramName: Int): String? {
        return getString(myString.fromResources(paramName))
    }

    /**
     * @return String value saved under provided paramName
     * @param paramName is String, which represents name, under which is the String saved
     */
    fun getStringNonNull(paramName: String): String {
        return getString(paramName)!!
    }

    /**
     * @return String value saved under provided paramName
     * @param paramName is int reference to String, which represents name, under which is the String saved
     */
    fun getStringNonNull(paramName: Int): String {
        return getString(paramName)!!
    }

    /**
     * @return Boolean value saved under provided paramName. If the jsonObject does not contain value with paramName, return null
     * @param paramName is String, which represents name, under which is the Boolean saved
     */
    fun getBoolean(paramName: String): Boolean? {
        return get().getBoolean(paramName)
    }

    /**
     * @return Boolean value saved under provided paramName. If the jsonObject does not contain value with paramName, return null
     * @param paramName is int reference to String, which represents name, under which is the Boolean saved
     */
    fun getBoolean(paramName: Int): Boolean? {
        return getBoolean(this.myString.fromResources(paramName))
    }

    /**
     * @return Boolean value saved under provided paramName
     * @param paramName is String, which represents name, under which is the Boolean saved
     */
    fun getBooleanNonNull(paramName: String): Boolean {
        return getBoolean(paramName)!!
    }

    /**
     * @return Boolean value saved under provided paramName
     * @param paramName is int reference to String, which represents name, under which is the Boolean saved
     */
    fun getBooleanNonNull(paramName: Int): Boolean {
        return getBoolean(paramName)!!
    }

    /**
     * @return Int value saved under provided paramName. If the jsonObject does not contain value with paramName, return null
     * @param paramName is String, which represents name, under which is the Int saved
     */
    fun getInt(paramName: String): Int? {
        return get().getInt(paramName)
    }

    /**
     * @return Int value saved under provided paramName. If the jsonObject does not contain value with paramName, return null
     * @param paramName is int reference to String, which represents name, under which is the Int saved
     */
    fun getInt(paramName: Int): Int? {
        return getInt(this.myString.fromResources(paramName))
    }

    /**
     * @return Int value saved under provided paramName
     * @param paramName is String, which represents name, under which is the Boolean saved
     */
    fun getIntNonNull(paramName: String): Int {
        return getInt(paramName)!!
    }

    /**
     * @return Boolean value saved under provided paramName
     * @param paramName is int reference to String, which represents name, under which is the Int saved
     */
    fun getIntNonNull(paramName: Int): Int {
        return getInt(paramName)!!
    }

    /**
     * @return Long value saved under provided paramName. If the jsonObject does not contain value with paramName, return null
     * @param paramName is String, which represents name, under which is the Long saved
     */
    fun getLong(paramName: String): Long? {
        return get().getLong(paramName)
    }

    /**
     * @return Long value saved under provided paramName. If the jsonObject does not contain value with paramName, return null
     * @param paramName is int reference to String, which represents name, under which is the Long saved
     */
    fun getLong(paramName: Int): Long? {
        return getLong(this.myString.fromResources(paramName))
    }

    /**
     * @return Long value saved under provided paramName
     * @param paramName is String, which represents name, under which is the Long saved
     */
    fun getLongNonNull(paramName: String): Long {
        return getLong(paramName)!!
    }

    /**
     * @return Long value saved under provided paramName
     * @param paramName is int reference to String, which represents name, under which is the Long saved
     */
    fun getLongNonNull(paramName: Int): Long {
        return getLong(paramName)!!
    }

    /**
     * @return Double value saved under provided paramName. If the jsonObject does not contain value with paramName, return null
     * @param paramName is String, which represents name, under which is the Double saved
     */
    fun getDouble(paramName: String): Double? {
        return get().getDouble(paramName)
    }

    /**
     * @return Double value saved under provided paramName. If the jsonObject does not contain value with paramName, return null
     * @param paramName is int reference to String, which represents name, under which is the Double saved
     */
    fun getDouble(paramName: Int): Double? {
        return getDouble(this.myString.fromResources(paramName))
    }

    /**
     * @return Double value saved under provided paramName
     * @param paramName is String, which represents name, under which the Double is saved
     */
    fun getDoubleNonNull(paramName: String): Double {
        return getDouble(paramName)!!
    }

    /**
     * @return Double value saved under provided paramName
     * @param paramName is int reference to String, which represents name, under which the Double is saved
     */
    fun getDoubleNonNull(paramName: Int): Double {
        return getDouble(paramName)!!
    }

    /**
     * @return if the jsonObject contains a value saved under provided paramName
     * @param paramName is String, which represents name, under which the value should be saved
     */
    fun has(paramName: String): Boolean {
        return get().has(paramName)
    }

    /**
     * @return if the jsonObject contains a value saved under provided paramName
     * @param paramName is int reference to String, which represents name, under which the value should be saved
     */
    fun has(paramName: Int): Boolean {
        return has(this.myString.fromResources(paramName))
    }

    /**
     * @return JSONArray saved under provided paramName. If the jsonObject does not contain jsonArray with paramName, return null
     * @param paramName is String, which represents name, under which is the jsonArray saved
     */
    fun getJSONArray(paramName: String): JSONArray? {
        return get().getJSONArray(paramName)
    }

    /**
     * @return JSONArray saved under provided paramName. If the jsonObject does not contain jsonArray with paramName, return null
     * @param paramName is int reference to String, which represents name, under which is the jsonArray saved
     */
    fun getJSONArray(paramName: Int): JSONArray? {
        return getJSONArray(this.myString.fromResources(paramName))
    }

    /**
     * @return JSONArray saved under provided paramName
     * @param paramName is String, which represents name, under which is the jsonArray saved
     */
    fun getJSONArrayNonNull(paramName: String): JSONArray {
        return getJSONArray(paramName)!!
    }

    /**
     * @return JSONArray saved under provided paramName
     * @param paramName is int reference to String, which represents name, under which is the jsonArray saved
     */
    fun getJSONArrayNonNull(paramName: Int): JSONArray {
        return getJSONArray(paramName)!!
    }

    /**
     * @return MyJSONArray saved under provided paramName. If the jsonObject does not contain jsonArray with paramName, return null
     * @param paramName is String, which represents name, under which is the MyJSONArray saved
     */
    fun getMyJSONArray(paramName: String): MyJSONArray? {
        return try {
            MyJSONArray(getContext(), get().getJSONArray(paramName))
        } catch (e: Exception) {
            return null
        }
    }

    /**
     * @return MyJSONArray saved under provided paramName. If the jsonObject does not contain jsonArray with paramName, return null
     * @param paramName is int reference to String, which represents name, under which is the MyJSONArray saved
     */
    fun getMyJSONArray(paramName: Int): MyJSONArray? {
        return getMyJSONArray(this.myString.fromResources(paramName))
    }

    /**
     * @return MyJSONArray saved under provided paramName
     * @param paramName is String, which represents name, under which is the MyJSONArray saved
     */
    fun getMyJSONArrayNonNull(paramName: String): MyJSONArray {
        return getMyJSONArray(paramName)!!
    }

    /**
     * @return MyJSONArray saved under provided paramName
     * @param paramName is int reference to String, which represents name, under which is the MyJSONArray saved
     */
    fun getMyJSONArrayNonNull(paramName: Int): MyJSONArray {
        return getMyJSONArray(paramName)!!
    }

    /**
     * @return JSONObject saved under provided paramName. If the jsonObject does not contain jsonArray with paramName, return null
     * @param paramName is String, which represents name, under which is the JSONObject saved
     */
    fun getJSONObject(paramName: String): JSONObject? {
        return get().getJSONObject(paramName)
    }

    /**
     * @return JSONObject saved under provided paramName. If the jsonObject does not contain jsonArray with paramName, return null
     * @param paramName is int reference to String, which represents name, under which is the JSONObject saved
     */
    fun getJSONObject(paramName: Int): JSONObject? {
        return getJSONObject(this.myString.fromResources(paramName))
    }

    /**
     * @return JSONObject saved under provided paramName
     * @param paramName is String, which represents name, under which is the JSONObject saved
     */
    fun getJSONObjectNonNull(paramName: String): JSONObject {
        return getJSONObject(paramName)!!
    }

    /**
     * @return JSONObject saved under provided paramName
     * @param paramName is int reference to String, which represents name, under which is the JSONObject saved
     */
    fun getJSONObjectNonNull(paramName: Int): JSONObject {
        return getJSONObject(paramName)!!
    }

    /**
     * @return MyJSONObject saved under provided paramName. If the jsonObject does not contain jsonArray with paramName, return null
     * @param paramName is String, which represents name, under which is the MyJSONObject saved
     */
    fun getMyJSONObject(paramName: String): MyJSONObject? {
        return try {
            MyJSONObject(getContext(), getJSONObjectNonNull(paramName))
        } catch (e: Exception) {
            null
        }
    }

    /**
     * @return MyJSONObject saved under provided paramName. If the jsonObject does not contain jsonArray with paramName, return null
     * @param paramName is int reference to String, which represents name, under which is the MyJSONObject saved
     */
    fun getMyJSONObject(paramName: Int): MyJSONObject? {
        return getMyJSONObject(this.myString.fromResources(paramName))
    }

    /**
     * @return MyJSONObject saved under provided paramName
     * @param paramName is String, which represents name, under which is the myJSONObject saved
     */
    fun getMyJSONObjectNonNull(paramName: String): MyJSONObject {
        return getMyJSONObject(paramName)!!
    }

    /**
     * @return MyJSONObject saved under provided paramName
     * @param paramName is int reference to String, which represents name, under which is the myJSONObject saved
     */
    fun getMyJSONObjectNonNull(paramName: Int): MyJSONObject {
        return getMyJSONObject(paramName)!!
    }

    /**
     * Adds String to jsonObject
     *
     * @param paramName is int reference to String, which represents name, under which the value should be saved
     * @param value is String, which will be saved to jsonObject under provided paramName
     */
    fun addString(paramName: String, value: String) {
        get().put(paramName, value)
    }

    /**
     * Adds String to jsonObject
     *
     * @param paramName is int reference to String, which represents name, under which the value should be saved
     * @param value is String, which will be saved to jsonObject under provided paramName
     */
    fun addString(paramName: Int, value: String) {
        addString(this.myString.fromResources(paramName), value)
    }

    /**
     * Adds Boolean to jsonObject
     *
     * @param paramName is String, which represents name, under which the value should be saved
     * @param value is Boolean, which will be saved to jsonObject under provided paramName
     */
    fun addBoolean(paramName: String, value: Boolean) {
        get().put(paramName, value)
    }

    /**
     * Adds Boolean to jsonObject
     *
     * @param paramName is int reference to String, which represents name, under which the value should be saved
     * @param value is int reference to Boolean, which will be saved to jsonObject under provided paramName
     */
    fun addBoolean(paramName: Int, value: Boolean) {
        addBoolean(this.myString.fromResources(paramName), value)
    }

    /**
     * Adds Int to jsonObject
     *
     * @param paramName is String, which represents name, under which the value should be saved
     * @param value is Int, which will be saved to jsonObject under provided paramName
     */
    fun addInt(paramName: String, value: Int) {
        get().put(paramName, value)
    }

    /**
     * Adds Int to jsonObject
     *
     * @param paramName is int reference to String, which represents name, under which the value should be saved
     * @param value is Int, which will be saved to jsonObject under provided paramName
     */
    fun addInt(paramName: Int, value: Int) {
        addInt(this.myString.fromResources(paramName), value)
    }

    /**
     * Adds Double to jsonObject
     *
     * @param paramName is String, which represents name, under which the value should be saved
     * @param value is Double, which will be saved to jsonObject under provided paramName
     */
    fun addDouble(paramName: String, value: Double) {
        get().put(paramName, value)
    }

    /**
     * Adds Double to jsonObject
     *
     * @param paramName is int reference to String, which represents name, under which the value should be saved
     * @param value is Double, which will be saved to jsonObject under provided paramName
     */
    fun addDouble(paramName: Int, value: Double) {
        addDouble(this.myString.fromResources(paramName), value)
    }

    /**
     * Adds Long to jsonObject
     *
     * @param paramName is String, which represents name, under which the value should be saved
     * @param value is Long, which will be saved to jsonObject under provided paramName
     */
    fun addLong(paramName: String, value: Long) {
        get().put(paramName, value)
    }

    /**
     * Adds Long to jsonObject
     *
     * @param paramName is int reference to String, which represents name, under which the value should be saved
     * @param value is Long, which will be saved to jsonObject under provided paramName
     */
    fun addLong(paramName: Int, value: Long) {
        addLong(this.myString.fromResources(paramName), value)
    }

    /**
     * Adds JSONObject to jsonObject
     *
     * @param paramName is String, which represents name, under which the value should be saved
     * @param value is JSONObject, which will be saved to jsonObject under provided paramName
     */
    fun addJSONObject(paramName: String, value: JSONObject) {
        get().put(paramName, value)
    }

    /**
     * Adds JSONObject to jsonObject
     *
     * @param paramName is int reference to String, which represents name, under which the value should be saved
     * @param value is JSONObject, which will be saved to jsonObject under provided paramName
     */
    fun addJSONObject(paramName: Int, value: JSONObject) {
        addJSONObject(this.myString.fromResources(paramName), value)
    }

    /**
     * Adds MyJSONObject to jsonObject
     *
     * @param paramName is String, which represents name, under which the value should be saved
     * @param value is MyJSONObject, which will be saved to jsonObject under provided paramName
     */
    fun addMyJSONObject(paramName: String, value: MyJSONObject) {
        addJSONObject(paramName, value.get())
    }

    /**
     * Adds MyJSONObject to jsonObject
     *
     * @param paramName is int reference to String, which represents name, under which the value should be saved
     * @param value is MyJSONObject, which will be saved to jsonObject under provided paramName
     */
    fun addMyJSONObject(paramName: Int, value: MyJSONObject) {
        addJSONObject(paramName, value.get())
    }

    /**
     * Adds JSONArray to jsonObject
     *
     * @param paramName is String, which represents name, under which the value should be saved
     * @param value is JSONArray, which will be saved to jsonObject under provided paramName
     */
    fun addJSONArray(paramName: String, value: JSONArray) {
        get().put(paramName, value)
    }

    /**
     * Adds JSONArray to jsonObject
     *
     * @param paramName is int reference to String, which represents name, under which the value should be saved
     * @param value is JSONArray, which will be saved to jsonObject under provided paramName
     */
    fun addJSONArray(paramName: Int, value: JSONArray) {
        addJSONArray(this.myString.fromResources(paramName), value)
    }

    /**
     * Adds MyJSONArray to jsonObject
     *
     * @param paramName is String, which represents name, under which the value should be saved
     * @param value is MyJSONArray, which will be saved to jsonObject under provided paramName
     */
    fun addMyJSONArray(paramName: String, value: MyJSONArray) {
        addJSONArray(paramName, value.get())
    }

    /**
     * Adds MyJSONArray to jsonObject
     *
     * @param paramName is int reference to String, which represents name, under which the value should be saved
     * @param value is MyJSONArray, which will be saved to jsonObject under provided paramName
     */
    fun addMyJSONArray(paramName: Int, value: MyJSONArray) {
        addJSONArray(paramName, value.get())
    }

    /**
     * @return if the jsonObject has value, saved under provided name
     * @param paramName is String, which holds name of the param, which should be in jsonObject
     */
    fun containsParam(paramName: String): Boolean {
        return get().has(paramName)
    }

    /**
     * @return if the jsonObject has value, saved under provided name
     * @param paramName is int reference to String, which holds name of the param, which should be in jsonObject
     */
    fun containsParam(paramName: Int): Boolean {
        return containsParam(this.myString.fromResources(paramName))
    }
}