@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.kaufmannmarek.library

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.io.Serializable

/**
 * Creates class, which can handle basic operations of fragments
 *
 * @param context of currently displayed activity
 * @param container is int reference to view, where the fragments will be displayed
 */
class MyFragmentManager(private val context: Context, private val container: Int) {
    private val fragmentManager = (this.context as FragmentActivity).supportFragmentManager
    private var fragmentTransaction = this.fragmentManager.beginTransaction()
    private val bundle = Bundle()
    private var myString: MyString? = null

    fun getMyString(): MyString {
        if (myString == null)
            myString = MyString(context)
        return myString!!
    }

    /**
     * Will replace current fragment with new specified fragment and activity code. Transaction will be committed immediately. All existing fragments in backStack will be removed.
     *
     * @param fragment is destination fragment
     * @param fragmentTag is String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     * @param serializableName is String, which set serializable variable name
     * @param serializable is object, that will passed to new fragment
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun replaceFragment(
        fragment: Fragment,
        fragmentTag: String,
        enterFromLeft: Boolean,
        activityCode: Int?,
        serializableName: String?,
        serializable: Serializable?,
        commit: Boolean
    ) {
        setTransactionParams(enterFromLeft, fragment, activityCode, serializableName, serializable)
        getFragmentTransaction().replace(
            getContainerId(),
            fragment,
            fragmentTag
        )
        getFragmentTransaction().addToBackStack(fragmentTag)
        if (commit)
            commit()
    }

    /**
     * Will replace current fragment with new specified fragment and activity code. Transaction will be committed immediately. All existing fragments in backStack will be removed.
     *
     * @param fragment is destination fragment
     * @param fragmentTag is int reference to String, that is used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     * @param serializableName is String, which set serializable variable name
     * @param serializable is object, that will passed to new fragment
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun replaceFragment(
        fragment: Fragment,
        fragmentTag: Int,
        enterFromLeft: Boolean,
        activityCode: Int,
        serializableName: String,
        serializable: Serializable,
        commit: Boolean
    ) {
        replaceFragment(
            fragment,
            getMyString().fromResources(fragmentTag),
            enterFromLeft,
            activityCode,
            serializableName,
            serializable,
            commit
        )
    }

    /**
     * Will replace current fragment with new specified fragment and activity code. Transaction will be committed immediately. All existing fragments in backStack will be removed.
     *
     * @param fragment is destination fragment
     * @param fragmentTag is String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     * @param serializableName is int reference to String, which set serializable variable name
     * @param serializable is object, that will passed to new fragment
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun replaceFragment(
        fragment: Fragment,
        fragmentTag: String,
        enterFromLeft: Boolean,
        activityCode: Int,
        serializableName: Int,
        serializable: Serializable,
        commit: Boolean
    ) {
        replaceFragment(
            fragment,
            fragmentTag,
            enterFromLeft,
            activityCode,
            getMyString().fromResources(serializableName),
            serializable,
            commit
        )
    }

    /**
     * Will replace current fragment with new specified fragment and activity code. Transaction will be committed immediately. All existing fragments in backStack will be removed.
     *
     * @param fragment is destination fragment
     * @param fragmentTag is int reference to String, that is used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     * @param serializableName is int reference to String, which set serializable variable name
     * @param serializable is object, that will passed to new fragment
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun replaceFragment(
        fragment: Fragment,
        fragmentTag: Int,
        enterFromLeft: Boolean,
        activityCode: Int,
        serializableName: Int,
        serializable: Serializable,
        commit: Boolean
    ) {
        replaceFragment(
            fragment,
            getMyString().fromResources(fragmentTag),
            enterFromLeft,
            activityCode,
            getMyString().fromResources(serializableName),
            serializable,
            commit
        )
    }

    /**
     * Will replace current fragment with new specified fragment and activity code. Transaction will be committed immediately. All existing fragments in backStack will be removed.
     *
     * @param fragment is destination fragment
     * @param fragmentTag is String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun replaceFragment(
        fragment: Fragment,
        fragmentTag: String,
        enterFromLeft: Boolean,
        activityCode: Int,
        commit: Boolean
    ) {
        replaceFragment(fragment, fragmentTag, enterFromLeft, activityCode, null, null, commit)
    }

    /**
     * Will replace current fragment with new specified fragment and activity code. Transaction will be committed immediately. All existing fragments in backStack will be removed.
     *
     * @param fragment is destination fragment
     * @param fragmentTag is int reference to String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun replaceFragment(
        fragment: Fragment,
        fragmentTag: Int,
        enterFromLeft: Boolean,
        activityCode: Int,
        commit: Boolean
    ) {
        replaceFragment(
            fragment,
            getMyString().fromResources(fragmentTag),
            enterFromLeft,
            activityCode,
            commit
        )
    }

    /**
     * Will replace current fragment with new specified fragment and activity code. Transaction will be committed immediately. All existing fragments in backStack will be removed.
     *
     * @param fragment is destination fragment
     * @param fragmentTag is String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     */
    fun replaceFragment(
        fragment: Fragment,
        fragmentTag: String,
        enterFromLeft: Boolean,
        activityCode: Int
    ) {
        putInt(R.string.keyActivityCode, activityCode)
        replaceFragment(fragment, fragmentTag, enterFromLeft, true)
    }

    /**
     * Will replace current fragment with new specified fragment and activity code. Transaction will be committed immediately. All existing fragments in backStack will be removed.
     *
     * @param fragment is destination fragment
     * @param fragmentTag is int reference to String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     */
    fun replaceFragment(
        fragment: Fragment,
        fragmentTag: Int,
        enterFromLeft: Boolean,
        activityCode: Int
    ) {
        replaceFragment(
            fragment,
            getMyString().fromResources(fragmentTag),
            enterFromLeft,
            activityCode
        )
    }

    /**
     * Will replace current fragment with new specified fragment. Transaction will be committed immediately. All existing fragments in backStack will be removed.
     *
     * @param fragment is destination fragment
     * @param fragmentTag is int reference to String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     */
    fun replaceFragment(fragment: Fragment, fragmentTag: String, enterFromLeft: Boolean) {
        replaceFragment(fragment, fragmentTag, enterFromLeft, true)
    }

    /**
     * Will replace current fragment with new specified fragment. Transaction will be committed immediately. All existing fragments in backStack will be removed.
     *
     * @param fragment is destination fragment
     * @param fragmentTag is int reference to String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     */
    fun replaceFragment(fragment: Fragment, fragmentTag: Int, enterFromLeft: Boolean) {
        replaceFragment(
            fragment,
            getMyString().fromResources(fragmentTag),
            enterFromLeft
        )
    }

    /**
     * Will replace current fragment with new specified fragment. All existing fragments in backStack will be removed.
     *
     * @param fragment is destination fragment
     * @param fragmentTag is int reference to String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun replaceFragment(
        fragment: Fragment,
        fragmentTag: Int,
        enterFromLeft: Boolean,
        commit: Boolean
    ) {
        replaceFragment(
            fragment,
            getMyString().fromResources(fragmentTag),
            enterFromLeft,
            commit
        )
    }

    /**
     * Will replace current fragment with new specified fragment. All existing fragments in backStack will be removed.
     *
     * @param fragment is destination fragment
     * @param fragmentTag is String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun replaceFragment(
        fragment: Fragment,
        fragmentTag: String,
        enterFromLeft: Boolean,
        commit: Boolean
    ) {
        setTransactionParams(enterFromLeft, fragment, null, null, null)
        getFragmentTransaction().replace(
            getContainerId(),
            fragment,
            fragmentTag
        )
        getFragmentTransaction().addToBackStack(fragmentTag)
        if (commit)
            commit()
    }

    /**
     * Perform basic operations before committing fragment transaction
     *
     * @param enterFromLeft if true, new fragment will appear from left side. Otherwise it will appear from right side
     * @param fragment is a fragment, where the arguments will be passed
     */
    private fun setTransactionParams(
        enterFromLeft: Boolean,
        fragment: Fragment,
        activityCode: Int?,
        serializableName: String?,
        serializable: Serializable?
    ) {
        if (activityCode != null)
            putInt(R.string.keyActivityCode, activityCode)
        if (serializable != null && serializableName != null)
            putSerializable(serializableName, serializable)
        setFragmentTransactionAnimation(enterFromLeft)
        fragment.arguments = getBundle()
    }

    /**
     * Perform basic operations before committing fragment transaction
     *
     * @param enterFromLeft if true, new fragment will appear from left side. Otherwise it will appear from right side
     * @param fragment is a fragment, where the arguments will be passed
     */
    private fun setTransactionParamsInt(
        enterFromLeft: Boolean,
        fragment: Fragment,
        activityCode: Int?,
        serializableName: Int?,
        serializable: Serializable?
    ) {
        if (activityCode != null)
            putInt(R.string.keyActivityCode, activityCode)
        if (serializable != null && serializableName != null)
            putSerializable(serializableName, serializable)
        setFragmentTransactionAnimation(enterFromLeft)
        fragment.arguments = getBundle()
    }

    /**
     * Adds animation to transaction
     *
     * @param enterFromLeft if true, new fragment will appear from left side. Otherwise it will appear from right side
     */
    private fun setFragmentTransactionAnimation(enterFromLeft: Boolean) {
        when (enterFromLeft) {
            true -> {
                getFragmentTransaction().setCustomAnimations(
                    R.anim.enter_right_to_left,
                    R.anim.exit_right_to_left,
                    R.anim.enter_left_to_right,
                    R.anim.exit_left_to_right
                )
            }
            false -> {
                getFragmentTransaction().setCustomAnimations(
                    R.anim.enter_left_to_right,
                    R.anim.exit_left_to_right,
                    R.anim.enter_right_to_left,
                    R.anim.exit_right_to_left
                )
            }
        }
    }

    private fun afterTransactionParamsSet(
        fragment: Fragment,
        fragmentTag: String,
        commit: Boolean
    ) {
        if (getLastFragmentInContainer() != null)
            getLastFragmentInContainer()!!.requireView().visibility = View.GONE
        getFragmentTransaction().add(getContainerId(), fragment, fragmentTag)
        getFragmentTransaction().addToBackStack(fragmentTag)
        if (commit)
            commit()
    }

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is String, that is used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     * @param serializableName is String, which set serializable variable name
     * @param serializable is object, that will passed to new fragment
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: String,
        enterFromLeft: Boolean,
        activityCode: Int?,
        serializableName: String?,
        serializable: Serializable?,
        commit: Boolean
    ) {
        setTransactionParams(enterFromLeft, fragment, activityCode, serializableName, serializable)
        afterTransactionParamsSet(fragment, fragmentTag, commit)
    }

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is int reference to String, that is used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     * @param serializableName is String, which set serializable variable name
     * @param serializable is object, that will passed to new fragment
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: Int,
        enterFromLeft: Boolean,
        activityCode: Int?,
        serializableName: String?,
        serializable: Serializable?,
        commit: Boolean
    ) {
        setTransactionParams(enterFromLeft, fragment, activityCode, serializableName, serializable)
        afterTransactionParamsSet(fragment, getMyString().fromResources(fragmentTag), commit)
    }

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is String, that is used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     * @param serializableName is int reference to String, which set serializable variable name
     * @param serializable is object, that will passed to new fragment
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: String,
        enterFromLeft: Boolean,
        activityCode: Int?,
        serializableName: Int?,
        serializable: Serializable?,
        commit: Boolean
    ) {
        setTransactionParamsInt(
            enterFromLeft,
            fragment,
            activityCode,
            serializableName,
            serializable
        )
        afterTransactionParamsSet(fragment, fragmentTag, commit)
    }

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is int reference to String, that is used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     * @param serializableName is int reference to String, which set serializable variable name
     * @param serializable is object, that will passed to new fragment
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: Int,
        enterFromLeft: Boolean,
        activityCode: Int?,
        serializableName: Int?,
        serializable: Serializable?,
        commit: Boolean
    ) {
        addFragment(
            fragment,
            getMyString().fromResources(fragmentTag),
            enterFromLeft,
            activityCode,
            serializableName,
            serializable,
            commit
        )
    }

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is String, that is used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     * @param serializableName is String, which set serializable variable name
     * @param serializable is object, that will passed to new fragment
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: String,
        enterFromLeft: Boolean,
        activityCode: Int,
        serializableName: String,
        serializable: Serializable
    ) {
        addFragment(
            fragment,
            fragmentTag,
            enterFromLeft,
            activityCode,
            serializableName,
            serializable,
            true
        )
    }

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is int reference to String, that is used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     * @param serializableName is String, which set serializable variable name
     * @param serializable is object, that will passed to new fragment
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: Int,
        enterFromLeft: Boolean,
        activityCode: Int,
        serializableName: String,
        serializable: Serializable
    ) {
        addFragment(
            fragment,
            getMyString().fromResources(fragmentTag),
            enterFromLeft,
            activityCode,
            serializableName,
            serializable
        )
    }

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is String, that is used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     * @param serializableName is int reference to String, which set serializable variable name
     * @param serializable is object, that will passed to new fragment
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: String,
        enterFromLeft: Boolean,
        activityCode: Int,
        serializableName: Int,
        serializable: Serializable
    ) {
        addFragment(
            fragment,
            fragmentTag,
            enterFromLeft,
            activityCode,
            getMyString().fromResources(serializableName),
            serializable
        )
    }

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is int reference to String, that is used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     * @param serializableName is int reference to String, which set serializable variable name
     * @param serializable is object, that will passed to new fragment
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: Int,
        enterFromLeft: Boolean,
        activityCode: Int,
        serializableName: Int,
        serializable: Serializable
    ) {
        addFragment(
            fragment,
            getMyString().fromResources(fragmentTag),
            enterFromLeft,
            activityCode,
            getMyString().fromResources(serializableName),
            serializable
        )
    }

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is String, that is used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param serializableName is String, which set serializable variable name
     * @param serializable is object, that will passed to new fragment
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: String,
        enterFromLeft: Boolean,
        serializableName: String,
        serializable: Serializable,
        commit: Boolean
    ) {
        addFragment(
            fragment,
            fragmentTag,
            enterFromLeft,
            null,
            serializableName,
            serializable,
            commit
        )
    }

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is int reference to String, that is used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param serializableName is String, which set serializable variable name
     * @param serializable is object, that will passed to new fragment
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: Int,
        enterFromLeft: Boolean,
        serializableName: String,
        serializable: Serializable,
        commit: Boolean
    ) {
        addFragment(
            fragment,
            getMyString().fromResources(fragmentTag),
            enterFromLeft,
            serializableName,
            serializable,
            commit
        )
    }

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is String, that is used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param serializableName is int reference to String, which set serializable variable name
     * @param serializable is object, that will passed to new fragment
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: String,
        enterFromLeft: Boolean,
        serializableName: Int,
        serializable: Serializable,
        commit: Boolean
    ) {
        addFragment(
            fragment,
            fragmentTag,
            enterFromLeft,
            getMyString().fromResources(serializableName),
            serializable,
            commit
        )
    }

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is int reference to String, that is used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param serializableName is int reference to String, which set serializable variable name
     * @param serializable is object, that will passed to new fragment
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: Int,
        enterFromLeft: Boolean,
        serializableName: Int,
        serializable: Serializable,
        commit: Boolean
    ) {
        addFragment(
            fragment,
            getMyString().fromResources(fragmentTag),
            enterFromLeft,
            getMyString().fromResources(serializableName),
            serializable,
            commit
        )
    }

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is String, that is used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param serializableName is String, which set serializable variable name
     * @param serializable is object, that will passed to new fragment
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: String,
        enterFromLeft: Boolean,
        serializableName: String,
        serializable: Serializable
    ) {
        addFragment(fragment, fragmentTag, enterFromLeft, serializableName, serializable, true)
    }

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is int reference to String, that is used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param serializableName is String, which set serializable variable name
     * @param serializable is object, that will passed to new fragment
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: Int,
        enterFromLeft: Boolean,
        serializableName: String,
        serializable: Serializable
    ) {
        addFragment(
            fragment,
            getMyString().fromResources(fragmentTag),
            enterFromLeft,
            serializableName,
            serializable
        )
    }

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is String, that is used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param serializableName is int reference to String, which set serializable variable name
     * @param serializable is object, that will passed to new fragment
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: String,
        enterFromLeft: Boolean,
        serializableName: Int,
        serializable: Serializable
    ) {
        addFragment(
            fragment,
            fragmentTag,
            enterFromLeft,
            getMyString().fromResources(serializableName),
            serializable
        )
    }

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is int reference to String, that is used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param serializableName is int reference to String, which set serializable variable name
     * @param serializable is object, that will passed to new fragment
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: Int,
        enterFromLeft: Boolean,
        serializableName: Int,
        serializable: Serializable
    ) {
        addFragment(
            fragment,
            getMyString().fromResources(fragmentTag),
            enterFromLeft,
            getMyString().fromResources(serializableName),
            serializable
        )
    }

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: String,
        enterFromLeft: Boolean,
        activityCode: Int,
        commit: Boolean
    ) {
        setTransactionParams(enterFromLeft, fragment, activityCode, null, null)
        afterTransactionParamsSet(fragment, fragmentTag, commit)
    }

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: Int,
        enterFromLeft: Boolean,
        activityCode: Int,
        commit: Boolean
    ) {
        addFragment(
            fragment,
            getMyString().fromResources(fragmentTag),
            enterFromLeft,
            activityCode,
            commit
        )
    }

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: String,
        enterFromLeft: Boolean,
        activityCode: Int
    ) {
        addFragment(fragment, fragmentTag, enterFromLeft, activityCode, true)
    }

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is int reference to String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: Int,
        enterFromLeft: Boolean,
        activityCode: Int
    ) {
        addFragment(
            fragment,
            getMyString().fromResources(fragmentTag),
            enterFromLeft,
            activityCode
        )
    }

    /**
     * Will add a new fragment to backStack and displays it
     *
     * @param fragment is destination fragment
     * @param fragmentTag is String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: String,
        enterFromLeft: Boolean,
        commit: Boolean
    ) {
        setTransactionParams(enterFromLeft, fragment, null, null, null)
        afterTransactionParamsSet(fragment, fragmentTag, commit)
    }

    /**
     * Will add a new fragment to backStack and displays it
     *
     * @param fragment is destination fragment
     * @param fragmentTag is int reference to String, which used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun addFragment(
        fragment: Fragment,
        fragmentTag: Int,
        enterFromLeft: Boolean,
        commit: Boolean
    ) {
        addFragment(
            fragment,
            getMyString().fromResources(fragmentTag),
            enterFromLeft,
            commit
        )
    }

    /**
     * Will add a new fragment to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is int reference to String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     */
    fun addFragment(fragment: Fragment, fragmentTag: String, enterFromLeft: Boolean) {
        addFragment(fragment, fragmentTag, enterFromLeft, true)
    }

    /**
     * Will add a new fragment to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param fragmentTag is int reference to String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     */
    fun addFragment(fragment: Fragment, fragmentTag: Int, enterFromLeft: Boolean) {
        addFragment(
            fragment,
            getMyString().fromResources(fragmentTag),
            enterFromLeft
        )
    }

    /**
     * Will retrieve fragment from backStack. If the fragment is not in backStack, then it will add it and
     *
     * @param fragment is destination fragment
     * @param fragmentTag is String, which used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun restoreFragment(
        fragment: Fragment,
        fragmentTag: String,
        enterFromLeft: Boolean,
        commit: Boolean
    ) {
        if (getFragmentByTag(fragmentTag) != null) {
            getFragmentManager().popBackStackImmediate(
                fragmentTag,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            if (commit)
                commit()
        } else
            addFragment(fragment, fragmentTag, enterFromLeft, commit)
    }

    /**
     * Will retrieve fragment from backStack. If the fragment is not in backStack, then it will add it and
     *
     * @param fragment is destination fragment
     * @param fragmentTag is int reference to String, which used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun restoreFragment(
        fragment: Fragment,
        fragmentTag: Int,
        enterFromLeft: Boolean,
        commit: Boolean
    ) {
        restoreFragment(
            fragment,
            getMyString().fromResources(fragmentTag),
            enterFromLeft,
            commit
        )
    }

    /**
     * Will retrieve fragment from backStack. If the fragment is not in backStack, then it will add it and
     *
     * @param fragment is destination fragment
     * @param fragmentTag is String, which used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     */
    fun restoreFragment(
        fragment: Fragment,
        fragmentTag: String,
        enterFromLeft: Boolean
    ) {
        restoreFragment(fragment, fragmentTag, enterFromLeft, true)
    }

    /**
     * Will retrieve fragment from backStack. If the fragment is not in backStack, then it will add it and
     *
     * @param fragment is destination fragment
     * @param fragmentTag is int reference to String, which used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     */
    fun restoreFragment(
        fragment: Fragment,
        fragmentTag: Int,
        enterFromLeft: Boolean
    ) {
        restoreFragment(
            fragment,
            getMyString().fromResources(fragmentTag),
            enterFromLeft,
            true
        )
    }

    /**
     * Commits transaction and allow to perform another transaction
     */
    fun commit() {
        getFragmentTransaction().commit()
        this.fragmentTransaction = getFragmentManager().beginTransaction()
    }

    /**
     * Adds serializable object as argument to this transaction
     *
     * @param paramName is String used to retrieve serializable
     * @param serializable is object, to be added in transaction
     */
    fun putSerializable(paramName: String, serializable: Serializable) {
        getBundle().putSerializable(
            paramName, serializable
        )
    }

    /**
     * Adds serializable object as argument to this transaction
     *
     * @param paramName is int reference to String, which is used to retrieve serializable
     * @param serializable is object, which will be added to transaction
     */
    fun putSerializable(paramName: Int, serializable: Serializable) {
        putSerializable(getMyString().fromResources(paramName), serializable)
    }

    /**
     * Adds String as argument to this transaction
     *
     * @param paramName is String, which is used to retrieve serializable
     * @param value is String, which will be added to transaction
     */
    fun putString(paramName: String, value: String) {
        getBundle().putString(paramName, value)
    }

    /**
     * Adds String as argument to this transaction
     *
     * @param paramName is int reference to String, which is used to retrieve String
     * @param value is String, which will be added to transaction
     */
    fun putString(paramName: Int, value: String) {
        putString(getMyString().fromResources(paramName), value)
    }

    /**
     * Adds String as argument to this transaction
     *
     * @param paramName is String, which is used to retrieve String
     * @param value is int reference to String, which will be added to transaction
     */
    fun putString(paramName: String, value: Int) {
        putString(paramName, getMyString().fromResources(value))
    }

    /**
     * Adds String as argument to this transaction
     *
     * @param paramName is int reference to String, which is used to retrieve String
     * @param value is int reference to String, which will be added to transaction
     */
    fun putString(paramName: Int, value: Int) {
        putString(
            getMyString().fromResources(paramName),
            getMyString().fromResources(value)
        )
    }

    /**
     * Adds int as argument to this transaction
     *
     * @param paramName is String, which is used to retrieve int
     * @param value is int, which will be added to transaction
     */
    fun putInt(paramName: String, value: Int) {
        getBundle().putInt(paramName, value)
    }

    /**
     * Adds int as argument to this transaction
     *
     * @param paramName is int reference to String, which is used to retrieve int
     * @param value is int, which will be added to transaction
     */
    fun putInt(paramName: Int, value: Int) {
        putInt(getMyString().fromResources(paramName), value)
    }

    /**
     * Adds boolean as argument to this transaction
     *
     * @param paramName is String, which is used to retrieve int
     * @param value is int, which will be added to transaction
     */
    fun putBoolean(paramName: String, value: Boolean) {
        getBundle().putBoolean(paramName, value)
    }

    /**
     * Adds boolean as argument to this transaction
     *
     * @param paramName is int reference to String, which is used to retrieve int
     * @param value is int, which will be added to transaction
     */
    fun putBoolean(paramName: Int, value: Boolean) {
        putBoolean(getMyString().fromResources(paramName), value)
    }

    /**
     * @return if the fragment with provided tag is in fragment manager
     * @param fragmentTag is tag of fragment, which we examine
     */
    fun isFragmentInBackStack(fragmentTag: String): Boolean {
        return try {
            getFragmentManager().findFragmentByTag(fragmentTag)!!.isVisible
        } catch (e: Exception) {
            false
        }
    }

    /**
     * @return if the fragment with provided tag is in fragment manager
     * @param fragmentTag is int reference to tag of fragment, which we examine
     */
    fun isFragmentInBackStack(fragmentTag: Int): Boolean {
        return isFragmentInBackStack(getMyString().fromResources(fragmentTag))
    }

    /**
     * @return currently visible fragment in container provided at the beginning. If there is no active fragment or the fragment was displayed in different activity, it will return null
     */
    fun getActiveFragment(): Fragment? {
        return try {
            getLastFragmentInContainer()
        } catch (e: Exception) {
            null
        }
    }

    /**
     * @return instance of active fragment in fragment manager using a fragment tag. If the fragment is not present, returns null.
     * @param fragmentTag is String, which represents required fragment
     */
    fun getFragmentByTag(fragmentTag: String): Fragment? {
        return getFragmentManager().findFragmentByTag(fragmentTag)
    }

    /**
     * @return instance of active fragment in fragment manager using a fragment tag. If the fragment is not present, returns null.
     * @param fragmentTag is int reference to String, which represents required fragment
     */
    fun getFragmentByTag(fragmentTag: Int): Fragment? {
        return getFragmentByTag(getMyString().fromResources(fragmentTag))
    }

    /**
     * @return instance of currently visible fragment in container
     */
    private fun getLastFragmentInContainer(): Fragment? {
        return getFragmentManager().findFragmentById(getContainerId())
    }


    /**
     * @return instance of fragmentManager of current activity
     */
    fun getFragmentManager(): FragmentManager {
        return this.fragmentManager
    }

    /**
     * @return instance of fragmentTransaction of current activity
     */
    fun getFragmentTransaction(): FragmentTransaction {
        return this.fragmentTransaction
    }

    /**
     * @return id of the container the fragment manager is using to display fragments
     */
    fun getContainerId(): Int {
        return this.container
    }

    /**
     * @return if the a container had any fragment inside it. If it had, the top fragment will be removed. Current fragment will exit to left side and a old will come from right.
     */
    fun thereWasFragmentToRemove(): Boolean {
        val fragment = getFragmentManager().findFragmentById(getContainerId())
        if (fragment != null) {
            fragmentRemove(fragment, false)
            return true
        }
        return false
    }

    /**
     * Removes a fragment from transaction
     *
     * @param fragment to be removed
     * @param enterFromLeft if true, new fragment will appear from left side. Otherwise it will appear from right side
     */
    private fun fragmentRemove(fragment: Fragment, enterFromLeft: Boolean) {
        setFragmentTransactionAnimation(enterFromLeft)
        getFragmentTransaction().remove(fragment)
        commit()
        getFragmentManager().popBackStackImmediate()
    }

    /**
     * @return number of fragments in fragment manager
     */
    fun getNumberOfActiveFragments(): Int {
        return this.fragmentManager.backStackEntryCount
    }

    /**
     * Removes a currently visible fragment and restore last saved fragment in backstack
     *
     * @param container is container, from where the fragment should be removed
     * @param enterFromLeft if true, new fragment will appear from left side. Otherwise it will appear from right side
     */
    fun removeFragment(container: Int, enterFromLeft: Boolean) {
        val fragment = getFragmentManager().findFragmentById(container)
        if (fragment != null)
            fragmentRemove(fragment, enterFromLeft)
    }

    /**
     * Removes a currently visible fragment and restore last saved fragment in backstack
     *
     * @param fragmentTag is String, that is used as tag of fragment, which should be remove
     * @param enterFromLeft if true, new fragment will appear from left side. Otherwise it will appear from right side
     */
    fun removeFragmentByTag(fragmentTag: String, enterFromLeft: Boolean) {
        val fragment = getFragmentManager().findFragmentByTag(fragmentTag)
        if (fragment != null)
            fragmentRemove(fragment, enterFromLeft)
    }

    /**
     * Removes a currently visible fragment and restore last saved fragment in backstack
     *
     * @param fragmentTag is int reference to String, that is used as tag of fragment, which should be remove
     * @param enterFromLeft if true, new fragment will appear from left side. Otherwise it will appear from right side
     */
    fun removeFragmentByTag(fragmentTag: Int, enterFromLeft: Boolean) {
        removeFragmentByTag(getMyString().fromResources(fragmentTag), enterFromLeft)
    }

    /**
     * @return bundle
     */
    fun getBundle(): Bundle {
        return this.bundle
    }

    /**
     * @return if the fragment with provided tag was restored
     * @param fragmentTag is String tag, which the required fragment should have
     */
    fun restoreFragment(fragmentTag: String): Boolean {
        val popped = this.fragmentManager.popBackStackImmediate(fragmentTag, 0)
        if (getLastFragmentInContainer() != null)
            getLastFragmentInContainer()!!.requireView().visibility = View.VISIBLE
        return popped
    }

    /**
     * @return if the fragment with provided tag was restored
     * @param fragmentTag is int reference to String tag, which the required fragment should have
     */
    fun restoreFragment(fragmentTag: Int): Boolean {
        return restoreFragment(getMyString().fromResources(fragmentTag))
    }
}