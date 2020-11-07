package com.kaufmannmarek.library

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

@Suppress("unused", "MemberVisibilityCanBePrivate")
/**
 * Create MyJSONArray object from already existing JSONArray, which simplify work with jsonArray
 *
 * @param context of currently displayed activity
 * @param jsonArray is already existing jsonArray
 */
class MyJSONArray(private val context: Context, private val jsonArray: JSONArray) {

    /**
     * Create MyJSONArray empty object, which simplify work with jsonArray
     *
     * @param context of currently displayed activity
     */
    constructor(context: Context) : this(context, JSONArray())

    /**
     * Create MyJSONArray object from already existing JSONArray, which simplify work with jsonArray
     *
     * @param context of currently displayed activity
     * @param jsonArrayAsString is jsonArray in String format
     */
    constructor(context: Context, jsonArrayAsString: String) : this(
        context,
        JSONArray(jsonArrayAsString)
    )

    /**
     * @return context used to create this object
     */
    fun getContext(): Context {
        return this.context
    }

    /**
     * @return jsonArray
     */
    fun get(): JSONArray {
        return this.jsonArray
    }

    /**
     * @return jsonArray as String
     */
    fun asString(): String {
        return get().toString()
    }

    /**
     * Remove element at provided position in jsonArray
     *
     * @param elementPosition is element position in jsonArray, which will be removed
     */
    fun removeElement(elementPosition: Int) {
        try {
            get().remove(elementPosition)
        } catch (e: Exception) {
            Log.e(
                "MyJSONArray",
                "Unable to remove, because provided index does not belong to array"
            )
        }
    }

    /**
     * @return number of values saved in jsonArray
     */
    fun length(): Int {
        return get().length()
    }

    /**
     * @return position of last item in jsonArray
     */
    fun getLastIndex(): Int {
        return length() - 1
    }

    /**
     * @return String value saved in jsonArray in provided position. If the position does not exist or the position does not contain value of type String, null will be returned
     * @param stringPosition is position of the String in jsonArray
     */
    fun getString(stringPosition: Int): String? {
        return try {
            get().getString(stringPosition)
        } catch (exception: java.lang.IndexOutOfBoundsException) {
            Log.e(
                "MyJSONArray",
                "Position " + stringPosition + " does not belong not in this jsonArray. Last value is in position with index " + getLastIndex()
            )
            return null
        } catch (exception: JSONException) {
            Log.e(
                "MyJSONArray",
                "Value at position $stringPosition is not of type String"
            )
            return null
        }
    }

    /**
     * @return String value saved in jsonArray in provided position. Be sure to use this method only in case, when you are sure, that value under provided position exists and it´s type is String
     * @param stringPosition is position of the String in jsonArray
     */
    fun getStringNonNull(stringPosition: Int): String {
        return getString(stringPosition)!!
    }

    /**
     * @return Boolean value saved in jsonArray in provided position. If the position does not exist or the position does not contain value of type Boolean, null will be returned
     * @param booleanPosition is position of the Boolean in jsonArray
     */
    fun getBoolean(booleanPosition: Int): Boolean? {
        return try {
            get().getBoolean(booleanPosition)
        } catch (exception: java.lang.IndexOutOfBoundsException) {
            Log.e(
                "MyJSONArray",
                "Position " + booleanPosition + " does not belong not in this jsonArray. Last value is in position with index " + getLastIndex()
            )
            return null
        } catch (exception: JSONException) {
            Log.e(
                "MyJSONArray",
                "Value at position $booleanPosition is not of type Boolean"
            )
            return null
        }
    }

    /**
     * @return Boolean value saved in jsonArray in provided position. Be sure to use this method only in case, when you are sure, that value under provided position exists and it´s type is Boolean
     * @param booleanPosition is position of the Boolean in jsonArray
     */
    fun getBooleanNonNull(booleanPosition: Int): Boolean {
        return getBoolean(booleanPosition)!!
    }

    /**
     * @return Int value saved in jsonArray in provided position. If the position does not exist or the position does not contain value of type Int, null will be returned
     * @param intPosition is position of the Boolean in jsonArray
     */
    fun getInt(intPosition: Int): Int? {
        return try {
            get().getInt(intPosition)
        } catch (exception: java.lang.IndexOutOfBoundsException) {
            Log.e(
                "MyJSONArray",
                "Position " + intPosition + " does not belong not in this jsonArray. Last value is in position with index " + getLastIndex()
            )
            return null
        } catch (exception: JSONException) {
            Log.e(
                "MyJSONArray",
                "Value at position $intPosition is not of type Int"
            )
            return null
        }
    }

    /**
     * @return Int value saved in jsonArray in provided position. Be sure to use this method only in case, when you are sure, that value under provided position exists and it´s type is Int
     * @param intPosition is position of the Int in jsonArray
     */
    fun getIntNonNull(intPosition: Int): Int {
        return getInt(intPosition)!!
    }

    /**
     * @return Long value saved in jsonArray in provided position. If the position does not exist or the position does not contain value of type Long, null will be returned
     * @param longPosition is position of the Long in jsonArray
     */
    fun getLong(longPosition: Int): Long? {
        return try {
            get().getLong(longPosition)
        } catch (exception: java.lang.IndexOutOfBoundsException) {
            Log.e(
                "MyJSONArray",
                "Position " + longPosition + " does not belong not in this jsonArray. Last value is in position with index " + getLastIndex()
            )
            return null
        } catch (exception: JSONException) {
            Log.e(
                "MyJSONArray",
                "Value at position $longPosition is not of type Long"
            )
            return null
        }
    }

    /**
     * @return Long value saved in jsonArray in provided position. Be sure to use this method only in case, when you are sure, that value under provided position exists and it´s type is Long
     * @param longPosition is position of the Long in jsonArray
     */
    fun getLongNonNull(longPosition: Int): Long {
        return getLong(longPosition)!!
    }

    /**
     * @return Double value saved in jsonArray in provided position. If the position does not exist or the position does not contain value of type Double, null will be returned
     * @param doublePosition is position of the Double in jsonArray
     */
    fun getDouble(doublePosition: Int): Double? {
        return try {
            get().getDouble(doublePosition)
        } catch (exception: java.lang.IndexOutOfBoundsException) {
            Log.e(
                "MyJSONArray",
                "Position " + doublePosition + " does not belong not in this jsonArray. Last value is in position with index " + getLastIndex()
            )
            return null
        } catch (exception: JSONException) {
            Log.e(
                "MyJSONArray",
                "Value at position $doublePosition is not of type Double"
            )
            return null
        }

    }

    /**
     * @return Double value saved in jsonArray in provided position. Be sure to use this method only in case, when you are sure, that value under provided position exists and it´s type is Double
     * @param doublePosition is position of the Double in jsonArray
     */
    fun getDoubleNonNull(doublePosition: Int): Double {
        return getDouble(doublePosition)!!
    }

    /**
     * @return JSONArray value saved in jsonArray in provided position. If the position does not exist or the position does not contain value of type JSONArray, null will be returned
     * @param jsonArrayPosition is position of the JSONArray in jsonArray
     */
    fun getJSONArray(jsonArrayPosition: Int): JSONArray? {
        return try {
            get().getJSONArray(jsonArrayPosition)
        } catch (exception: java.lang.IndexOutOfBoundsException) {
            Log.e(
                "MyJSONArray",
                "Position " + jsonArrayPosition + " does not belong not in this jsonArray. Last value is in position with index " + getLastIndex()
            )
            return null
        } catch (exception: JSONException) {
            Log.e(
                "MyJSONArray",
                "Value at position $jsonArrayPosition is not of type JSONArray"
            )
            return null
        }
    }

    /**
     * @return JSONArray value saved in jsonArray in provided position. Be sure to use this method only in case, when you are sure, that value under provided position exists and it´s type is JSONArray
     * @param jsonArrayPosition is position of the JSONArray in jsonArray
     */
    fun getJSONArrayNonNull(jsonArrayPosition: Int): JSONArray {
        return getJSONArray(jsonArrayPosition)!!
    }

    /**
     * @return JSONArray as MyJSONArray saved in jsonArray in provided position. If the position does not exist or the position does not contain value of type JSONArray, null will be returned
     * @param jsonArrayPosition is position of the JSONArray in jsonArray
     */
    fun getMyJSONArray(jsonArrayPosition: Int): MyJSONArray? {
        return try {
            MyJSONArray(getContext(), get().getJSONArray(jsonArrayPosition))
        } catch (exception: java.lang.IndexOutOfBoundsException) {
            Log.e(
                "MyJSONArray",
                "Position " + jsonArrayPosition + " does not belong not in this jsonArray. Last value is in position with index " + getLastIndex()
            )
            return null
        } catch (exception: JSONException) {
            Log.e(
                "MyJSONArray",
                "Value at position $jsonArrayPosition is not of type JSONArray"
            )
            return null
        }
    }

    /**
     * @return JSONArray as MyJSONArray saved in jsonArray in provided position. Be sure to use this method only in case, when you are sure, that value under provided position exists and it´s type is JSONArray
     * @param jsonArrayPosition is position of the JSONArray in jsonArray
     */
    fun getMyJSONArrayNonNull(jsonArrayPosition: Int): MyJSONArray {
        return getMyJSONArray(jsonArrayPosition)!!
    }

    /**
     * Adds String to jsonArray in provided position
     *
     * @param string is value to be added
     * @param position is position in jsonArray, where the value will be put. If the position is higher than last value position, then positions between those two numbers will be filled in nulls. If the position contains a value, it will be overwritten
     */
    fun addString(string: String, position: Int) {
        get().put(position, string)
    }

    /**
     * Adds String to jsonArray a put it to position, which index is length of jsonArray
     *
     * @param string is value to be added
     */
    fun addString(string: String) {
        get().put(string)
    }

    /**
     * Adds Int to jsonArray in provided position
     *
     * @param int is value to be added
     * @param position is position in jsonArray, where the value will be put. If the position is higher than last value position, then positions between those two numbers will be filled in nulls. If the position contains a value, it will be overwritten
     */
    fun addInt(int: Int, position: Int) {
        get().put(position, int)
    }

    /**
     * Adds Int to jsonArray a put it to position, which index is length of jsonArray
     *
     * @param int is value to be added
     */
    fun addInt(int: Int) {
        get().put(int)
    }

    /**
     * Adds Double to jsonArray in provided position
     *
     * @param double is value to be added
     * @param position is position in jsonArray, where the value will be put. If the position is higher than last value position, then positions between those two numbers will be filled in nulls. If the position contains a value, it will be overwritten
     */
    fun addDouble(double: Double, position: Int) {
        get().put(position, double)
    }

    /**
     * Adds Double to jsonArray a put it to position, which index is length of jsonArray
     *
     * @param double is value to be added
     */
    fun addDouble(double: Double) {
        get().put(double)
    }

    /**
     * Adds JSONArray to jsonArray in provided position
     *
     * @param jsonArray is value to be added
     * @param position is position in jsonArray, where the value will be put. If the position is higher than last value position, then positions between those two numbers will be filled in nulls. If the position contains a value, it will be overwritten
     */
    fun addJSONArray(jsonArray: JSONArray, position: Int) {
        get().put(position, jsonArray)
    }

    /**
     * Adds JSONArray to jsonArray a put it to position, which index is length of jsonArray
     *
     * @param jsonArray is value to be added
     */
    fun addJSONArray(jsonArray: JSONArray) {
        get().put(jsonArray)
    }

    /**
     * Adds JSONArray to jsonArray in provided position
     *
     * @param myJSONArray is value to be added
     * @param position is position in jsonArray, where the value will be put. If the position is higher than last value position, then positions between those two numbers will be filled in nulls. If the position contains a value, it will be overwritten
     */
    fun addMyJSONArray(myJSONArray: MyJSONArray, position: Int) {
        get().put(position, myJSONArray.get())
    }

    /**
     * Adds MyJSONArray to jsonArray a put it to position, which index is length of jsonArray
     *
     * @param myJSONArray is value to be added
     */
    fun addMyJSONArray(myJSONArray: MyJSONArray) {
        get().put(myJSONArray.get())
    }

    /**
     * Adds JSONObject to jsonArray in provided position
     *
     * @param jsonObject is value to be added
     * @param position is position in jsonArray, where the value will be put. If the position is higher than last value position, then positions between those two numbers will be filled in nulls. If the position contains a value, it will be overwritten
     */
    fun addJSONObject(jsonObject: JSONObject, position: Int) {
        get().put(position, jsonObject)
    }

    /**
     * Adds JSONObject to jsonArray a put it to position, which index is length of jsonArray
     *
     * @param jsonObject is value to be added
     */
    fun addJSONObject(jsonObject: JSONObject) {
        get().put(jsonObject)
    }

    /**
     * Adds MyJSONObject to jsonArray in provided position
     *
     * @param myJSONObject is value to be added
     * @param position is position in jsonArray, where the value will be put. If the position is higher than last value position, then positions between those two numbers will be filled in nulls. If the position contains a value, it will be overwritten
     */
    fun addMyJSONObject(myJSONObject: MyJSONObject, position: Int) {
        get().put(position, myJSONObject.get())
    }

    /**
     * Adds MyJSONObject to jsonArray a put it to position, which index is length of jsonArray
     *
     * @param myJSONObject is value to be added
     */
    fun addMyJSONObject(myJSONObject: MyJSONObject) {
        get().put(myJSONObject.get())
    }
}