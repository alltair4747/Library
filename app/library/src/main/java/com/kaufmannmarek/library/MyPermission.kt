@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.kaufmannmarek.library

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat

/**
 * Creates class, which simplify permission requests and provides several useful methods. Start by calling method addPermission as many times you want. !Warning! Be sure to add permissions you are asking for to your project manifest. Otherwise it will not work !Warning! Once you are done, call startPermissionsRequests. It is necessary to implement onRequestPermissionsResult in default activity. From there you should call onResult method top handle users response
 *
 * @param context of currently used activity
 */
class MyPermission(private val context: Context) {

    private lateinit var permissions: ArrayList<Permission>
    private var currentPermissionIndex = -1
    private lateinit var currentPermission: Permission
    private var started = false

    /**
     * Call this method in activity´s onRequestPermissionsResult method. It handle the result of user choice
     *
     * @param grantResults is param from onRequestPermissionsResult method in your activity
     */
    fun onResult(
        grantResults: IntArray
    ) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            this.currentPermission.granted = true
        else {
            if (this.currentPermission.hasOnDeclineMessage()) {
                val notificationDialog = NotificationDialog(
                    this.context,
                    R.string.permissionNotGranted,
                    this.currentPermission.onDeclineMessage!!,
                    R.drawable.ic_one_way
                )
                notificationDialog.getRightButton().setOnClickListener {
                    notificationDialog.dismiss()
                    requestNextPermission()
                }
            } else requestNextPermission()
        }
    }

    /**
     * @return if the provided permission was granted. Returns false if provided string is not valid permission
     * @param permission is examined permission
     */
    fun permissionWasGranted(permission: String): Boolean {
        return try {
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        } catch (e: Exception) {
            false
        }
    }

    /**
     * @return if the provided permission was not granted. Returns true if provided string is not valid permission
     * @param permission is examined permission
     */
    fun permissionWasNotGranted(permission: String): Boolean {
        return !permissionWasGranted(permission)
    }

    /**
     *
     * If there is another permission to be asked for, it will do so. Otherwise will do nothing
     */
    private fun requestNextPermission() {
        this.currentPermissionIndex++
        if (this.currentPermissionIndex < this.permissions.size) {
            this.currentPermission = this.permissions[this.currentPermissionIndex]
            if (this.currentPermission.hasExplanationMessage()) {
                val notificationDialog = NotificationDialog(
                    this.context,
                    R.string.permissionRequest,
                    this.currentPermission.explanationMessage!!,
                    R.drawable.ic_question_mark
                )
                notificationDialog.getRightButton().setOnClickListener {
                    notificationDialog.dismiss()
                    requestPermissions(
                        this.context as Activity,
                        arrayOf(this.currentPermission.permission),
                        47
                    )
                }
            } else requestPermissions(
                this.context as Activity,
                arrayOf(this.currentPermission.permission),
                47
            )
        }
    }

    /**
     * Adds permission request to queue. It can display messages
     *
     * @param permission is permission to be requested
     * @param explanationMessage is String, which will displayed before permission dialog it self. If is null, no message will be displayed
     * @param onDeclineMessage is String, which will displayed in case of declining the permission. If is null, no message will be displayed
     */
    fun addPermission(permission: String, explanationMessage: String?, onDeclineMessage: String?) {
        if (!this.started) {
            if (!this::permissions.isInitialized)
                this.permissions = ArrayList()
            this.permissions.add(Permission(permission, explanationMessage, onDeclineMessage))
        }
    }

    /**
     * Adds permission request to queue with no messages
     *
     * @param permission is permission to be requested
     */
    fun addPermission(permission: String) {
        addPermission(permission, null, null)
    }

    /**
     * Call this method after adding all permission to queue. The process of requesting all required permissions will start
     */
    fun startPermissionsRequests() {
        this.started = true
        if (this.permissions.size > 0)
            requestNextPermission()
    }

    /**
     * Adds camera permission request to queue. It can display messages.
     *
     * @param explanationMessage is String, which will displayed before permission dialog it self. If is null, no message will be displayed
     * @param onDeclineMessage is String, which will displayed in case of declining the permission. If is null, no message will be displayed
     * @param requestNow if true, no more permissions will be added and the process of retrieving the permissions will start
     */
    fun requestCameraPermission(
        explanationMessage: String?,
        onDeclineMessage: String?,
        requestNow: Boolean
    ) {
        addPermission(Manifest.permission.CAMERA, explanationMessage, onDeclineMessage)
        if (requestNow)
            startPermissionsRequests()
    }

    /**
     * Adds camera permission request to queue
     *
     * @param requestNow if true, no more permissions will be added and the process of retrieving the permissions will start
     */
    fun requestCameraPermission(requestNow: Boolean) {
        requestCameraPermission(null, null, requestNow)
    }

    /**
     * Adds camera permission request to queue and start the process of retrieving the permissions immediately
     */
    fun requestCameraPermission() {
        requestCameraPermission(null, null, true)
    }

    /**
     * Adds camera permission request to queue. It can display messages.
     *
     * @param explanationMessage is String, which will displayed before permission dialog it self. If is null, no message will be displayed
     * @param onDeclineMessage is int reference to String, which will displayed in case of declining the permission
     * @param requestNow if true, no more permissions will be added and the process of retrieving the permissions will start
     */
    fun requestCameraPermission(
        explanationMessage: String?,
        onDeclineMessage: Int,
        requestNow: Boolean
    ) {
        requestCameraPermission(
            explanationMessage,
            MyString(this.context).fromResources(onDeclineMessage),
            requestNow
        )
    }

    /**
     * Adds camera permission request to queue
     *
     * @param explanationMessage is int reference to String, which will displayed before permission dialog it self
     * @param onDeclineMessage is String, which will displayed in case of declining the permission. If is null, no message will be displayed
     * @param requestNow if true, no more permissions will be added and the process of retrieving the permissions will start
     */
    fun requestCameraPermission(
        explanationMessage: Int,
        onDeclineMessage: String?,
        requestNow: Boolean
    ) {
        requestCameraPermission(
            MyString(this.context).fromResources(explanationMessage),
            onDeclineMessage,
            requestNow
        )
    }

    /**
     * Adds camera permission request to queue
     *
     * @param explanationMessage is int reference to String, which will displayed before permission dialog it self
     * @param onDeclineMessage is int reference to String, which will displayed in case of declining the permission
     * @param requestNow if true, no more permissions will be added and the process of retrieving the permissions will start
     */
    fun requestCameraPermission(
        explanationMessage: Int,
        onDeclineMessage: Int,
        requestNow: Boolean
    ) {
        requestCameraPermission(
            MyString(this.context).fromResources(explanationMessage),
            MyString(this.context).fromResources(onDeclineMessage),
            requestNow
        )
    }

    /**
     * Adds camera permission request to queue and start the process of retrieving the permissions immediately
     *
     * @param explanationMessage is String, which will displayed before permission dialog it self. If is null, no message will be displayed
     * @param onDeclineMessage is String, which will displayed in case of declining the permission. If is null, no message will be displayed
     */
    fun requestCameraPermission(explanationMessage: String?, onDeclineMessage: String?) {
        requestCameraPermission(explanationMessage, onDeclineMessage, true)
    }

    /**
     * Adds camera permission request to queue and start the process of retrieving the permissions immediately
     *
     * @param explanationMessage is int reference to String, which will displayed before permission dialog it self
     * @param onDeclineMessage is String, which will displayed in case of declining the permission. If is null, no message will be displayed
     */
    fun requestCameraPermission(explanationMessage: Int, onDeclineMessage: String?) {
        requestCameraPermission(
            MyString(this.context).fromResources(explanationMessage),
            onDeclineMessage,
            true
        )
    }

    /**
     * Adds camera permission request to queue and start the process of retrieving the permissions immediately
     *
     * @param explanationMessage is String, which will displayed before permission dialog it self. If is null, no message will be displayed
     * @param onDeclineMessage is int reference to String, which will displayed in case of declining the permission
     */
    fun requestCameraPermission(explanationMessage: String?, onDeclineMessage: Int) {
        requestCameraPermission(
            explanationMessage,
            MyString(this.context).fromResources(onDeclineMessage),
            true
        )
    }

    /**
     * Adds camera permission request to queue and start the process of retrieving the permissions immediately
     *
     * @param explanationMessage is int reference to String, which will displayed before permission dialog it self
     * @param onDeclineMessage is int reference to String, which will displayed in case of declining the permission
     */
    fun requestCameraPermission(explanationMessage: Int, onDeclineMessage: Int) {
        requestCameraPermission(
            MyString(this.context).fromResources(explanationMessage),
            MyString(this.context).fromResources(onDeclineMessage),
            true
        )
    }

    /**
     * Creates permission data class
     *
     * @param permission is requested permission
     * @param explanationMessage is String, which will displayed before showing the permission request dialog it self. If is null, no message will displayed
     * @param onDeclineMessage is String, which will displayed in case of declining the permission. If is null, no message will displayed
     */
    private data class Permission(
        val permission: String,
        val explanationMessage: String?,
        val onDeclineMessage: String?,
        var granted: Boolean = false
    ) {

        /**
         * Creates permission data class with no messages to display
         */
        constructor(permission: String) : this(permission, null, null)

        /**
         * @return if the message will displayed before showing the permission dialog
         */
        fun hasExplanationMessage(): Boolean {
            return this.explanationMessage != null
        }

        /**
         * @return if the message will displayed in case of declining the permission
         */
        fun hasOnDeclineMessage(): Boolean {
            return this.onDeclineMessage != null
        }
    }
}