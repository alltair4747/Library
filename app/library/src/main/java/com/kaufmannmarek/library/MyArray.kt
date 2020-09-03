package com.kaufmannmarek.library

import android.content.Context

class MyArray(private val context: Context, private val arrayReference: Int) {

    /**
     * @return arrayList which is formed from array
     */
    fun toArrayList(): ArrayList<String> {
        val arrayList = ArrayList<String>()
        val array = getArray()
        for (index in array.indices) {
            arrayList.add(array[index])
        }
        return arrayList
    }

    private fun getArray(): Array<String> {
        return this.context.resources.getStringArray(this.arrayReference)
    }
}