@file:Suppress("unused")

package com.kaufmannmarek.library

/**
 * Creates an interface, which serves in case, when you need to call origin activity method, when the tasks in different class are done. To make this working, you must implement this interface in origin activity with method onRespondReceived. Then you must pass this implementation to method of the class, where the other tasks are performed. Once the tasks are done and you got it´s result, you must call onRespondReceived by using passed instance of the interface.
 */
interface MyRespond {

    /**
     * This method should be called in other classes from where the respond is expected. Also it is important to configure this method in original activity or fragment
     *
     * @param requestCode is code of performed operation
     * @param resultCode is result of operation, where 0 means success
     * @param data is optional and it may contain any values or object
     */
    fun onRespondReceived(requestCode: Int, resultCode: Int, data: Any?)
}