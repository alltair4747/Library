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
    private val fragmentTransaction = this.fragmentManager.beginTransaction()
    private val bundle = Bundle()

    /**
     * Will replace current fragment with new specified fragment. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param container is int reference to view, where the fragment will be displayed
     * @param tag is int reference to String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param activityCode is code, which will be passed to another fragment
     */
    fun switchFragment(
        fragment: Fragment,
        container: Int,
        tag: Int,
        enterFromLeft: Boolean,
        activityCode: Int
    ) {
        putInt(R.string.keyActivityCode, activityCode)
        switchFragment(fragment, container, tag, enterFromLeft, true)
    }

    /**
     * Will replace current fragment with new specified fragment. Transaction will be committed immediately
     *
     * @param fragment is destination fragment
     * @param container is int reference to view, where the fragment will be displayed
     * @param tag is int reference to String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     */
    fun switchFragment(fragment: Fragment, container: Int, tag: Int, enterFromLeft: Boolean) {
        switchFragment(fragment, container, tag, enterFromLeft, true)
    }

    /**
     * Will replace current fragment with new specified fragment
     *
     * @param fragment is destination fragment
     * @param container is int reference to view, where the fragment will be displayed
     * @param tag is int reference to String used to retrieve fragment from fragment manager
     * @param enterFromLeft is condition, which sets correct animation in transaction. Put true, if the new fragment will appear from left side
     * @param commit is condition. If true, transaction will commit immediately. Else wait until commit function is called
     */
    fun switchFragment(
        fragment: Fragment,
        container: Int,
        tag: Int,
        enterFromLeft: Boolean,
        commit: Boolean
    ) {
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
        fragment.arguments = getBundle()
        getFragmentTransaction().replace(
            container,
            fragment,
            MyString(this.context).getStringFromInt(tag)
        )
        if (commit)
            commit()
    }

    /**
     * Commits transaction
     */
    fun commit() {
        getFragmentTransaction().commit()
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
     * @return instance of active fragment in fragment manager using a fragment tag.
     * @param tag is int reference to String, which represents required fragment
     */
    fun getFragmentByTag(tag: Int): Fragment {
        return getFragmentManager().findFragmentByTag(
            MyString(this.context).getStringFromInt(
                tag
            )
        )!!
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