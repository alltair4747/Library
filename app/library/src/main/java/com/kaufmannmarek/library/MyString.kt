@file:Suppress("unused", "MemberVisibilityCanBePrivate")
package com.kaufmannmarek.library

import android.content.Context
import kotlin.random.Random

class MyString(private val context: Context) {

    /**
     * Generates random string with provided length
     *
     * @return random String with defined length
     * @param length defines, how many chars will returned String contain
     */

    @Suppress("SpellCheckingInspection")
    fun generateNewString(length: Int): String {
        val range = "abcdefghijklmnopqrstuvwxyz123456789"
        val string = StringBuilder()
        for (index in 0 until length) {
            string.append(range[Random.nextInt(0, range.length - 1)])
        }
        return string.toString()
    }

    /**
     * @return unique String among provided arrayList of Strings
     *
     * @param length is length of returned String
     * @param stringList is collection of Strings which should not match returned String
     */
    fun getUniqueString(length: Int, stringList: ArrayList<String>): String {
        val uniqueId = generateNewString(length)
        for (id in stringList) {
            if (id == uniqueId)
                return getUniqueString(length, stringList)
        }
        return uniqueId
    }

    /**
     * @return String value from provided int reference
     * @param paramName is reference to string
     */
    fun fromResources(paramName: Int): String {
        return this.context.getString(paramName)
    }

}