@file:Suppress("unused", "MemberVisibilityCanBePrivate", "UNUSED_VALUE")

package com.kaufmannmarek.library

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap


/**
 * Creates dialog with provided parameters
 *
 * @param context of currently displayed activity
 * @param title is text, which will displayed at the top of the dialog
 * @param message is text, which will displayed below title
 * @param icon is int reference to drawable, which will displayed next to title
 * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
 */

open class Dialog(
    private val context: Context,
    title: String,
    message: String?,
    icon: Int,
    hasScrollElement: Boolean
) {

    private val view = View.inflate(
        this.context, when (hasScrollElement) {
            true -> R.layout.dialog
            else -> R.layout.dialog_scroll_view
        }, null
    )
    private val imageView = this.view.findViewById(R.id.dialog_icon) as ImageView
    private val title = this.view.findViewById(R.id.dialog_title) as TextView
    private val message = this.view.findViewById(R.id.dialog_message) as TextView
    private val contentLayout =
        this.view.findViewById(R.id.dialog_content) as MaxHeightLinearLayout
    private lateinit var dialog: AlertDialog

    init {
        this.imageView.setImageResource(icon)
        this.title.text = title
        if (message != null) {
            this.message.text = message
            this.message.movementMethod = ScrollingMovementMethod()
        } else
            this.message.visibility = View.GONE
        setDialogMaxHeight()
        showDialog()
    }

    /**
     * Gets maximum height for content section
     */
    fun setDialogMaxHeight() {
        val myScreen = MyScreen(this.context)
        getContentLayout().setMaxHeightDp((myScreen.getScreenHeightDensityPoints() - 200))
    }

    /**
     * @return root view of the dialog
     */
    fun getView(): View {
        return this.view
    }

    fun setDialogBackground(backgroundColor: Int) {
        this.view.setBackgroundColor(backgroundColor)
    }

    /**
     * @return context of the dialog
     */
    fun getContext(): Context {
        return this.context
    }

    /**
     * dismiss currently displayed dialog
     */
    fun dismiss() {
        getDialog().dismiss()
    }

    /**
     * Sets text to button
     *
     * @param button, where the text will be displayed
     * @param text is String, which will be displayed in the button
     */
    fun setButtonText(button: Button, text: String) {
        button.text = text
    }

    /**
     * initialize and show dialog if it was not displayed in constructor
     */
    private fun showDialog() {
        if (!::dialog.isInitialized) {
            this.dialog = AlertDialog.Builder(getContext()).create()
            this.dialog.setView(view)
            this.dialog.setCancelable(false)
            this.dialog.show()
            this.dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
            this.dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }
    }

    /**
     * add view to content section of dialog
     */
    open fun addContentView(view: View) {
        getContentLayout().addView(view)
    }

    /**
     * @return LinearLayout, where the additional content is placed
     */
    fun getContentLayout(): MaxHeightLinearLayout {
        return this.contentLayout
    }

    /**
     * @return view inflated view of dialog
     */
    fun getDialogView(): View {
        return this.view
    }

    /**
     * @return dialog instance
     */
    private fun getDialog(): AlertDialog {
        return this.dialog
    }
}

/**
 * Creates dialog with one button
 *
 * @param context of currently displayed activity
 * @param title is text, which will displayed at the top of the dialog
 * @param message is text, which will displayed below title
 * @param icon is int reference to drawable, which will displayed next to title
 * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
 * @param buttonText is text, which will displayed on button. If you pass null, it display "Done"
 */
open class DialogOneButton(
    context: Context,
    title: String,
    message: String,
    icon: Int,
    hasScrollElement: Boolean,
    buttonText: String?
) : Dialog(context, title, message, icon, hasScrollElement) {
    private val rightButton = getView().findViewById(R.id.dialog_right_button) as Button

    init {
        setRightButtonText(
            when (buttonText == null) {
                true -> {
                    setRightButtonAction()
                    MyString(getContext()).fromResources(R.string.done)
                }
                else -> buttonText
            }
        )
    }

    /**
     * Creates dialog with one button
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param buttonText is text, which will displayed on button. If you pass null, it display "Done"
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean,
        buttonText: String?
    ) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        icon,
        hasScrollElement,
        buttonText
    )

    /**
     * Creates dialog with one button
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param buttonText is text, which will displayed on button. If you pass null, it display "Done"
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        icon: Int,
        hasScrollElement: Boolean,
        buttonText: String?
    ) : this(
        context,
        MyString(context).fromResources(title),
        message,
        icon,
        hasScrollElement,
        buttonText
    )

    /**
     * Creates dialog with one button
     *
     * @param context of currently displayed activity
     * @param title is String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param buttonText is text, which will displayed on button. If you pass null, it display "Done"
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean,
        buttonText: String?
    ) : this(
        context,
        title,
        MyString(context).fromResources(message),
        icon,
        hasScrollElement,
        buttonText
    )

    /**
     * Creates dialog with one button
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param buttonText is int reference to String, which will displayed on button
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean,
        buttonText: Int
    ) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        icon,
        hasScrollElement,
        MyString(context).fromResources(buttonText)
    )

    /**
     * Creates dialog with one button
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param buttonText is int reference to String, which will displayed on button
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        icon: Int,
        hasScrollElement: Boolean,
        buttonText: Int
    ) : this(
        context,
        MyString(context).fromResources(title),
        message,
        icon,
        hasScrollElement,
        MyString(context).fromResources(buttonText)
    )

    /**
     * Creates dialog with one button
     *
     * @param context of currently displayed activity
     * @param title is String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param buttonText is int reference to String, which will displayed on button
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean,
        buttonText: Int
    ) : this(
        context,
        title,
        MyString(context).fromResources(message),
        icon,
        hasScrollElement,
        MyString(context).fromResources(buttonText)
    )

    /**
     * Creates dialog with one button
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean
    ) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        icon,
        hasScrollElement,
        null
    )

    /**
     * Creates dialog with one button
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        icon: Int,
        hasScrollElement: Boolean
    ) : this(
        context,
        MyString(context).fromResources(title), message, icon, hasScrollElement, null
    )

    /**
     * Creates dialog with one button
     *
     * @param context of currently displayed activity
     * @param title is String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean
    ) : this(context, title, MyString(context).fromResources(message), icon, hasScrollElement, null)

    /**
     * Creates dialog with one button
     *
     * @param context of currently displayed activity
     * @param title is String, which will displayed at the top of the dialog
     * @param message is String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     */
    constructor(
        context: Context,
        title: String,
        message: String,
        icon: Int,
        hasScrollElement: Boolean
    ) : this(context, title, message, icon, hasScrollElement, null)

    /**
     *@return instance of bottom right button in dialog
     */
    fun getRightButton(): Button {
        return this.rightButton
    }

    /**
     * Sets actions on right button click
     */
    fun setRightButtonAction() {
        getRightButton().setOnClickListener {
            dismiss()
        }
    }

    /**
     * Sets text to right button
     *
     * @param text is String, which will be displayed in the button
     */
    fun setRightButtonText(text: String) {
        setButtonText(getRightButton(), text)
    }

    /**
     * Sets text to right button
     *
     * @param text is int reference to String, which will be displayed in the button
     */
    fun setRightButtonText(text: Int) {
        setRightButtonText(getContext().getString(text))
    }
}

/**
 * Creates dialog with title (text and icon) and message with dismiss button
 *
 * @param context of activity, where the dialog will displayed
 * @param title is String, which will displayed at the top of the dialog
 * @param message is String, which will displayed below title
 * @param icon is int reference to drawable, which will displayed next to title
 */
class NotificationDialog(context: Context, title: String, message: String, icon: Int) :
    DialogOneButton(context, title, message, icon, false) {

    /**
     * Creates dialog with title (text and icon) and message with dismiss button
     *
     * @param context of activity, where the dialog will displayed
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     */
    constructor(context: Context, title: String, message: Int, icon: Int) : this(
        context,
        title,
        MyString(context).fromResources(message),
        icon
    )

    /**
     * Creates dialog with title (text and icon) and message with dismiss button
     *
     * @param context of activity, where the dialog will displayed
     * @param title is String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     */
    constructor(context: Context, title: Int, message: String, icon: Int) : this(
        context,
        MyString(context).fromResources(title),
        message,
        icon
    )

    /**
     * Creates dialog with title (text and icon) and message with dismiss button
     *
     * @param context of activity, where the dialog will displayed
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     */
    constructor(context: Context, title: Int, message: Int, icon: Int) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        icon
    )

    /**
     * Will not add any view to this dialog, since no content elements are allowed. Only textView displaying message is allowed
     *
     * @param view is any member of view class
     */
    override fun addContentView(view: View) {

    }
}


/**
 * Creates dialog with date picker. After selecting the date a pressing corresponding button, it will set date and tag with number of elapsed days to provided editText
 *
 * @param context of currently displayed activity
 * @param message is int reference to String, which will be displayed under title
 * @param icon is int reference to drawable, which will be displayed on left side of title
 * @param editTextToUpdate is editText, which will be updated
 */
class DatePickerDialog(
    context: Context,
    message: Int,
    icon: Int,
    private val editTextToUpdate: EditText?
) : DialogTwoButtons(
    context,
    R.string.selectDate,
    message,
    icon,
    false
) {
    private val datePicker = DatePicker(getContext())

    init {
        addContentView(getDatePicker())
        setRightButtonText(R.string.select)
        if (this.editTextToUpdate != null) {
            getRightButton().setOnClickListener {
                this.editTextToUpdate.setText(getStringDate())
                this.editTextToUpdate.tag = getIntDate()
                dismiss()
            }
        }
    }

    /**
     * Creates dialog with date picker
     *
     * @param context of currently displayed activity
     * @param message is int reference to String, which will be displayed under title
     * @param icon is int reference to drawable, which will be displayed on left side of title
     */
    constructor(
        context: Context,
        message: Int,
        icon: Int
    ) : this(context, message, icon, null)


    /**
     * @return instance of datePickerView
     */
    fun getDatePicker(): DatePicker {
        return this.datePicker
    }

    /**
     * @return date as String in format D.M.yyyy
     */
    fun getStringDate(): String {
        return StringBuilder().append(getDatePicker().dayOfMonth).append(".")
            .append(getDatePicker().month + 1).append(".").append(getDatePicker().year).toString()
    }

    /**
     * @return number of days since the default date. See class CustomDate for details
     */
    fun getIntDate(): Int {
        return MyDate().getDifferenceBetweenDays(getStringDate())
    }
}

/**
 * Creates dialog with provided parameters and two buttons. Do not forget to set your custom action on buttons click
 *
 * @param context of currently displayed activity
 * @param title is text, which will displayed at the top of the dialog
 * @param message is text, which will displayed below title
 * @param icon is int reference to drawable, which will displayed next to title
 * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
 * @param rightButtonText is String, which will be displayed at bottom right button
 */
open class DialogTwoButtons(
    context: Context,
    title: String,
    message: String,
    icon: Int,
    hasScrollElement: Boolean,
    rightButtonText: String?
) :
    DialogOneButton(context, title, message, icon, hasScrollElement, rightButtonText) {


    /**
     * Creates dialog with provided parameters and two buttons
     *
     * @param context of currently displayed activity
     * @param title is text, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param rightButtonText is String, which will be displayed at bottom right button
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        icon: Int,
        hasScrollElement: Boolean,
        rightButtonText: String?
    ) : this(
        context,
        MyString(context).fromResources(title),
        message,
        icon,
        hasScrollElement,
        rightButtonText
    )

    /**
     * Creates dialog with provided parameters and two buttons
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param rightButtonText is int reference to String, which will be displayed at bottom right button
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean,
        rightButtonText: Int
    ) : this(
        context,
        title,
        MyString(context).fromResources(message),
        icon,
        hasScrollElement,
        MyString(context).fromResources(rightButtonText)
    )

    /**
     * Creates dialog with provided parameters and two buttons
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param rightButtonText is int reference to String, which will be displayed at bottom right button
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean,
        rightButtonText: Int
    ) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        icon,
        hasScrollElement,
        MyString(context).fromResources(rightButtonText)
    )


    /**
     * Creates dialog with provided parameters and two buttons
     *
     * @param context of currently displayed activity
     * @param title is text, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param rightButtonText is int reference to String, which will be displayed at bottom right button
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        icon: Int,
        hasScrollElement: Boolean,
        rightButtonText: Int
    ) : this(
        context,
        MyString(context).fromResources(title),
        message,
        icon,
        hasScrollElement,
        MyString(context).fromResources(rightButtonText)
    )

    /**
     * Creates dialog with provided parameters and two buttons
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param rightButtonText is String, which will be displayed at bottom right button
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean,
        rightButtonText: String?
    ) : this(
        context,
        title,
        MyString(context).fromResources(message),
        icon,
        hasScrollElement,
        rightButtonText
    )

    /**
     * Creates dialog with provided parameters and two buttons
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param rightButtonText is String, which will be displayed at bottom right button
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean,
        rightButtonText: String?
    ) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        icon,
        hasScrollElement,
        rightButtonText
    )


    /**
     * Creates dialog with provided parameters and two buttons
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        icon: Int,
        hasScrollElement: Boolean
    ) : this(
        context,
        MyString(context).fromResources(title),
        message,
        icon,
        hasScrollElement,
        null
    )

    /**
     * Creates dialog with provided parameters and two buttons
     *
     * @param context of currently displayed activity
     * @param title is String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean
    ) : this(
        context,
        title,
        MyString(context).fromResources(message),
        icon,
        hasScrollElement,
        null
    )

    /**
     * Creates dialog with provided parameters and two buttons
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean
    ) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        icon,
        hasScrollElement,
        null
    )

    /**
     * Creates dialog with provided parameters and two buttons
     *
     * @param context of currently displayed activity
     * @param title is String, which will displayed at the top of the dialog
     * @param message is String reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     */
    constructor(
        context: Context,
        title: String,
        message: String,
        icon: Int,
        hasScrollElement: Boolean
    ) : this(
        context,
        title,
        message,
        icon,
        hasScrollElement,
        null
    )

    private val leftButton = getView().findViewById(R.id.dialog_left_button) as Button

    init {
        getLeftButton().visibility = View.VISIBLE
        setLeftButtonAction()
        (getView().findViewById(R.id.dialog_left_space) as Space).visibility = View.GONE
    }

    /**
     * @return left button of the dialog
     */
    fun getLeftButton(): Button {
        return this.leftButton
    }

    /**
     * Sets left button default action. Onclick it will dismiss the dialog
     */
    internal fun setLeftButtonAction() {
        getLeftButton().setOnClickListener {
            dismiss()
        }
    }

    /**
     * Sets text of the left button
     */
    fun setLeftButtonText(text: Int) {
        setButtonText(getLeftButton(), getContext().getString(text))
    }
}

/**
 * Creates dialog with provided parameters and three buttons
 *
 * @param context of currently displayed activity
 * @param title is String, which will displayed at the top of the dialog
 * @param message is String, which will displayed below title
 * @param icon is int reference to drawable, which will displayed next to title
 * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
 * @param rightButtonText is String, which will be displayed on right bottom button
 * @param middleButtonText is String, which will be displayed on middle bottom button
 */
class DialogThreeButtons(
    context: Context,
    title: String,
    message: String,
    icon: Int,
    hasScrollElement: Boolean,
    rightButtonText: String?,
    middleButtonText: String?
) :
    DialogTwoButtons(context, title, message, icon, hasScrollElement, rightButtonText) {

    /**
     * Creates dialog with provided parameters and three buttons
     *
     * @param context of currently displayed activity
     * @param title is String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean
    ) : this(
        context,
        title,
        MyString(context).fromResources(message),
        icon,
        hasScrollElement,
        null,
        null
    )

    /**
     * Creates dialog with provided parameters and three buttons
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        icon: Int,
        hasScrollElement: Boolean
    ) : this(
        context,
        MyString(context).fromResources(title),
        message,
        icon,
        hasScrollElement,
        null,
        null
    )

    /**
     * Creates dialog with provided parameters and three buttons
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean
    ) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        icon,
        hasScrollElement,
        null,
        null
    )

    /**
     * Creates dialog with provided parameters and three buttons
     *
     * @param context of currently displayed activity
     * @param title is String, which will displayed at the top of the dialog
     * @param message is String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     */
    constructor(
        context: Context,
        title: String,
        message: String,
        icon: Int,
        hasScrollElement: Boolean
    ) : this(context, title, message, icon, hasScrollElement, null, null)


    /**
     * Creates dialog with provided parameters and three buttons
     *
     * @param context of currently displayed activity
     * @param title is String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param rightButtonText is String, which will be displayed on right bottom button
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean,
        rightButtonText: String?
    ) : this(
        context,
        title,
        MyString(context).fromResources(message),
        icon,
        hasScrollElement,
        rightButtonText,
        null
    )

    /**
     * Creates dialog with provided parameters and three buttons
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param rightButtonText is String, which will be displayed on right bottom button
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        icon: Int,
        hasScrollElement: Boolean,
        rightButtonText: String?
    ) : this(
        context,
        MyString(context).fromResources(title),
        message,
        icon,
        hasScrollElement,
        rightButtonText,
        null
    )

    /**
     * Creates dialog with provided parameters and three buttons
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param rightButtonText is String, which will be displayed on right bottom button
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean,
        rightButtonText: String?
    ) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        icon,
        hasScrollElement,
        rightButtonText,
        null
    )

    /**
     * Creates dialog with provided parameters and three buttons
     *
     * @param context of currently displayed activity
     * @param title is String, which will displayed at the top of the dialog
     * @param message is String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param rightButtonText is String, which will be displayed on right bottom button
     */
    constructor(
        context: Context,
        title: String,
        message: String,
        icon: Int,
        hasScrollElement: Boolean,
        rightButtonText: String?
    ) : this(context, title, message, icon, hasScrollElement, rightButtonText, null)

    /**
     * Creates dialog with provided parameters and three buttons
     *
     * @param context of currently displayed activity
     * @param title is String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param rightButtonText is String, which will be displayed on right bottom button
     * @param middleButtonText is String, which will be displayed on middle bottom button
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean,
        rightButtonText: String?,
        middleButtonText: String?
    ) : this(
        context,
        title,
        MyString(context).fromResources(message),
        icon,
        hasScrollElement,
        rightButtonText,
        middleButtonText
    )

    /**
     * Creates dialog with provided parameters and three buttons
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param rightButtonText is String, which will be displayed on right bottom button
     * @param middleButtonText is String, which will be displayed on middle bottom button
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        icon: Int,
        hasScrollElement: Boolean,
        rightButtonText: String?,
        middleButtonText: String?
    ) : this(
        context,
        MyString(context).fromResources(title),
        message,
        icon,
        hasScrollElement,
        rightButtonText,
        middleButtonText
    )

    /**
     * Creates dialog with provided parameters and three buttons
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param rightButtonText is String, which will be displayed on right bottom button
     * @param middleButtonText is String, which will be displayed on middle bottom button
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean,
        rightButtonText: String?,
        middleButtonText: String?
    ) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        icon,
        hasScrollElement,
        rightButtonText,
        middleButtonText
    )

    /**
     * Creates dialog with provided parameters and three buttons
     *
     * @param context of currently displayed activity
     * @param title is String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param rightButtonText is int reference to String, which will be displayed on right bottom button
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean,
        rightButtonText: Int
    ) : this(
        context,
        title,
        MyString(context).fromResources(message),
        icon,
        hasScrollElement,
        MyString(context).fromResources(rightButtonText),
        null
    )

    /**
     * Creates dialog with provided parameters and three buttons
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param rightButtonText is int reference to String, which will be displayed on right bottom button
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        icon: Int,
        hasScrollElement: Boolean,
        rightButtonText: Int
    ) : this(
        context,
        MyString(context).fromResources(title),
        message,
        icon,
        hasScrollElement,
        MyString(context).fromResources(rightButtonText),
        null
    )

    /**
     * Creates dialog with provided parameters and three buttons
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param rightButtonText is int reference to String, which will be displayed on right bottom button
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean,
        rightButtonText: Int
    ) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        icon,
        hasScrollElement,
        MyString(context).fromResources(rightButtonText),
        null
    )

    /**
     * Creates dialog with provided parameters and three buttons
     *
     * @param context of currently displayed activity
     * @param title is String, which will displayed at the top of the dialog
     * @param message is String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param rightButtonText is int reference to String, which will be displayed on right bottom button
     */
    constructor(
        context: Context,
        title: String,
        message: String,
        icon: Int,
        hasScrollElement: Boolean,
        rightButtonText: Int
    ) : this(
        context,
        title,
        message,
        icon,
        hasScrollElement,
        MyString(context).fromResources(rightButtonText),
        null
    )

    /**
     * Creates dialog with provided parameters and three buttons
     *
     * @param context of currently displayed activity
     * @param title is String, which will displayed at the top of the dialog
     * @param message is String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param rightButtonText is int reference to String, which will be displayed on right bottom button
     * @param middleButtonText is int reference to String, which will be displayed on middle bottom button
     */
    constructor(
        context: Context,
        title: String,
        message: String,
        icon: Int,
        hasScrollElement: Boolean,
        rightButtonText: Int,
        middleButtonText: Int
    ) : this(
        context,
        title,
        message,
        icon,
        hasScrollElement,
        MyString(context).fromResources(rightButtonText),
        MyString(context).fromResources(middleButtonText)
    )

    /**
     * Creates dialog with provided parameters and three buttons
     *
     * @param context of currently displayed activity
     * @param title is String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param rightButtonText is int reference to String, which will be displayed on right bottom button
     * @param middleButtonText is int reference to String, which will be displayed on middle bottom button
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean,
        rightButtonText: Int,
        middleButtonText: Int
    ) : this(
        context,
        title,
        MyString(context).fromResources(message),
        icon,
        hasScrollElement,
        MyString(context).fromResources(rightButtonText),
        MyString(context).fromResources(middleButtonText)
    )

    /**
     * Creates dialog with provided parameters and three buttons
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param rightButtonText is int reference to String, which will be displayed on right bottom button
     * @param middleButtonText is int reference to String, which will be displayed on middle bottom button
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        icon: Int,
        hasScrollElement: Boolean,
        rightButtonText: Int,
        middleButtonText: Int
    ) : this(
        context,
        MyString(context).fromResources(title),
        message,
        icon,
        hasScrollElement,
        MyString(context).fromResources(rightButtonText),
        MyString(context).fromResources(middleButtonText)
    )

    /**
     * Creates dialog with provided parameters and three buttons
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
     * @param icon is int reference to drawable, which will displayed next to title
     * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
     * @param rightButtonText is int reference to String, which will be displayed on right bottom button
     * @param middleButtonText is int reference to String, which will be displayed on middle bottom button
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean,
        rightButtonText: Int,
        middleButtonText: Int
    ) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        icon,
        hasScrollElement,
        MyString(context).fromResources(rightButtonText),
        MyString(context).fromResources(middleButtonText)
    )

    private val middleButton = getView().findViewById(R.id.dialog_middle_button) as Button

    init {
        if (middleButtonText != null) {
            getMiddleButton().text = middleButtonText
        }
        getMiddleButton().visibility = View.VISIBLE
        (getView().findViewById(R.id.dialog_middle_space) as Space).visibility = View.GONE
    }

    /**
     * @return middle button in the dialog
     */
    fun getMiddleButton(): Button {
        return this.middleButton
    }

    /**
     * Assign provided String as text to middle button of the dialog
     *
     * @param text to be assigned
     */
    fun setMiddleButtonText(text: String) {
        setButtonText(getMiddleButton(), text)
    }

    /**
     * Assign provided String as text to middle button of the dialog
     *
     * @param text is int reference to String, which will be assigned
     */
    fun setMiddleButtonText(text: Int) {
        setMiddleButtonText(MyString(getContext()).fromResources(text))
    }
}

/**
 * Create ListView dialog in current context with provided items
 *
 * @param context of currently displayed activity
 * @param title is String, which will be displayed at the top of dialog
 * @param message is String, which will be displayed under the title
 * @param icon is int reference to drawable, which will be displayed next to title
 * @param editTextToUpdate is reference to editText, which will be updated on item select
 */
@Suppress("LeakingThis")
open class ListViewDialog private constructor(
    context: Context,
    title: String,
    message: String,
    icon: Int,
    private val editTextToUpdate: EditText?
) : DialogOneButton(context, title, message, icon, true) {

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at the top of dialog
     * @param message is String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param arrayList its elements will be displayed
     * @param sortAlphabetically if true, the elements will alphabetically sorted
     * @param editTextToUpdate is reference to editText, which will be updated on item select
     */
    constructor(
        context: Context,
        title: String,
        message: String,
        icon: Int,
        arrayList: ArrayList<String>,
        sortAlphabetically: Boolean,
        editTextToUpdate: EditText?
    ) : this(
        context,
        title,
        message,
        icon,
        editTextToUpdate
    ) {
        val newArrayList = ArrayList<ListViewDialogItem>()
        if (sortAlphabetically) {
            val defaultHashMap = LinkedHashMap<String, Int>()
            val sortedArrayList = ArrayList<String>()
            for (index in 0 until arrayList.size) {
                defaultHashMap[arrayList[index]] = index
                sortedArrayList.add(arrayList[index])
            }
            sortedArrayList.sort()
            for (param in sortedArrayList) {
                newArrayList.add(ListViewDialogItem(defaultHashMap[param]!!, param, param, null))
            }
        } else {
            for (index in 0 until arrayList.size) {
                newArrayList.add(
                    ListViewDialogItem(
                        index,
                        arrayList[index],
                        arrayList[index],
                        null
                    )
                )
            }
        }
        setupDialog(newArrayList)
    }

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at the top of dialog
     * @param message is String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param arrayList its elements will be displayed
     * @param sortAlphabetically if true, the elements will alphabetically sorted
     * @param editTextToUpdate is reference to editText, which will be updated on item select
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        icon: Int,
        arrayList: ArrayList<String>,
        sortAlphabetically: Boolean,
        editTextToUpdate: EditText?
    ) : this(
        context,
        MyString(context).fromResources(title),
        message,
        icon,
        arrayList,
        sortAlphabetically,
        editTextToUpdate
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at the top of dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param arrayList its elements will be displayed
     * @param sortAlphabetically if true, the elements will alphabetically sorted
     * @param editTextToUpdate is reference to editText, which will be updated on item select
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        icon: Int,
        arrayList: ArrayList<String>,
        sortAlphabetically: Boolean,
        editTextToUpdate: EditText?
    ) : this(
        context,
        title,
        MyString(context).fromResources(message),
        icon,
        arrayList,
        sortAlphabetically,
        editTextToUpdate
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at the top of dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param arrayList its elements will be displayed
     * @param sortAlphabetically if true, the elements will alphabetically sorted
     * @param editTextToUpdate is reference to editText, which will be updated on item select
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        arrayList: ArrayList<String>,
        sortAlphabetically: Boolean,
        editTextToUpdate: EditText?
    ) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        icon,
        arrayList,
        sortAlphabetically,
        editTextToUpdate
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at the top of dialog
     * @param message is String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param arrayList its elements will be displayed
     * @param sortAlphabetically if true, the elements will alphabetically sorted
     */
    constructor(
        context: Context,
        title: String,
        message: String,
        icon: Int,
        arrayList: ArrayList<String>,
        sortAlphabetically: Boolean
    ) : this(
        context,
        title,
        message,
        icon,
        arrayList,
        sortAlphabetically,
        null
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at the top of dialog
     * @param message is String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param arrayList its elements will be displayed
     * @param sortAlphabetically if true, the elements will alphabetically sorted
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        icon: Int,
        arrayList: ArrayList<String>,
        sortAlphabetically: Boolean
    ) : this(
        context,
        MyString(context).fromResources(title),
        message,
        icon,
        arrayList,
        sortAlphabetically,
        null
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at the top of dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param arrayList its elements will be displayed
     * @param sortAlphabetically if true, the elements will alphabetically sorted
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        icon: Int,
        arrayList: ArrayList<String>,
        sortAlphabetically: Boolean
    ) : this(
        context,
        title,
        MyString(context).fromResources(message),
        icon,
        arrayList,
        sortAlphabetically,
        null
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at the top of dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param arrayList its elements will be displayed
     * @param sortAlphabetically if true, the elements will alphabetically sorted
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        arrayList: ArrayList<String>,
        sortAlphabetically: Boolean
    ) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        icon,
        arrayList,
        sortAlphabetically,
        null
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at the top of dialog
     * @param message is String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param linkedHashMap its elements will be displayed
     * @param editTextToUpdate is editText, which will be updated on item select
     */
    constructor(
        context: Context,
        title: String,
        message: String,
        icon: Int,
        linkedHashMap: LinkedHashMap<Int, ListViewDialogItem>,
        editTextToUpdate: EditText?
    ) : this(
        context,
        title,
        message,
        icon,
        editTextToUpdate
    ) {
        val arrayList = ArrayList<ListViewDialogItem>()
        for (key in linkedHashMap.keys) {
            arrayList.add(linkedHashMap[key]!!)
        }
        setupDialog(arrayList)
    }

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at the top of dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param linkedHashMap its elements will be displayed
     * @param editTextToUpdate is editText, which will be updated on item select
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        icon: Int,
        linkedHashMap: LinkedHashMap<Int, ListViewDialogItem>,
        editTextToUpdate: EditText?
    ) : this(
        context,
        title,
        MyString(context).fromResources(message),
        icon,
        linkedHashMap,
        editTextToUpdate
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at the top of dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param linkedHashMap its elements will be displayed
     * @param editTextToUpdate is editText, which will be updated on item select
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        icon: Int,
        linkedHashMap: LinkedHashMap<Int, ListViewDialogItem>,
        editTextToUpdate: EditText?
    ) : this(
        context,
        MyString(context).fromResources(title),
        message,
        icon,
        linkedHashMap,
        editTextToUpdate
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at the top of dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param linkedHashMap its elements will be displayed
     * @param editTextToUpdate is editText, which will be updated on item select
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        linkedHashMap: LinkedHashMap<Int, ListViewDialogItem>,
        editTextToUpdate: EditText?
    ) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        icon,
        linkedHashMap,
        editTextToUpdate
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at the top of dialog
     * @param message is String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param linkedHashMap its elements will be displayed
     */
    constructor(
        context: Context,
        title: String,
        message: String,
        icon: Int,
        linkedHashMap: LinkedHashMap<Int, ListViewDialogItem>
    ) : this(
        context,
        title,
        message,
        icon,
        linkedHashMap,
        null
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at the top of dialog
     * @param message is String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param linkedHashMap its elements will be displayed
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        icon: Int,
        linkedHashMap: LinkedHashMap<Int, ListViewDialogItem>
    ) : this(
        context,
        MyString(context).fromResources(title),
        message,
        icon,
        linkedHashMap,
        null
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at the top of dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param linkedHashMap its elements will be displayed
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        icon: Int,
        linkedHashMap: LinkedHashMap<Int, ListViewDialogItem>
    ) : this(
        context,
        title,
        MyString(context).fromResources(message),
        icon,
        linkedHashMap,
        null
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at the top of dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param linkedHashMap its elements will be displayed
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        linkedHashMap: LinkedHashMap<Int, ListViewDialogItem>
    ) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        icon,
        linkedHashMap,
        null
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at the top of dialog
     * @param message is String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param array is String array, which elements will be displayed
     * @param sortAlphabetically if true, the elements will alphabetically sorted
     * @param editTextToUpdate is editText, which will be updated on item select
     */
    constructor(
        context: Context,
        title: String,
        message: String,
        icon: Int,
        array: Array<String>,
        sortAlphabetically: Boolean,
        editTextToUpdate: EditText?
    ) : this(context, title, message, icon, editTextToUpdate) {
        val arrayList = ArrayList<ListViewDialogItem>()
        if (sortAlphabetically) {
            val defaultHashMap = HashMap<String, Int>()
            val sortedArrayList = ArrayList<String>()
            for (index in array.indices) {
                sortedArrayList.add(array[index])
                defaultHashMap[array[index]] = index
            }
            sortedArrayList.sort()
            for (param in sortedArrayList) {
                arrayList.add(ListViewDialogItem(defaultHashMap[param]!!, param, param, null))
            }
        } else {
            for (index in array.indices) {
                arrayList.add(ListViewDialogItem(index, array[index], array[index], null))
            }
        }
        setupDialog(arrayList)
    }

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at the top of dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param array is String array, which elements will be displayed
     * @param sortAlphabetically if true, the elements will alphabetically sorted
     * @param editTextToUpdate is editText, which will be updated on item select
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        icon: Int,
        array: Array<String>,
        sortAlphabetically: Boolean,
        editTextToUpdate: EditText?
    ) : this(
        context,
        title,
        MyString(context).fromResources(message),
        icon,
        array,
        sortAlphabetically,
        editTextToUpdate
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at the top of dialog
     * @param message is String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param array is String array, which elements will be displayed
     * @param sortAlphabetically if true, the elements will alphabetically sorted
     * @param editTextToUpdate is editText, which will be updated on item select
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        icon: Int,
        array: Array<String>,
        sortAlphabetically: Boolean,
        editTextToUpdate: EditText?
    ) : this(
        context,
        MyString(context).fromResources(title),
        message,
        icon,
        array,
        sortAlphabetically,
        editTextToUpdate
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at the top of dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param array is String array, which elements will be displayed
     * @param sortAlphabetically if true, the elements will alphabetically sorted
     * @param editTextToUpdate is editText, which will be updated on item select
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        array: Array<String>,
        sortAlphabetically: Boolean,
        editTextToUpdate: EditText?
    ) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        icon,
        array,
        sortAlphabetically,
        editTextToUpdate
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at the top of dialog
     * @param message is String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param stringArrayReference is int reference to String array
     * @param sortAlphabetically if true, the elements will alphabetically sorted
     * @param editTextToUpdate is editText, which will be updated on item select
     */
    constructor(
        context: Context,
        title: String,
        message: String,
        icon: Int,
        stringArrayReference: Int,
        sortAlphabetically: Boolean,
        editTextToUpdate: EditText?
    ) : this(
        context,
        title,
        message,
        icon,
        context.resources.getStringArray(stringArrayReference),
        sortAlphabetically,
        editTextToUpdate
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at the top of dialog
     * @param message is String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param stringArrayReference is int reference to String array
     * @param sortAlphabetically if true, the elements will alphabetically sorted
     * @param editTextToUpdate is editText, which will be updated on item select
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        icon: Int,
        stringArrayReference: Int,
        sortAlphabetically: Boolean,
        editTextToUpdate: EditText?
    ) : this(
        context,
        MyString(context).fromResources(title),
        message,
        icon,
        context.resources.getStringArray(stringArrayReference),
        sortAlphabetically,
        editTextToUpdate
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at the top of dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param stringArrayReference is int reference to String array
     * @param sortAlphabetically if true, the elements will alphabetically sorted
     * @param editTextToUpdate is editText, which will be updated on item select
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        icon: Int,
        stringArrayReference: Int,
        sortAlphabetically: Boolean,
        editTextToUpdate: EditText?
    ) : this(
        context,
        title,
        MyString(context).fromResources(message),
        icon,
        context.resources.getStringArray(stringArrayReference),
        sortAlphabetically,
        editTextToUpdate
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at the top of dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param stringArrayReference is int reference to String array
     * @param sortAlphabetically if true, the elements will alphabetically sorted
     * @param editTextToUpdate is editText, which will be updated on item select
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        stringArrayReference: Int,
        sortAlphabetically: Boolean,
        editTextToUpdate: EditText?
    ) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        icon,
        context.resources.getStringArray(stringArrayReference),
        sortAlphabetically,
        editTextToUpdate
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at the top of dialog
     * @param message is String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param stringArrayReference is int reference to String array
     * @param sortAlphabetically if true, the elements will alphabetically sorted
     */
    constructor(
        context: Context,
        title: String,
        message: String,
        icon: Int,
        stringArrayReference: Int,
        sortAlphabetically: Boolean
    ) : this(
        context,
        title,
        message,
        icon,
        context.resources.getStringArray(stringArrayReference),
        sortAlphabetically,
        null
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at the top of dialog
     * @param message is String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param stringArrayReference is int reference to String array
     * @param sortAlphabetically if true, the elements will alphabetically sorted
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        icon: Int,
        stringArrayReference: Int,
        sortAlphabetically: Boolean
    ) : this(
        context,
        MyString(context).fromResources(title),
        message,
        icon,
        context.resources.getStringArray(stringArrayReference),
        sortAlphabetically,
        null
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at the top of dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param stringArrayReference is int reference to String array
     * @param sortAlphabetically if true, the elements will alphabetically sorted
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        icon: Int,
        stringArrayReference: Int,
        sortAlphabetically: Boolean
    ) : this(
        context,
        title,
        MyString(context).fromResources(message),
        icon,
        context.resources.getStringArray(stringArrayReference),
        sortAlphabetically,
        null
    )

    /**
     * Create ListView dialog in current context with provided items
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at the top of dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param stringArrayReference is int reference to String array
     * @param sortAlphabetically if true, the elements will alphabetically sorted
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        stringArrayReference: Int,
        sortAlphabetically: Boolean
    ) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        icon,
        context.resources.getStringArray(stringArrayReference),
        sortAlphabetically,
        null
    )

    private lateinit var adapter: Adapter

    /**
     * Assign adapter to the listView and sets all required elements
     */
    private fun setupDialog(sourceArrayList: ArrayList<ListViewDialogItem>) {
        val listView = ListView(getContext())
        val dialogElements = DialogElements(getContext())
        val editText = dialogElements.getSearchEditText()
        val textView = dialogElements.getNoItemFoundTextView()
        editText.addTextChangedListener {
            if (editText.text.isEmpty())
                getAdapter().setData(sourceArrayList)
            else {
                val arrayList = ArrayList<ListViewDialogItem>()
                for (item in sourceArrayList) {
                    if (item.text.toLowerCase(Locale.GERMANY)
                            .contains(editText.text.toString().toLowerCase(Locale.GERMANY))
                    )
                        arrayList.add(item)
                }
                getAdapter().setData(arrayList)
            }
        }
        addContentView(editText)
        addContentView(textView)
        addContentView(listView)
        this.adapter = Adapter(getContext(), sourceArrayList, textView, listView)
        listView.adapter = this.adapter
        if (this.editTextToUpdate != null)
            setOnItemClick()
    }

    /**
     * @return adapter of the listView
     */
    private fun getAdapter(): Adapter {
        return this.adapter
    }

    /**
     * @return item position in default array. If the item do not exists in array, it will return -1
     * @param position is int position in the listView
     */
    fun getItemValue(position: Int): Any {
        return this.adapter.getItemValue(position)
    }

    /**
     * @return item position in provided unsorted array. If the array was not sorted, the position will equal position in the dialog
     * @param position is int position in the listView
     */
    fun getItemOriginalPosition(position: Int): Int {
        return this.adapter.getItemOriginalPosition(position)
    }

    /**
     * @return item stored in provided position of listView
     * @param position is int position in the listView
     */
    fun getItem(position: Int): ListViewDialogItem {
        return this.adapter.getItem(position)
    }

    /**
     * @return listView in the dialog
     */
    fun getListView(): ListView {
        return this.adapter.getListView()
    }


    /**
     * Sets on item click
     */
    private fun setOnItemClick() {
        getListView().setOnItemClickListener { _, _, position, _ ->
            this.editTextToUpdate!!.setText(this.adapter.getItemText(position))
            this.editTextToUpdate.tag = getItemValue(position)
            dismiss()
        }
    }

    private class Adapter(
        private val context: Context,
        private var arrayList: ArrayList<ListViewDialogItem>,
        private val textView: TextView,
        private val listView: ListView
    ) : BaseAdapter() {
        private val layoutInflater = LayoutInflater.from(context)


        init {
            setData(this.arrayList)
        }

        fun setData(arrayList: ArrayList<ListViewDialogItem>) {
            setData(arrayList, false)
        }

        fun setData(arrayList: ArrayList<ListViewDialogItem>, isFiltered: Boolean) {
            this.arrayList = arrayList
            notifyDataSetChanged()
            when (count) {
                0 -> {
                    this.textView.text = MyString(this.context).fromResources(
                        when (isFiltered) {
                            true -> R.string.noItemsFound
                            false -> R.string.noItemsToDisplay
                        }
                    )
                    this.textView.visibility = View.VISIBLE
                    this.listView.visibility = View.GONE
                }
                else -> {
                    this.textView.visibility = View.GONE
                    this.listView.visibility = View.VISIBLE
                }
            }
        }

        /**
         * @return instance of listView
         */
        fun getListView(): ListView {
            return this.listView
        }

        /**
         * Get a View that displays the data at the specified position in the data set. You can either
         * create a View manually or inflate it from an XML layout file. When the View is inflated, the
         * parent View (GridView, ListView...) will apply default layout parameters unless you use
         * [android.view.LayoutInflater.inflate]
         * to specify a root view and to prevent attachment to the root.
         *
         * @param position The position of the item within the adapter's data set of the item whose view
         * we want.
         * @param convertView The old view to reuse, if possible. Note: You should check that this view
         * is non-null and of an appropriate type before using. If it is not possible to convert
         * this view to display the correct data, this method can create a new view.
         * Heterogeneous lists can specify their number of view types, so that this View is
         * always of the right type (see [.getViewTypeCount] and
         * [.getItemViewType]).
         * @param parent The parent that this view will eventually be attached to
         * @return A View corresponding to the data at the specified position.
         */
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View?
            val viewHolder: ViewHolder
            if (convertView == null) {
                view = this.layoutInflater.inflate(R.layout.item_dialog_textview, parent, false)
                viewHolder = ViewHolder(view)
                view.tag = viewHolder
            } else {
                view = convertView
                viewHolder = view.tag as ViewHolder
            }
            if (getItemDrawable(position) != null) {
                viewHolder.imageView.background = getItemDrawable(position)
                viewHolder.imageView.visibility = View.VISIBLE
            } else
                viewHolder.imageView.visibility = View.GONE
            viewHolder.dialogItem.text = getItemText(position)
            return view!!
        }

        /**
         * Get the data item associated with the specified position in the data set.
         *
         * @param position Position of the item whose data we want within the adapter's
         * data set.
         * @return The data at the specified position.
         */
        override fun getItem(position: Int): ListViewDialogItem {
            return this.arrayList[position]
        }

        /**
         * @return String to display for the dialogItem
         * @param position is row number of the item in the listView
         */
        fun getItemText(position: Int): String {
            return getItem(position).text
        }

        /**
         * @return value to retrieve associated with the dialogItem
         * @param position is row number of the item in the listView
         */
        fun getItemValue(position: Int): Any {
            return getItem(position).value
        }

        /**
         * @return item position in provided unsorted array. If the array was not sorted, the position will equal position in the dialog
         * @param position is row number of the item in the listView
         */
        fun getItemOriginalPosition(position: Int): Int {
            return getItem(position).position
        }

        /**
         * @return drawable associated with the dialogItem. If there is no drawable, returns null
         * @param position is row number of the item in the listView
         */
        fun getItemDrawable(position: Int): Drawable? {
            return getItem(position).drawable
        }

        /**
         * Get the row id associated with the specified position in the list.
         *
         * @param position The position of the item within the adapter's data set whose row id we want.
         * @return The id of the item at the specified position.
         */
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        /**
         * How many items are in the data set represented by this Adapter.
         *
         * @return Count of items.
         */
        override fun getCount(): Int {
            return this.arrayList.count()
        }

        private class ViewHolder(view: View) {
            val dialogItem = view.findViewById(R.id.item_dialog) as TextView
            val imageView = view.findViewById(R.id.image_view) as ImageView
        }
    }
}

/**
 * Creates dialog with a listView inside it. The list contains items, which consist checkable textView
 * @param context of currently displayed activity
 * @param title is String, which will be displayed at a top of the dialog
 * @param message is String, which will be displayed under the title
 */
class CheckBoxesDialog private constructor(
    context: Context,
    title: String,
    message: String
) : DialogTwoButtons(context, title, message, R.drawable.round_check_box_white_24dp, true) {

    /**
     * Creates dialog with a listView inside it. The list contains items, which consist checkable textView
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at a top of the dialog
     * @param message is String, which will be displayed under the title
     * @param linkedHashMap is linkedHashMap with values saved under keys. The values will be displayed in the dialog
     * @param alreadySelectedItems is arrayList of Ints, which represents elements, which were selected in previous instance of the dialog. Providing these keys will check the items with provided keys
     */
    constructor(
        context: Context,
        title: String,
        message: String,
        linkedHashMap: LinkedHashMap<Int, CheckBoxDialogItem>,
        alreadySelectedItems: ArrayList<Int>?
    ) : this(context, title, message) {
        val arrayList = ArrayList<CheckBoxDialogItem>()
        for (key in linkedHashMap.keys) {
            arrayList.add(linkedHashMap[key]!!)
        }
        setupDialog(arrayList, alreadySelectedItems)
    }

    /**
     * Creates dialog with a listView inside it. The list contains items, which consist checkable textView
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at a top of the dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param linkedHashMap is linkedHashMap with values saved under keys. The values will be displayed in the dialog
     * @param alreadySelectedItems is arrayList of Ints, which represents elements, which were selected in previous instance of the dialog. Providing these keys will check the items with provided keys
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        linkedHashMap: LinkedHashMap<Int, CheckBoxDialogItem>,
        alreadySelectedItems: ArrayList<Int>?
    ) : this(
        context,
        title,
        MyString(context).fromResources(message),
        linkedHashMap,
        alreadySelectedItems
    )

    /**
     * Creates dialog with a listView inside it. The list contains items, which consist checkable textView
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at a top of the dialog
     * @param message is String, which will be displayed under the title
     * @param linkedHashMap is linkedHashMap with values saved under keys. The values will be displayed in the dialog
     * @param alreadySelectedItems is arrayList of Ints, which represents elements, which were selected in previous instance of the dialog. Providing these keys will check the items with provided keys
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        linkedHashMap: LinkedHashMap<Int, CheckBoxDialogItem>,
        alreadySelectedItems: ArrayList<Int>?
    ) : this(
        context,
        MyString(context).fromResources(title),
        message,
        linkedHashMap,
        alreadySelectedItems
    )

    /**
     * Creates dialog with a listView inside it. The list contains items, which consist checkable textView
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at a top of the dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param linkedHashMap is linkedHashMap with values saved under keys. The values will be displayed in the dialog
     * @param alreadySelectedItems is arrayList of Ints, which represents elements, which were selected in previous instance of the dialog. Providing these keys will check the items with provided keys
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        linkedHashMap: LinkedHashMap<Int, CheckBoxDialogItem>,
        alreadySelectedItems: ArrayList<Int>?
    ) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        linkedHashMap,
        alreadySelectedItems
    )

    /**
     * Creates dialog with a listView inside it. The list contains items, which consist checkable textView
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at a top of the dialog
     * @param message is String, which will be displayed under the title
     * @param arrayList is ArrayList, which will be displayed in the dialog
     * @param alreadySelectedItems is arrayList of Ints, which represents elements, which were selected in previous instance of the dialog. Providing these keys will check the items with provided keys
     */
    constructor(
        context: Context,
        title: String,
        message: String,
        arrayList: ArrayList<String>,
        sortAlphabetically: Boolean,
        alreadySelectedItems: ArrayList<Int>?
    ) : this(context, title, message) {
        val newArrayList = ArrayList<CheckBoxDialogItem>()
        if (sortAlphabetically) {
            val defaultHashMap = LinkedHashMap<String, Int>()
            val sortedArrayList = ArrayList<String>()
            for (index in 0 until arrayList.size) {
                defaultHashMap[arrayList[index]] = index
                sortedArrayList.add(arrayList[index])
            }
            sortedArrayList.sort()
            for (param in sortedArrayList) {
                newArrayList.add(CheckBoxDialogItem(defaultHashMap[param]!!, param, null))
            }
        } else {
            for (index in 0 until arrayList.size) {
                newArrayList.add(CheckBoxDialogItem(index, arrayList[index], null))
            }
        }
        setupDialog(newArrayList, alreadySelectedItems)
    }

    /**
     * Creates dialog with a listView inside it. The list contains items, which consist checkable textView
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at a top of the dialog
     * @param message is String, which will be displayed under the title
     * @param arrayList is ArrayList, which will be displayed in the dialog
     * @param alreadySelectedItems is arrayList of Ints, which represents elements, which were selected in previous instance of the dialog. Providing these keys will check the items with provided keys
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        arrayList: ArrayList<String>,
        sortAlphabetically: Boolean,
        alreadySelectedItems: ArrayList<Int>?
    ) : this(
        context,
        MyString(context).fromResources(title),
        message,
        arrayList,
        sortAlphabetically,
        alreadySelectedItems
    )

    /**
     * Creates dialog with a listView inside it. The list contains items, which consist checkable textView
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at a top of the dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param arrayList is ArrayList, which will be displayed in the dialog
     * @param alreadySelectedItems is arrayList of Ints, which represents elements, which were selected in previous instance of the dialog. Providing these keys will check the items with provided keys
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        arrayList: ArrayList<String>,
        sortAlphabetically: Boolean,
        alreadySelectedItems: ArrayList<Int>?
    ) : this(
        context,
        title,
        MyString(context).fromResources(message),
        arrayList,
        sortAlphabetically,
        alreadySelectedItems
    )

    /**
     * Creates dialog with a listView inside it. The list contains items, which consist checkable textView
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at a top of the dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param arrayList is ArrayList, which will be displayed in the dialog
     * @param alreadySelectedItems is arrayList of Ints, which represents elements, which were selected in previous instance of the dialog. Providing these keys will check the items with provided keys
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        arrayList: ArrayList<String>,
        sortAlphabetically: Boolean,
        alreadySelectedItems: ArrayList<Int>?
    ) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        arrayList,
        sortAlphabetically,
        alreadySelectedItems
    )

    /**
     * Creates dialog with a listView inside it. The list contains items, which consist checkable textView
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at a top of the dialog
     * @param message is String, which will be displayed under the title
     * @param array is String array, which will be displayed in the dialog
     * @param sortAlphabetically if true, the array values will be sorted alphabetically
     * @param alreadySelectedItems is arrayList of Ints, which represents elements, which were selected in previous instance of the dialog. Providing these keys will check the items with provided keys
     */
    constructor(
        context: Context,
        title: String,
        message: String,
        array: Array<String>,
        sortAlphabetically: Boolean,
        alreadySelectedItems: ArrayList<Int>?
    ) : this(context, title, message) {
        val arrayList = ArrayList<CheckBoxDialogItem>()
        if (sortAlphabetically) {
            val defaultHashMap = HashMap<String, Int>()
            val sortedArrayList = ArrayList<String>()
            for (index in array.indices) {
                sortedArrayList.add(array[index])
                defaultHashMap[array[index]] = index
            }
            sortedArrayList.sort()
            for (param in sortedArrayList) {
                arrayList.add(CheckBoxDialogItem(defaultHashMap[param]!!, param, null))
            }
        } else {
            for (index in array.indices) {
                arrayList.add(CheckBoxDialogItem(index, array[index], null))
            }
        }
        setupDialog(arrayList, alreadySelectedItems)
    }

    /**
     * Creates dialog with a listView inside it. The list contains items, which consist checkable textView
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at a top of the dialog
     * @param message is String, which will be displayed under the title
     * @param array is String array, which will be displayed in the dialog
     * @param sortAlphabetically if true, the array values will be sorted alphabetically
     * @param alreadySelectedItems is arrayList of Ints, which represents elements, which were selected in previous instance of the dialog. Providing these keys will check the items with provided keys
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        array: Array<String>,
        sortAlphabetically: Boolean,
        alreadySelectedItems: ArrayList<Int>?
    ) : this(
        context,
        MyString(context).fromResources(title),
        message,
        array,
        sortAlphabetically,
        alreadySelectedItems
    )

    /**
     * Creates dialog with a listView inside it. The list contains items, which consist checkable textView
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at a top of the dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param array is String array, which will be displayed in the dialog
     * @param sortAlphabetically if true, the array values will be sorted alphabetically
     * @param alreadySelectedItems is arrayList of Ints, which represents elements, which were selected in previous instance of the dialog. Providing these keys will check the items with provided keys
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        array: Array<String>,
        sortAlphabetically: Boolean,
        alreadySelectedItems: ArrayList<Int>?
    ) : this(
        context,
        title,
        MyString(context).fromResources(message),
        array,
        sortAlphabetically,
        alreadySelectedItems
    )

    /**
     * Creates dialog with a listView inside it. The list contains items, which consist checkable textView
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at a top of the dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param array is String array, which will be displayed in the dialog
     * @param sortAlphabetically if true, the array values will be sorted alphabetically
     * @param alreadySelectedItems is arrayList of Ints, which represents elements, which were selected in previous instance of the dialog. Providing these keys will check the items with provided keys
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        array: Array<String>,
        sortAlphabetically: Boolean,
        alreadySelectedItems: ArrayList<Int>?
    ) : this(
        context,
        MyString(context).fromResources(title),
        MyString(context).fromResources(message),
        array,
        sortAlphabetically,
        alreadySelectedItems
    )

    /**
     * Creates dialog with a listView inside it. The list contains items, which consist checkable textView
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at a top of the dialog
     * @param message is String, which will be displayed under the title
     * @param arrayReference is int reference to String array, which will be displayed in the dialog
     * @param sortAlphabetically if true, the array values will be sorted alphabetically
     * @param alreadySelectedItems is arrayList of Ints, which represents elements, which were selected in previous instance of the dialog. Providing these keys will check the items with provided keys
     */
    constructor(
        context: Context,
        title: String,
        message: String,
        arrayReference: Int,
        sortAlphabetically: Boolean,
        alreadySelectedItems: ArrayList<Int>?
    ) : this(
        context,
        title,
        message,
        context.resources.getStringArray(arrayReference),
        sortAlphabetically,
        alreadySelectedItems
    )

    /**
     * Creates dialog with a listView inside it. The list contains items, which consist checkable textView
     * @param context of currently displayed activity
     * @param title is String, which will be displayed at a top of the dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param arrayReference is int reference to String array, which will be displayed in the dialog
     * @param sortAlphabetically if true, the array values will be sorted alphabetically
     * @param alreadySelectedItems is arrayList of Ints, which represents elements, which were selected in previous instance of the dialog. Providing these keys will check the items with provided keys
     */
    constructor(
        context: Context,
        title: String,
        message: Int,
        arrayReference: Int,
        sortAlphabetically: Boolean,
        alreadySelectedItems: ArrayList<Int>?
    ) : this(
        context,
        title,
        message,
        context.resources.getStringArray(arrayReference),
        sortAlphabetically,
        alreadySelectedItems
    )

    /**
     * Creates dialog with a listView inside it. The list contains items, which consist checkable textView
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at a top of the dialog
     * @param message is String, which will be displayed under the title
     * @param arrayReference is int reference to String array, which will be displayed in the dialog
     * @param sortAlphabetically if true, the array values will be sorted alphabetically
     * @param alreadySelectedItems is arrayList of Ints, which represents elements, which were selected in previous instance of the dialog. Providing these keys will check the items with provided keys
     */
    constructor(
        context: Context,
        title: Int,
        message: String,
        arrayReference: Int,
        sortAlphabetically: Boolean,
        alreadySelectedItems: ArrayList<Int>?
    ) : this(
        context,
        title,
        message,
        context.resources.getStringArray(arrayReference),
        sortAlphabetically,
        alreadySelectedItems
    )

    /**
     * Creates dialog with a listView inside it. The list contains items, which consist checkable textView
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at a top of the dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param arrayReference is int reference to String array, which will be displayed in the dialog
     * @param sortAlphabetically if true, the array values will be sorted alphabetically
     * @param alreadySelectedItems is arrayList of Ints, which represents elements, which were selected in previous instance of the dialog. Providing these keys will check the items with provided keys
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        arrayReference: Int,
        sortAlphabetically: Boolean,
        alreadySelectedItems: ArrayList<Int>?
    ) : this(
        context,
        title,
        message,
        context.resources.getStringArray(arrayReference),
        sortAlphabetically,
        alreadySelectedItems
    )

    private lateinit var adapter: Adapter

    /**
     * Sets all required elements of the listViewDialog
     */
    private fun setupDialog(
        sourceArrayList: ArrayList<CheckBoxDialogItem>,
        alreadySelectedItems: ArrayList<Int>?
    ) {
        val listView = ListView(getContext())
        val dialogElements = DialogElements(getContext())
        val editText = dialogElements.getSearchEditText()
        this.adapter = Adapter(
            getContext(),
            dialogElements.getNoItemFoundTextView(),
            listView,
            sourceArrayList,
            alreadySelectedItems
        )
        editText.addTextChangedListener {
            this.adapter.setData(
                if (editText.text.isEmpty())
                    sourceArrayList
                else {
                    val filteredArrayList = ArrayList<CheckBoxDialogItem>()
                    for (item in sourceArrayList) {
                        @SuppressLint("DefaultLocale")
                        if (item.text.toLowerCase()
                                .contains(editText.text.toString().toLowerCase())
                        )
                            filteredArrayList.add(item)
                    }
                    filteredArrayList
                }
            )
        }
        addContentView(editText)
        listView.adapter = this.adapter
        addContentView(listView)
    }

    /**
     * @return arrayList of keys, which were selected in the dialog
     */
    fun getSelectedItems(): ArrayList<Int> {
        return this.adapter.getSelectedItems()
    }

    /**
     * @return adapter assigned to listView
     */
    private fun getAdapter(): Adapter {
        return this.adapter
    }

    /**
     * @return listView, where the items are displayed
     */
    fun getListView(): ListView {
        return getAdapter().getListView()
    }

    /**
     * @return value displayed in provided row position
     * @param position in the listView
     */
    fun getValueToDisplay(position: Int): String {
        return getAdapter().getItem(position).text
    }

    /**
     * @return position of the item in original array. If the array was not sorted, it equals to the position in dialog
     * @param position in the listView
     */
    fun getItemOriginalPosition(position: Int): Int {
        return getAdapter().getItem(position).position
    }

    /**
     * @return drawable associated with the item
     * @param position in the listView
     */
    fun getItemDrawable(position: Int): Drawable? {
        return getAdapter().getItem(position).drawable
    }

    private class Adapter(
        private val context: Context,
        private val noItemFoundTextView: TextView,
        private val listView: ListView,
        arrayList: ArrayList<CheckBoxDialogItem>,
        selectedItems: ArrayList<Int>?
    ) :
        BaseAdapter() {
        private val layoutInflater = LayoutInflater.from(this.context)
        private var selectedItems: ArrayList<Int> = when (selectedItems == null) {
            true -> ArrayList()
            else -> selectedItems
        }
        private lateinit var arrayList: ArrayList<CheckBoxDialogItem>

        init {
            setData(arrayList)
        }

        /**
         * @param arrayList are values to populate listView
         * sets data to display
         */
        fun setData(arrayList: ArrayList<CheckBoxDialogItem>) {
            this.arrayList = arrayList
            notifyDataSetChanged()
            when (count) {
                0 -> {
                    this.noItemFoundTextView.text =
                        MyString(this.context).fromResources(R.string.noItemsFound)
                    this.noItemFoundTextView.visibility = View.VISIBLE
                    this.listView.visibility = View.GONE
                }
                else -> {
                    this.noItemFoundTextView.visibility = View.GONE
                    this.listView.visibility = View.VISIBLE
                }
            }
        }

        /**
         * @return adapter assigned to listView
         */
        fun getValueToDisplay(position: Int): String {
            return getItem(position).text
        }

        /**
         * @return position of the item in original array. If the array was not sorted, it equals to the position in dialog
         * @param position in the listView
         */
        fun getItemOriginalPosition(position: Int): Int {
            return getItem(position).position
        }

        /**
         * @return drawable associated with the item
         * @param position in the listView
         */
        fun getItemDrawable(position: Int): Drawable? {
            return getItem(position).drawable
        }

        /**
         * @return selected items keys
         */
        fun getSelectedItems(): ArrayList<Int> {
            return this.selectedItems
        }

        /**
         * @return listView, where the items are displayed
         */
        fun getListView(): ListView {
            return this.listView
        }

        /**
         * Get a View that displays the data at the specified position in the data set. You can either
         * create a View manually or inflate it from an XML layout file. When the View is inflated, the
         * parent View (GridView, ListView...) will apply default layout parameters unless you use
         * [android.view.LayoutInflater.inflate]
         * to specify a root view and to prevent attachment to the root.
         *
         * @param position The position of the item within the adapter's data set of the item whose view
         * we want.
         * @param convertView The old view to reuse, if possible. Note: You should check that this view
         * is non-null and of an appropriate type before using. If it is not possible to convert
         * this view to display the correct data, this method can create a new view.
         * Heterogeneous lists can specify their number of view types, so that this View is
         * always of the right type (see [.getViewTypeCount] and
         * [.getItemViewType]).
         * @param parent The parent that this view will eventually be attached to
         * @return A View corresponding to the data at the specified position.
         */
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View?
            val viewHolder: ViewHolder
            if (convertView == null) {
                view = this.layoutInflater.inflate(
                    R.layout.item_dialog_checkbox,
                    parent,
                    false
                )
                viewHolder = ViewHolder(view)
                view.tag = viewHolder
            } else {
                view = convertView
                viewHolder = view.tag as ViewHolder
            }
            viewHolder.checkBox.text = getValueToDisplay(position)
            viewHolder.checkBox.tag = getItem(position)
            if (getItemDrawable(position) != null) {
                viewHolder.imageView.visibility = View.VISIBLE
                viewHolder.imageView.background = getItemDrawable(position)
            } else
                viewHolder.imageView.visibility = View.GONE
            viewHolder.checkBox.isChecked =
                this.selectedItems.contains(getItemOriginalPosition(position))
            viewHolder.checkBox.setOnClickListener {
                if (viewHolder.checkBox.isChecked) {
                    this.selectedItems.remove(getItemOriginalPosition(position))
                    viewHolder.checkBox.isChecked = false
                } else {
                    this.selectedItems.add(getItemOriginalPosition(position))
                    viewHolder.checkBox.isChecked = true
                }
            }
            return view!!
        }

        /**
         * Get the data item associated with the specified position in the data set.
         *
         * @param position Position of the item whose data we want within the adapter's
         * data set.
         * @return The data at the specified position.
         */
        override fun getItem(position: Int): CheckBoxDialogItem {
            return this.arrayList[position]
        }

        /**
         * Get the row id associated with the specified position in the list.
         *
         * @param position The position of the item within the adapter's data set whose row id we want.
         * @return The id of the item at the specified position.
         */
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        /**
         * How many items are in the data set represented by this Adapter.
         *
         * @return Count of items.
         */
        override fun getCount(): Int {
            return this.arrayList.size
        }

        private class ViewHolder(view: View) {
            val checkBox = view.findViewById(R.id.check_box) as CheckedTextView
            val imageView = view.findViewById(R.id.image_view) as ImageView
        }
    }
}

/**
 * Creates object for CheckBoxDialog
 *
 * @param position is position in the original array
 * @param text is String, which describes the item
 * @param drawable is image, which will be displayed next to text
 */
data class CheckBoxDialogItem(val position: Int, val text: String, val drawable: Drawable?) {

    /**
     * Creates object for CheckBoxDialog
     *
     * @param position is position in the original array
     * @param text is String, which describes the item
     */
    constructor(position: Int, text: String) : this(position, text, null)

    /**
     * Creates object for CheckBoxDialog
     *
     * @param context of currently displayed activity
     * @param position is position in the original array
     * @param text is int reference to String, which describes the item
     */
    constructor(context: Context, position: Int, text: Int) : this(
        position,
        MyString(context).fromResources(text)
    )

    /**
     * Creates object for CheckBoxDialog
     *
     * @param context of currently displayed activity
     * @param position is position in the original array
     * @param text is int reference to String, which describes the item
     * @param drawable is image, which will be displayed next to text
     */
    constructor(
        context: Context,
        position: Int,
        text: Int,
        drawable: Drawable
    ) : this(position, MyString(context).fromResources(text), drawable)
}

/**
 * Creates object for ListViewDialog
 *
 * @param position is position in the original array
 * @param text is String, which describes the item
 * @param value is value, which can retrieved by a method
 * @param drawable is image, which will be displayed next to text
 */
data class ListViewDialogItem(
    val position: Int,
    val text: String,
    val value: Any,
    val drawable: Drawable?
) {
    /**
     * Creates object for ListViewDialog
     *
     * @param position is position in the original array
     * @param text is String, which describes the item
     * @param value is value, which can retrieved by a method
     */
    constructor(position: Int, text: String, value: Any) : this(position, text, value, null)

    /**
     * Creates object for ListViewDialog
     *
     * @param context of currently displayed activity
     * @param position is position in the original array
     * @param text is int reference to String, which describes the item
     * @param value is value, which can retrieved by a method
     * @param drawable is image, which will be displayed next to text
     */
    constructor(context: Context, position: Int, text: Int, value: Any, drawable: Drawable?) : this(
        position,
        MyString(context).fromResources(text),
        value,
        drawable
    )

    /**
     * Creates object for ListViewDialog
     *
     * @param context of currently displayed activity
     * @param position is position in the original array
     * @param text is int reference to String, which describes the item
     * @param value is value, which can retrieved by a method
     */
    constructor(context: Context, position: Int, text: Int, value: Any) : this(
        position,
        MyString(context).fromResources(text),
        value,
        null
    )

}

private class DialogElements(private val context: Context) {

    fun getSearchEditText(): EditText {
        val editText = EditText(this.context)
        editText.setHintTextColor(ContextCompat.getColor(this.context, R.color.hintColor))
        editText.setTextColor(ContextCompat.getColor(this.context, R.color.textColor))
        editText.textSize = 18f
        editText.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.round_search_white_18dp,
            0, 0, 0
        )

        editText.hint = MyString(context).fromResources(R.string.hintSearch)
        return editText
    }

    fun getNoItemFoundTextView(): TextView {
        val textView = TextView(this.context)
        textView.setTextColor(ContextCompat.getColor(this.context, R.color.textColor))
        textView.textSize = 18f
        textView.setPadding(15, 15, 0, 5)
        textView.visibility = View.GONE
        return textView
    }

}