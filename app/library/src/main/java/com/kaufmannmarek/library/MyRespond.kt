@file:Suppress("unused")

package com.kaufmannmarek.library

interface MyRespond {

    /**
     * @param code is result of operation, where 0 means success
     */
    fun respond(code: Int)
}