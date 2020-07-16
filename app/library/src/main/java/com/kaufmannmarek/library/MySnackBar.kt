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
 * Create default snackBar with provided parameters
 *
 * @param context of currently displayed activity
 * @param text is String, which will be displayed
 * @param show will display snackBar or wait till function show is called
 * @param length is int which represents number of milliseconds, when the snackBar will be displayed
 * @param view snackBar will be shown at the bottom of the provided View. If you want to display it on the bottom of current activity, pass null
 */
open class MySnackBar(
    private val context: Context,
    text: String,
    private var show: Boolean,
    length: Int,
    view: View?
) {
    private val snackBar: Snackbar = Snackbar.make(
        when (view == null) {
            true -> (this.context as Activity).findViewById(android.R.id.content)
            false -> view
        }, text, length
    )

    init {
        setBackGroundColor(R.color.snackBarBackground)
        if (this.show)
            show()
    }

    /**
     * Create snackBar with provided parameters, which will be displayed at the bottom of the current activity
     *
     * @param context of currently displayed activity
     * @param text is String, which will be displayed
     * @param show will display snackBar or wait till function show is called
     * @param length is int which represents number of milliseconds, when the snackBar will be display
     */
    constructor(context: Context, text: String, show: Boolean, length: Int) : this(
        context,
        text,
        show,
        length,
        null
    )

    /**
     * Create snackBar with provided parameters, which will be displayed at the bottom of the current activity
     *
     * @param context of currently displayed activity
     * @param text is int reference to String, which will be displayed
     * @param show will display snackBar or wait till function show is called
     * @param length is int which represents number of milliseconds, when the snackBar will be displayed
     */
    constructor(context: Context, text: Int, show: Boolean, length: Int) : this(
        context,
        MyString(context).fromResources(text),
        show,
        length,
        null
    )

    /**
     * Create snackBar with provided parameters
     *
     * @param context of currently displayed activity
     * @param text is String, which will be displayed
     * @param show will display snackBar or wait till function show is called
     * @param length is int which represents number of milliseconds, when the snackBar will be displayed
     * @param view snackBar will be shown at the bottom of the provided View. If you want to display it on the bottom of current activity, pass null
     */
    constructor(context: Context, text: Int, show: Boolean, length: Int, view: View?) : this(
        context,
        MyString(context).fromResources(text),
        show,
        length,
        view
    )

    /**
     * Create snackBar with provided parameters, which will be displayed immediately on the bottom of view. If you want to display it on the bottom of activity, pass null
     *
     * @param context of currently displayed activity
     * @param text is String, which will be displayed
     * @param view snackBar will be shown at the bottom of the provided View. If you want to display it on the bottom of current activity, pass null
     */
    constructor(context: Context, text: String, view: View?) : this(context, text, true, 2500, view)

    /**
     * Create snackBar with provided parameters, which will be displayed immediately on the bottom of view. If you want to display it on the bottom of activity, pass null
     *
     * @param context of currently displayed activity
     * @param text is int reference to String, which will be displayed
     * @param view snackBar will be shown at the bottom of the provided View. If you want to display it on the bottom of current activity, pass null
     */
    constructor(context: Context, text: Int, view: View?) : this(
        context,
        MyString(context).fromResources(text),
        view
    )

    /**
     * Create snackBar with provided parameters, which will be displayed immediately on the bottom of activity
     *
     * @param context of currently displayed activity
     * @param text is String, which will be displayed
     */
    constructor(context: Context, text: String) : this(context, text, null)

    /**
     * Create snackBar with provided parameters, which will be displayed immediately on the bottom of activity
     *
     * @param context of currently displayed activity
     * @param text is int reference to String, which will be displayed
     */
    constructor(context: Context, text: Int) : this(
        context,
        MyString(context).fromResources(text),
        null
    )

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
        if (this.show) {
            this.show = false
            getSnackBar().show()
        }
    }

    /**
     * @return context of displayed activity at the time of initialization
     */
    fun getContext(): Context {
        return this.context
    }

    /**
     * Sets snackBar background color
     * @param color is int reference to Color
     */
    fun setBackGroundColor(color: Int) {
        getSnackBar().view.setBackgroundColor(color)
    }


}

/**
 * Creates snackBar with no visible ui elements. Use can change snackBar background color by defining your own color with name "snackBarBackground" and text color with name "snackBarTextColor"
 *
 * @param context of currently displayed activity
 * @param show will display snackBar or wait till function show is called
 * @param length is int which represents number of milliseconds, when the snackBar will be displayed
 * @param view snackBar will be shown at the bottom of the provided View. If you want to display it on the bottom of current activity, pass null
 */
open class MySnackBarCustomLayout(context: Context, show: Boolean, length: Int, view: View?) :
    MySnackBar(context, "", show, length, view) {
    private val layout = getSnackBar().view as Snackbar.SnackbarLayout

    init {
        setBackgroundColor(R.color.snackBarBackground)
    }

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
 * Create snackBar with imageView and textView. Use can change snackBar background color by defining your own color with name "snackBarBackground" and text color with name "snackBarTextColor"
 *
 * @param context of currently displayed activity
 * @param text is String to be shown in textView
 * @param image is int reference to image that should be displayed in snackBar
 * @param length is int which represents number of milliseconds, when the snackBar will be displayed
 * @param show will display snackBar or wait till function show is called
 * @param view snackBar will be shown at the bottom of the provided View. If you want to display it on the bottom of current activity, pass null
 */
open class MySnackBarImageAndText(
    context: Context,
    text: String,
    image: Int,
    length: Int,
    show: Boolean,
    view: View?
) :
    MySnackBarCustomLayout(context, show, length, view) {
    private val customLayout = View.inflate(context, R.layout.snack_bar_with_imageview, null)
    private val textView = (this.customLayout.findViewById(R.id.snack_bar_text) as TextView)
    private val imageView = (this.customLayout.findViewById(R.id.snack_bar_icon) as ImageView)

    /**
     * Create snackBar with imageView and textView. Use can change snackBar background color by defining your own color with name "snackBarBackground" and text color with name "snackBarTextColor"
     *
     * @param context of currently displayed activity
     * @param text is int reference to String, which is going to be shown in textView
     * @param image is int reference to image that should be displayed in snackBar
     * @param length is int which represents number of milliseconds, when the snackBar will be displayed
     * @param show will display snackBar or wait till function show is called
     * @param view snackBar will be shown at the bottom of the provided View. If you want to display it on the bottom of current activity, pass null
     */
    constructor(
        context: Context,
        text: Int,
        image: Int,
        length: Int,
        show: Boolean,
        view: View?
    ) : this(
        context,
        MyString(context).fromResources(text),
        image,
        length,
        show,
        view
    )

    /**
     * Create snackBar with imageView and textView. Use can change snackBar background color by defining your own color with name "snackBarBackground" and text color with name "snackBarTextColor"
     *
     * @param context of currently displayed activity
     * @param text is String, which is going to be shown in textView
     * @param image is int reference to image that should be displayed in snackBar
     * @param length is int which represents number of milliseconds, when the snackBar will be displayed
     * @param show will display snackBar or wait till function show is called
     */
    constructor(context: Context, text: String, image: Int, length: Int, show: Boolean) : this(
        context,
        text,
        image,
        length,
        show,
        null
    )

    /**
     * Create snackBar with imageView and textView. Use can change snackBar background color by defining your own color with name "snackBarBackground" and text color with name "snackBarTextColor"
     *
     * @param context of currently displayed activity
     * @param text is int reference to String, which is going to be shown in textView
     * @param image is int reference to image that should be displayed in snackBar
     * @param length is int which represents number of milliseconds, when the snackBar will be displayed
     * @param show will display snackBar or wait till function show is called
     */
    constructor(context: Context, text: Int, image: Int, length: Int, show: Boolean) : this(
        context,
        MyString(context).fromResources(text),
        image,
        length,
        show,
        null
    )

    init {
        this.textView.text = text
        this.imageView.setImageResource(image)
        this.imageView.setColorFilter(ContextCompat.getColor(getContext(), R.color.textColor))
        getSnackBarLayout().addView(this.customLayout)
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

/**
 * Will display snackBar, which notifies user about successful login in to app
 *
 * @param context of currently displayed activity
 * @param userName is name of the user, which has login
 * @param view snackBar will be shown at the bottom of the provided View. If you want to display it on the bottom of current activity, pass null
 */
class MySnackBarLogin(context: Context, userName: String, view: View?) : MySnackBarImageAndText(
    context,
    context.getString(R.string.greetingUser, userName),
    R.drawable.ic_user,
    1500,
    true,
    view
) {
    /**
     * Will display snackBar on the bottom of the activity, which notifies user about successful login in to app
     *
     * @param context of currently displayed activity
     * @param userName is name of the user, which has login
     */
    constructor(context: Context, userName: String) : this(context, userName, null)
}

/**
 * Will display snackBar, which notifies user about successful result of operation
 *
 * @param context of currently displayed activity
 * @param text is String to be displayed
 * @param view snackBar will be shown at the bottom of the provided View. If you want to display it on the bottom of current activity, pass null
 */
class MySnackBarSuccess(context: Context, text: String, view: View?) :
    MySnackBarImageAndText(context, text, R.drawable.ic_success, 2500, true, view) {

    /**
     * Will display snackBar, which notifies user about successful result of operation
     *
     * @param context of currently displayed activity
     * @param text is int reference to String to be displayed
     * @param view snackBar will be shown at the bottom of the provided View. If you want to display it on the bottom of current activity, pass null
     */
    constructor(context: Context, text: Int, view: View?) : this(
        context,
        MyString(context).fromResources(text),
        view
    )

    /**
     * Will display snackBar on the bottom of the activity, which notifies user about successful result of operation
     *
     * @param context of currently displayed activity
     * @param text is String to be displayed
     */
    constructor(context: Context, text: String) : this(context, text, null)

    /**
     * Will display snackBar on the bottom of the activity, which notifies user about successful result of operation
     *
     * @param context of currently displayed activity
     * @param text is int reference to String to be displayed
     */
    constructor(context: Context, text: Int) : this(
        context,
        MyString(context).fromResources(text),
        null
    )

}

/**
 * Will display snackBar, which notifies user about unsuccessful result of operation
 *
 * @param context of currently displayed activity
 * @param text is String to be displayed
 * @param view snackBar will be shown at the bottom of the provided View. If you want to display it on the bottom of current activity, pass null
 */
class MySnackBarFail(context: Context, text: String, view: View?) :
    MySnackBarImageAndText(context, text, R.drawable.ic_fail, 4000, true, view) {

    /**
     * Will display snackBar, which notifies user about unsuccessful result of operation
     *
     * @param context of currently displayed activity
     * @param text is int reference to String to be displayed
     * @param view snackBar will be shown at the bottom of the provided View. If you want to display it on the bottom of current activity, pass null
     */
    constructor(context: Context, text: Int, view: View?) : this(
        context,
        MyString(context).fromResources(text),
        view
    )

    /**
     * Will display snackBar on the bottom of the activity, which notifies user about unsuccessful result of operation
     *
     * @param context of currently displayed activity
     * @param text is String to be displayed
     */
    constructor(context: Context, text: String) : this(context, text, null)

    /**
     * Will display snackBar on the bottom of the activity, which notifies user about unsuccessful result of operation
     *
     * @param context of currently displayed activity
     * @param text is int reference to String to be displayed
     */
    constructor(context: Context, text: Int) : this(
        context,
        MyString(context).fromResources(text),
        null
    )

}

/**
 * Will display snackBar, which displays warning
 *
 * @param context of currently displayed activity
 * @param text is String to be displayed
 * @param view snackBar will be shown at the bottom of the provided View. If you want to display it on the bottom of current activity, pass null
 */
class MySnackBarWarning(context: Context, text: String, view: View?) :
    MySnackBarImageAndText(context, text, R.drawable.ic_warning, 2500, true, view) {

    /**
     * Will display snackBar, which displays warning
     *
     * @param context of currently displayed activity
     * @param text is int reference to String to be displayed
     * @param view snackBar will be shown at the bottom of the provided View. If you want to display it on the bottom of current activity, pass null
     */
    constructor(context: Context, text: Int, view: View?) : this(
        context,
        MyString(context).fromResources(text),
        view
    )

    /**
     * Will display snackBar on the bottom of the activity, which displays warning
     *
     * @param context of currently displayed activity
     * @param text is String to be displayed
     */
    constructor(context: Context, text: String) : this(context, text, null)

    /**
     * Will display snackBar on the bottom of the activity, which displays warning
     *
     * @param context of currently displayed activity
     * @param text is int reference to String to be displayed
     */
    constructor(context: Context, text: Int) : this(
        context,
        MyString(context).fromResources(text),
        null
    )
}

/**
 * Will display snackBar, which displays notification
 *
 * @param context of currently displayed activity
 * @param text is String to be displayed
 * @param view snackBar will be shown at the bottom of the provided View. If you want to display it on the bottom of current activity, pass null
 */
class MySnackBarNotification(context: Context, text: String, view: View?) :
    MySnackBarImageAndText(context, text, R.drawable.ic_notification, 2500, true, view) {

    /**
     * Will display snackBar, which displays notification
     *
     * @param context of currently displayed activity
     * @param text is int reference to String to be displayed
     * @param view snackBar will be shown at the bottom of the provided View. If you want to display it on the bottom of current activity, pass null
     */
    constructor(context: Context, text: Int, view: View?) : this(
        context,
        MyString(context).fromResources(text),
        view
    )

    /**
     * Will display snackBar on the bottom of the activity, which displays notification
     *
     * @param context of currently displayed activity
     * @param text is String to be displayed
     */
    constructor(context: Context, text: String) : this(context, text, null)

    /**
     * Will display snackBar on the bottom of the activity, which displays notification
     *
     * @param context of currently displayed activity
     * @param text is int reference to String to be displayed
     */
    constructor(context: Context, text: Int) : this(
        context,
        MyString(context).fromResources(text),
        null
    )
}
