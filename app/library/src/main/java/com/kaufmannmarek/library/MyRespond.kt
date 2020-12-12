@file:Suppress("unused", "PrivatePropertyName")

package com.kaufmannmarek.library

/**
 * Creates an interface, which serves in case, when you need to call origin activity method, when the tasks in different class are done. To make this working, you must implement this abstract class in origin activity with method onRespondReceived. It serves as filter at the end of the process. Then you must pass this implementation to method of the class, where the other tasks are performed. Once the tasks are done and you got it´s result, you must call setTaskResult, setTaskResultOK or setTaskResultBad. Afterwards the method onRespondReceived is called. You can implement this method as you need
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class MyRespond(private val requestCode: Int) {
    private var resultCode: Int? = null
    private val RESULT_OK = -1
    private val RESULT_BAD = 1

    /**
     * Sets result of the task
     *
     * @param resultCode represents the result of the task
     * @param data are data, that needs to be passed to origin activity
     */
    private fun setTaskResult(resultCode: Int, data: Any?) {
        this.resultCode = resultCode
        if(resultIsOk())
            onSuccess(getRequestCode(), data)
        else
            onFailure(getRequestCode(), data)
    }

    /**
     * @return requestCode of the task
     */
    fun getRequestCode(): Int {
        return requestCode
    }

    /**
     * Sets OK result of the task and pass the data
     *
     * @param data are data, that needs to be passed to origin activity
     */
    fun setTaskResultOK(data: Any?) {
        setTaskResult(RESULT_OK, data)
    }

    /**
     * Sets OK result of the task
     */
    fun setTaskResultOK() {
        setTaskResultOK(null)
    }

    /**
     * Sets BAD result of the task and pass the data
     *
     * @param data are data, that needs to be passed to origin activity
     */
    fun setTaskResultBad(data: Any?) {
        setTaskResult(RESULT_BAD, data)
    }

    /**
     * Sets BAD result of the task
     */
    fun setTaskResultBad() {
        setTaskResultBad(null)
    }

    /**
     * @return if the task has end up with OK code
     */
    fun resultIsOk(): Boolean {
        return this.resultCode == this.RESULT_OK
    }

    /**
     * @return if the task has end up with BAD code
     */
    fun resultIsBad(): Boolean {
        return !resultIsOk()
    }


    /**
     * This method is called in distinct class where the task is performed in case of success. This method must be implemented in origin class. You may implement it as you need.
     *
     * @param requestCode is code of performed operation. This is the argument, you have set before the passing the class instance to different class
     * @param data is optional and it may contain any values or object. Can be null
     */
    abstract fun onSuccess(requestCode: Int, data: Any?)

    /**
     * This method is called in distinct class where the task is performed in case of failure. This method must be implemented in origin class. You may implement it as you need.
     *
     * @param requestCode is code of performed operation. This is the argument, you have set before the passing the class instance to different class
     * @param data is optional and it may contain any values or object. Can be null
     */
    abstract fun onFailure(requestCode: Int, data: Any?)
}