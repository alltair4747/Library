@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.kaufmannmarek.library

import android.app.Activity
import android.content.Context
import android.util.TypedValue
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
open class MySnackBar(
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
open class CustomLayoutMySnackBar(context: Context, showDialog: Boolean, length: Int) :
    MySnackBar(context, "", showDialog, length) {
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
open class ImageAndTextMySnackBar(
    context: Context,
    text: String,
    image: Int,
    length: Int,
    showDialog: Boolean
) :
    CustomLayoutMySnackBar(context, showDialog, length) {
    private val customLayout = View.inflate(context, R.layout.snack_bar_with_imageview, null)
    private val textView = (this.customLayout.findViewById(R.id.snack_bar_text) as TextView)
    private val imageView = (this.customLayout.findViewById(R.id.snack_bar_icon) as ImageView)
    private var textSize = 30

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
        while (!willTextFitInTextView(text))
            this.textView.text = text
        this.imageView.setImageResource(image)
        this.imageView.setColorFilter(ContextCompat.getColor(getContext(), R.color.textColor))
        getSnackBarLayout().addView(customLayout)
        show()
    }

    fun willTextFitInTextView(text: String): Boolean {
        val textLengthInPixels = textView.paint.measureText(text)
        val linesCount =
            textLengthInPixels / ((getContext().resources.displayMetrics.widthPixels * 2 / 3) - getPaddingToPixels())
        val height = (linesCount - linesCount % 1 + 1) * this.textSize
        return 80 >= height
    }

    fun getPaddingToPixels(): Float {
        val dip = 35f
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip,
            getContext().resources.displayMetrics
        )
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

class LoginMySnackBar(context: Context, userName: String) : ImageAndTextMySnackBar(
    context,
    context.getString(R.string.greetingUser, userName),
    R.drawable.ic_user,
    1500,
    true
)

class SuccessMySnackBar(context: Context, text: String) :
    ImageAndTextMySnackBar(context, text, R.drawable.ic_success, 2500, true) {

    constructor(context: Context, text: Int) : this(context, context.getString(text))

}

class FailMySnackBar(context: Context, text: String) :
    ImageAndTextMySnackBar(context, text, R.drawable.ic_fail, 4000, true) {

    constructor(context: Context, text: Int) : this(context, context.getString(text))

}

class WarningMySnackBar(context: Context, text: String) :
    ImageAndTextMySnackBar(context, text, R.drawable.ic_warning, 2500, true) {

    constructor(context: Context, text: Int) : this(context, context.getString(text))
}

class NotificationMySnackBar(context: Context, text: String) :
    ImageAndTextMySnackBar(context, text, R.drawable.ic_notification, 2500, true) {

    constructor(context: Context, text: Int) : this(context, context.getString(text))
}
