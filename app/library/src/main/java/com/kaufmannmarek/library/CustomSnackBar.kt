@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.kaufmannmarek.library

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

/**
 * Create snackBar with provided parameters. Use can change snackBar background color by defining your own color with name "snackBarBackground"
 *
 * @param context of currently displayed activity
 * @param text is String, which will be displayed
 * @param showDialog will display snackBar or wait till function show is called
 * @param length is int which represents number of milliseconds, when the snackBar will be displayed
 * @param view snackBar will be shown at the bottom of the provided View
 */
open class SnackBar(
    private val context: Context,
    text: String,
    private var showDialog: Boolean,
    length: Int,
    view: View
) {
    private val snackBar: Snackbar = Snackbar.make(view, text, length)


    /**
     * Create snackBar with provided parameters, which will be displayed at the bottom of the current activity. Use can change snackBar background color by defining your own color with name "snackBarBackground"
     *
     * @param context of currently displayed activity
     * @param text is String, which will be displayed
     * @param showDialog will display snackBar or wait till function show is called
     * @param length is int which represents number of milliseconds, when the snackBar will be displayed
     */
    constructor(context: Context, text: String, showDialog: Boolean, length: Int) : this(
        context,
        text,
        showDialog,
        length,
        (context as Activity).findViewById(android.R.id.content)
    )

    /**
     * Create snackBar with provided parameters, which will be displayed at the bottom of the current activity. Use can change snackBar background color by defining your own color with name "snackBarBackground"
     *
     * @param context of currently displayed activity
     * @param text is int reference to String, which will be displayed
     * @param showDialog will display snackBar or wait till function show is called
     * @param length is int which represents number of milliseconds, when the snackBar will be displayed
     */
    constructor(context: Context, text: Int, showDialog: Boolean, length: Int) : this(
        context,
        context.getString(text),
        showDialog,
        length
    )

    /**
     * Create snackBar with provided parameters. Use can change snackBar background color by defining your own color with name "snackBarBackground"
     *
     * @param context of currently displayed activity
     * @param text is String, which will be displayed
     * @param showDialog will display snackBar or wait till function show is called
     * @param length is int which represents number of milliseconds, when the snackBar will be displayed
     * @param view snackBar will be shown at the bottom of the provided View
     */
    constructor(context: Context, text: Int, showDialog: Boolean, length: Int, view: View) : this(
        context,
        context.getString(text),
        showDialog,
        length,
        view
    )

    init {
        getSnackBar().view.setBackgroundResource(R.color.snackBarBackground)
        if (this.showDialog)
            show()
    }

    /**
     * @return instance of the snackBar
     */
    fun getSnackBar(): Snackbar {
        return this.snackBar
    }

    /**
     * Will display the snackBar if its not displayed or was already displayed
     */
    fun show() {
        if (this.showDialog) {
            this.showDialog = false
            getSnackBar().show()
        }
    }

    /**
     * @return context of displayed activity at the time of initialization
     */
    fun getContext(): Context {
        return this.context
    }
}

/**
 * Creates snackBar with no visible ui elements. Use can change snackBar background color by defining your own color with name "snackBarBackground"
 *
 * @param context of currently displayed activity
 * @param showDialog will display snackBar or wait till function show is called
 * @param length is int which represents number of milliseconds, when the snackBar will be displayed
 */
open class CustomLayoutSnackBar(context: Context, showDialog: Boolean, length: Int) :
    SnackBar(context, "", showDialog, length) {
    private val layout = getSnackBar().view as Snackbar.SnackbarLayout

    fun setBackgroundColor(color: Int) {
        this.layout.setBackgroundColor(ContextCompat.getColor(getContext(), color))
    }

    /**
     *@return main view of the snackBar
     */
    fun getSnackBarLayout(): Snackbar.SnackbarLayout {
        return this.layout
    }

    /**
     * Will add view to root of the dialog. This view should be root view
     *
     * @param view is view to be added
     */
    fun addRootView(view: View) {
        getSnackBarLayout().addView(view)
    }
}

/**
 * Create snackBar with imageView and textView. Use can change snackBar background color by defining your own color with name "snackBarBackground"
 *
 * @param context of currently displayed activity
 * @param text is String to be shown
 * @param image is int reference to image that should be displayed in snackBar
 * @param length is int which represents number of milliseconds, when the snackBar will be displayed
 * @param showDialog will display snackBar or wait till function show is called
 */
open class ImageAndTextSnackBar(
    context: Context,
    text: String,
    image: Int,
    length: Int,
    showDialog: Boolean
) :
    CustomLayoutSnackBar(context, showDialog, length) {
    private val customLayout = View.inflate(context, R.layout.snack_bar_with_imageview, null)
    private val textView = (this.customLayout.findViewById(R.id.snack_bar_text) as TextView)
    private val imageView = (this.customLayout.findViewById(R.id.snack_bar_icon) as ImageView)

    /**
     * Create snackBar with imageView and textView
     *
     * @param context of currently displayed activity
     * @param text is int reference to String, which is going to be shown
     * @param image is int reference to image that should be displayed in snackBar
     * @param length is int which represents number of milliseconds, when the snackBar will be displayed
     * @param showDialog will display snackBar or wait till function show is called
     */
    constructor(context: Context, text: Int, image: Int, length: Int, showDialog: Boolean) : this(
        context,
        context.getString(text),
        image,
        length,
        showDialog
    )

    init {
        this.textView.text = text
        this.imageView.setImageResource(image)
        getSnackBarLayout().addView(customLayout)
        show()
    }

    /**
     * @return textView in this snackBar
     */
    fun getTextView(): TextView {
        return this.textView
    }

    /**
     * @return view, which contains textView and imageView
     */
    fun getCustomLayout(): View {
        return this.customLayout
    }

    /**
     * @return imageView in this snackBar
     */
    fun getImageView(): ImageView {
        return this.imageView
    }
}
