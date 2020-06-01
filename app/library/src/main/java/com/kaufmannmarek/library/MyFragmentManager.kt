@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.kaufmannmarek.library

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.io.Serializable

/**
 * Creates class, which can handle basic operations of fragments
 *
 * @param context of currently displayed activity
 */
class MyFragmentManager(private val context: Context) {
    private val fragmentManager = (this.context as FragmentActivity).supportFragmentManager
    private var fragmentTransaction = this.fragmentManager.beginTransaction()
    private val bundle = Bundle()

    companion object {
        private var activeFragmentTag: String? = null
    }

    /**
     * Will replace current fragment with new specified fragment and activity code. Transaction will be committed immediately. All existing fragments in backStack will be removed.
     *
     * @param fragment is destination fragment
     * @param container is int reference to view, where the fragment will be displayed
     * @param tag is String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     */
    fun replaceFragment(
        fragment: Fragment,
        container: Int,
        tag: String,
        enterFromLeft: Boolean,
        activityCode: Int
    ) {
        putInt(R.string.keyActivityCode, activityCode)
        replaceFragment(fragment, container, tag, enterFromLeft, true)
    }

    /**
     * Will replace current fragment with new specified fragment and activity code. Transaction will be committed immediately. All existing fragments in backStack will be removed.
     *
     * @param fragment is destination fragment
     * @param container is int reference to view, where the fragment will be displayed
     * @param tag is int reference to String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     */
    fun replaceFragment(
        fragment: Fragment,
        container: Int,
        tag: Int,
        enterFromLeft: Boolean,
        activityCode: Int
    ) {
        replaceFragment(
            fragment,
            container,
            MyString(this.context).fromResources(tag),
            enterFromLeft,
            activityCode
        )
    }

    /**
     * Will replace current fragment with new specified fragment. Transaction will be committed immediately. All existing fragments in backStack will be removed.
     *
     * @param fragment is destination fragment
     * @param container is int reference to view, where the fragment will be displayed
     * @param tag is int reference to String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     */
    fun replaceFragment(fragment: Fragment, container: Int, tag: String, enterFromLeft: Boolean) {
        replaceFragment(fragment, container, tag, enterFromLeft, true)
    }

    /**
     * Will replace current fragment with new specified fragment. Transaction will be committed immediately. All existing fragments in backStack will be removed.
     *
     * @param fragment is destination fragment
     * @param container is int reference to view, where the fragment will be displayed
     * @param tag is int reference to String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     */
    fun replaceFragment(fragment: Fragment, container: Int, tag: Int, enterFromLeft: Boolean) {
        replaceFragment(
            fragment,
            container,
            MyString(this.context).fromResources(tag),
            enterFromLeft
        )
    }

    /**
     * Will replace current fragment with new specified fragment. All existing fragments in backStack will be removed.
     *
     * @param fragment is destination fragment
     * @param container is int reference to view, where the fragment will be displayed
     * @param tag is int reference to String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun replaceFragment(
        fragment: Fragment,
        container: Int,
        tag: Int,
        enterFromLeft: Boolean,
        commit: Boolean
    ) {
        replaceFragment(
            fragment,
            container,
            MyString(this.context).fromResources(tag),
            enterFromLeft,
            commit
        )
    }

    /**
     * Will replace current fragment with new specified fragment. All existing fragments in backStack will be removed.
     *
     * @param fragment is destination fragment
     * @param container is int reference to view, where the fragment will be displayed
     * @param tag is String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun replaceFragment(
        fragment: Fragment,
        container: Int,
        tag: String,
        enterFromLeft: Boolean,
        commit: Boolean
    ) {
        setTransactionParams(enterFromLeft, fragment)
        getFragmentTransaction().replace(
            container,
            fragment,
            tag
        )
        getFragmentTransaction().addToBackStack(tag)
        if (commit)
            commit()
    }

    /**
     * Perform basic operations before committing fragment transaction
     */
    private fun setTransactionParams(enterFromLeft: Boolean, fragment: Fragment) {
        setFragmentTransactionAnimation(enterFromLeft)
        fragment.arguments = getBundle()
    }

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

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param container is int reference to view, where the fragment will be displayed
     * @param tag is String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     */
    fun addFragment(
        fragment: Fragment,
        container: Int,
        tag: String,
        enterFromLeft: Boolean,
        activityCode: Int
    ) {
        putInt(R.string.keyActivityCode, activityCode)
        addFragment(fragment, container, tag, enterFromLeft, true)
    }

    /**
     * Will add a new fragment with activity code to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param container is int reference to view, where the fragment will be displayed
     * @param tag is int reference to String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment. This code is saved under String "activityCode"
     */
    fun addFragment(
        fragment: Fragment,
        container: Int,
        tag: Int,
        enterFromLeft: Boolean,
        activityCode: Int
    ) {
        addFragment(
            fragment,
            container,
            MyString(this.context).fromResources(tag),
            enterFromLeft,
            activityCode
        )
    }

    /**
     * Will add a new fragment to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param container is int reference to view, where the fragment will be displayed
     * @param tag is int reference to String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     */
    fun addFragment(fragment: Fragment, container: Int, tag: String, enterFromLeft: Boolean) {
        addFragment(fragment, container, tag, enterFromLeft, true)
    }

    /**
     * Will add a new fragment to backStack and displays it. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param container is int reference to view, where the fragment will be displayed
     * @param tag is int reference to String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     */
    fun addFragment(fragment: Fragment, container: Int, tag: Int, enterFromLeft: Boolean) {
        addFragment(
            fragment,
            container,
            MyString(this.context).fromResources(tag),
            enterFromLeft
        )
    }

    /**
     * Will add a new fragment to backStack and displays it
     *
     * @param fragment is destination fragment
     * @param container is int reference to view, where the fragment will be displayed
     * @param tag is String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun addFragment(
        fragment: Fragment,
        container: Int,
        tag: String,
        enterFromLeft: Boolean,
        commit: Boolean
    ) {
        setTransactionParams(enterFromLeft, fragment)
        getFragmentTransaction().add(container, fragment, tag)
        getFragmentTransaction().addToBackStack(tag)
        if (commit)
            commit()
    }

    /**
     * Will add a new fragment to backStack and displays it
     *
     * @param fragment is destination fragment
     * @param container is int reference to view, where the fragment will be displayed
     * @param tag is int reference to String, which used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun addFragment(
        fragment: Fragment,
        container: Int,
        tag: Int,
        enterFromLeft: Boolean,
        commit: Boolean
    ) {
        addFragment(
            fragment,
            container,
            MyString(this.context).fromResources(tag),
            enterFromLeft,
            commit
        )
    }

    /**
     * @return if the a container had any fragment inside it. If it had, the top fragment will be removed. Current fragment will exit to left side and a old will come from right.
     * @param container is container, from where the fragment should be removed
     */
    fun thereWasFragmentToRemove(container: Int): Boolean {
        val fragment = getFragmentManager().findFragmentById(container)
        if (fragment != null) {
            fragmentRemove(fragment, false)
            return true
        }
        return false
    }

    /**
     * Removes a fragment from transaction
     * @param fragment to be removed
     */
    private fun fragmentRemove(fragment: Fragment, enterFromLeft: Boolean) {
        setFragmentTransactionAnimation(enterFromLeft)
        getFragmentTransaction().remove(fragment)
        commit()
        getFragmentManager().popBackStack()
    }

    fun getNumberOfActiveFragments(): Int {
        return this.fragmentManager.backStackEntryCount
    }

    /**
     * Removes a currently visible fragment
     * @param container is container, from where the fragment should be removed
     */
    fun removeFragment(container: Int, enterFromLeft: Boolean) {
        val fragment = getFragmentManager().findFragmentById(container)
        if (fragment != null)
            fragmentRemove(fragment, enterFromLeft)
    }

    /**
     * Commits transaction
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
        putSerializable(this.context.getString(paramName), serializable)
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
        putString(this.context.getString(paramName), value)
    }

    /**
     * Adds String as argument to this transaction
     *
     * @param paramName is String, which is used to retrieve String
     * @param value is int reference to String, which will be added to transaction
     */
    fun putString(paramName: String, value: Int) {
        putString(paramName, MyString(this.context).fromResources(value))
    }

    /**
     * Adds String as argument to this transaction
     *
     * @param paramName is int reference to String, which is used to retrieve String
     * @param value is int reference to String, which will be added to transaction
     */
    fun putString(paramName: Int, value: Int) {
        putString(
            MyString(this.context).fromResources(paramName),
            MyString(this.context).fromResources(value)
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
        putInt(this.context.getString(paramName), value)
    }

    /**
     * @return if the fragment with provided tag is active
     * @param fragmentTag is tag of fragment, which we examine
     */
    fun isFragmentActive(fragmentTag: String): Boolean {
        return try {
            getFragmentManager().findFragmentByTag(fragmentTag)!!.isVisible
        } catch (e: Exception) {
            false
        }
    }

    fun getActiveFragmentTag(): String {
        return if (activeFragmentTag != null)
            activeFragmentTag!!
        else
            "none"
    }

    /**
     * @return currently active fragment
     */
    fun getActiveFragment(): Fragment? {
        return getFragmentByTag(getActiveFragmentTag())
    }

    /**
     * @return if the fragment with provided tag is active
     * @param fragmentTag is int reference to tag of fragment, which we examine
     */
    fun isFragmentActive(fragmentTag: Int): Boolean {
        return isFragmentActive(MyString(this.context).fromResources(fragmentTag))
    }

    /**
     * @return instance of active fragment in fragment manager using a fragment tag.
     * @param tag is String, which represents required fragment
     */
    fun getFragmentByTag(tag: String): Fragment {
        return getFragmentManager().findFragmentByTag(tag)!!
    }

    /**
     * @return instance of active fragment in fragment manager using a fragment tag.
     * @param tag is int reference to String, which represents required fragment
     */
    fun getFragmentByTag(tag: Int): Fragment {
        return getFragmentByTag(MyString(this.context).fromResources(tag))
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

    fun getBundle(): Bundle {
        return this.bundle
    }
}