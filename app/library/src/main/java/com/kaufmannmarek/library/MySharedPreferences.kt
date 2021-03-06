@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.kaufmannmarek.library

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * Creates database of SharedPreferences type with all necessary functions.
 *
 * @param context of currently active activity
 * @param databaseName is String, which represents name of the database
 * @param encrypt determine if the database will be encrypted
 * @param encryptKey is key that will be used encrypt the database. If null, then the app will generate the key. The value of param encrypt must be true to encrypt the database
 */
open class MySharedPreferences private constructor(
    private val context: Context,
    databaseName: String,
    encrypt: Boolean,
    encryptKey: String?
) {

    private val sharedPreferences: SharedPreferences = when (encrypt) {
        true -> EncryptedSharedPreferences.create(
            getContext(),
            databaseName,
            when (encryptKey == null) {
                true -> MasterKey.Builder(getContext()).setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build()
                false -> MasterKey.Builder(getContext(), encryptKey)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
            },
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        false -> getContext().getSharedPreferences(databaseName, Context.MODE_PRIVATE)
    }

    /**
     * Creates database of SharedPreferences type with all necessary functions.
     *
     * @param context of currently active activity
     * @param databaseName is String, which represents name of the database
     * @param encrypt determine if the database will be encrypted
     * @param encryptKey is key that will be used encrypt the database. If null, then the app will generate the key. The value of param encrypt must be true to encrypt the database
     */
    private constructor(
        context: Context,
        databaseName: Int,
        encrypt: Boolean,
        encryptKey: String?
    ) : this(context, context.getString(databaseName), encrypt, encryptKey)

    /**
     * Creates encrypted database of SharedPreferences type with all necessary functions.
     *
     * @param context of currently active activity
     * @param databaseName is String, which represents name of the database
     * @param encryptKey is key that will be used encrypt the database. If null, then the app will generate the key
     */
    constructor(context: Context, databaseName: String, encryptKey: String?) : this(
        context,
        databaseName,
        true,
        encryptKey
    )

    /**
     * Creates encrypted database of SharedPreferences type with all necessary functions.
     *
     * @param context of currently active activity
     * @param databaseName is int reference to String, which represents name of the database
     * @param encryptKey is key that will be used encrypt the database. If null, then the app will generate the key
     */
    constructor(context: Context, databaseName: Int, encryptKey: String?) : this(
        context,
        databaseName,
        true,
        encryptKey
    )

    /**
     * Creates not encrypted database of SharedPreferences type with all necessary functions.
     *
     * @param context of currently active activity
     * @param databaseName is String, which represents name of the database
     */
    constructor(context: Context, databaseName: String) : this(context, databaseName, false, null)

    /**
     * Creates not encrypted database of SharedPreferences type with all necessary functions
     *
     * @param context of currently active activity
     * @param databaseName is int reference to String, which represents name of the database
     */
    constructor(context: Context, databaseName: Int) : this(
        context,
        MyString(context).fromResources(databaseName),
        false,
        null
    )

    private var editor: SharedPreferences.Editor? = null

    /**
     * @return all values from this database
     * @param V is type of values saved under string key values
     */
    fun <V> getAll(): Map<String, V> {
        @Suppress("UNCHECKED_CAST")
        return getSharedPreferences().all as Map<String, V>
    }

    /**
     * @return all keys found in this database
     */
    fun getKeys(): MutableSet<String> {
        return getSharedPreferences().all.keys
    }

    /**
     * @return reference of sharedPreference
     */
    fun getSharedPreferences(): SharedPreferences {
        return this.sharedPreferences
    }

    /**
     * Will apply changes in database
     */
    fun applyChanges() {
        getEditor().apply()
    }

    /**
     * Print all elements in database. Please note that all database items must use same type
     */
    fun <V> printDatabase() {
        val data = getAll<V>()
        Log.d("debug", "Begin of database")
        for ((itemIndex, key) in getKeys().withIndex()) {
            Log.d("debug", "item " + itemIndex + " - key: " + key + " - value: " + data[key])
        }
        Log.d("debug", "End of database")
    }

    /**
     * @return context of original activity
     */
    fun getContext(): Context {
        return this.context
    }

    /**
     * Saves int value under provided key
     *
     * @param paramName is key, under which the value will be stored
     * @param value is value to store
     * @param apply is condition. If true, it will apply changes to database. Else you must call applyChanges at the of the process
     */
    fun setInt(paramName: String, value: Int, apply: Boolean) {
        getEditor().putInt(paramName, value)
        if (apply)
            applyChanges()
    }

    /**
     * Saves int value under provided key. It will not apply changes, you must call applyChanges to save value
     *
     * @param paramName is key, under which the value will be stored
     * @param value is value to store
     */
    fun setInt(paramName: String, value: Int) {
        setInt(paramName, value, false)
    }

    /**
     * Saves int value under provided key. It will not apply changes, you must call applyChanges to save value
     *
     * @param paramName is int reference to String, which is key, under which the value will be stored
     * @param value is value to store
     */
    fun setInt(paramName: Int, value: Int) {
        setInt(getContext().getString(paramName), value)
    }

    /**
     * @return saved int value under provided String
     * @param paramName is key, under which is the value stored
     */
    fun getInt(paramName: String): Int {
        return getSharedPreferences().getInt(paramName, 0)
    }

    /**
     * Removes value save under String
     *
     * @param paramName is String, under which is the value saved
     */
    fun removeParam(paramName: String) {
        getEditor().remove(paramName)
    }

    /**
     * Removes value save under String, which is retrieved from string resources int
     *
     * @param paramName is int reference to String value, under which is the value saved
     */
    fun removeParam(paramName: Int) {
        removeParam(getContext().getString(paramName))
    }

    /**
     * @return int saved under provided String. If there is no value saved, 0 will be returned
     * @param paramName is String, under which is the int stored
     */
    fun getInt(paramName: Int): Int {
        return getInt(getContext().getString(paramName))
    }

    /**
     * return long save under provided String. It there is no value saved, 0 will be returned
     * @param paramName is String, under which is the long saved
     */
    fun getLong(paramName: String): Long {
        return getSharedPreferences().getLong(paramName, 0)
    }

    /**
     * return long save under provided String, which is obtained by the int reference. It there is no value saved, 0 will be returned
     * @param paramName is int reference to String value, under which is the value saved
     */
    fun getLong(paramName: Int): Long {
        return getLong(getContext().getString(paramName))
    }

    /**
     * Saves long value under provided key. It will not apply changes, you must call applyChanges to save value
     *
     * @param paramName is key, under which the value will be stored
     * @param value is value to store
     */
    fun setLong(paramName: String, value: Long) {
        getEditor().putLong(paramName, value)
    }

    /**
     * Saves long value under provided key. It will not apply changes, you must call applyChanges to save value
     *
     * @param paramName is int reference to String, which is key, under which the value will be stored
     * @param value is value to store
     */
    fun setLong(paramName: Int, value: Long) {
        setLong(getContext().getString(paramName), value)
    }

    /**
     * @return String saved under provided key. If there is no value, null will be returned
     * @param paramName is key, under which is the value stored
     */
    fun getStringNull(paramName: String): String? {
        return getSharedPreferences().getString(paramName, null)
    }

    /**
     * @return String saved under provided key. If there is no value, null will be returned
     * @param paramName is int reference to String, which is key, under which is the value stored
     */
    fun getStringNull(paramName: Int): String? {
        return getStringNull(getContext().getString(paramName))
    }

    /**
     * @return String saved under provided String. If there is no value saved, empty String will be returned
     * @param paramName is String, under which is the String stored
     */
    fun getString(paramName: String): String {
        return getSharedPreferences().getString(paramName, "")!!
    }

    /**
     * @return String saved under provided String, If there is no value saved, empty String will be returned
     * @param paramName is int reference to String, under which is the String stored
     */
    fun getString(paramName: Int): String {
        return getString(getContext().getString(paramName))
    }

    /**
     * Saves value under provided key
     *
     * @param paramName is key, under which the value will be stored
     * @param value is value to be stored
     * @param apply is condition. If true, it will apply changes. Else you must call applyChanges at the of the process
     */
    fun setString(paramName: String, value: String, apply: Boolean) {
        getEditor().putString(paramName, value)
        if (apply)
            applyChanges()
    }

    /**
     * Saves value under provided key
     *
     * @param paramName is key, under which the value will be stored
     * @param value is value to be stored
     */
    fun setString(paramName: String, value: String) {
        setString(paramName, value, false)
    }

    /**
     * Saves String under provided key
     *
     * @param paramName is int reference to String, which is key, under which the value will be stored
     * @param value is value to be stored
     */
    fun setString(paramName: Int, value: String) {
        setString(getContext().getString(paramName), value)
    }

    /**
     * Saves boolean under provided key
     *
     * @param paramName is key, under which the value will be stored
     * @param value is value to be stored
     */
    fun setBoolean(paramName: String, value: Boolean) {
        getEditor().putBoolean(paramName, value)
    }

    /**
     * Saves boolean under provided key
     *
     * @param paramName is int reference to String, which is key, under which the value will be stored
     * @param value is value to be stored
     */
    fun setBoolean(paramName: Int, value: Boolean) {
        setBoolean(getContext().getString(paramName), value)
    }

    /**
     * @return boolean value saved under provided key. If there is no value saved, it will return false.
     *
     * @param paramName is key, under which is the value saved
     */
    fun getBoolean(paramName: String): Boolean {
        return getSharedPreferences().getBoolean(paramName, false)
    }

    /**
     * @return boolean value saved under provided key, which is obtained by provided int reference. If there is no value saved, it will return false.
     *
     * @param paramName is int reference to String, under which is the value saved
     */
    fun getBoolean(paramName: Int): Boolean {
        return getBoolean(getContext().getString(paramName))
    }

    /**
     * @return editor used for editing sharedPreferences. Do not forget to use apply at the end
     */
    @SuppressLint("CommitPrefEdits")
    fun getEditor(): SharedPreferences.Editor {
        if (this.editor == null)
            this.editor = getSharedPreferences().edit()
        return this.editor!!
    }

    /**
     * Clear all data saved in this sharedPreferences database
     *
     * @param commit if true, the database will clear immediately
     */
    fun clear(commit: Boolean) {
        getEditor().clear()
        if (commit)
            applyChanges()
    }

    /**
     * Clear all data saved in this sharedPreferences database. Will not commit changes
     */
    fun clear() {
        clear(false)
    }
}