@file:Suppress("unused", "MemberVisibilityCanBePrivate", "UNUSED_VALUE")

package com.kaufmannmarek.library

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener

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
    message: String,
    icon: Int,
    hasScrollElement: Boolean
) {

    private val view = View.inflate(
        this.context, when (hasScrollElement) {
            true -> R.layout.dialog
            else -> R.layout.dialog_scroll_view
        }, null
    )
    private val title = this.view.findViewById(R.id.dialog_title) as TextView
    private val message = this.view.findViewById(R.id.dialog_message) as TextView
    private val content = this.view.findViewById(R.id.dialog_content) as LinearLayout
    private lateinit var dialog: AlertDialog

    init {
        this.title.setCompoundDrawablesWithIntrinsicBounds(
            icon,
            0,
            0,
            0
        )
        this.title.text = title
        this.title.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
        this.message.text = message
        showDialog()
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
    fun addView(view: View) {
        this.content.addView(view)
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
 */
open class DialogOneButton(
    context: Context,
    title: String,
    message: String,
    icon: Int,
    hasScrollElement: Boolean
) : Dialog(context, title, message, icon, hasScrollElement) {
    private val rightButton = getView().findViewById(R.id.dialog_right_button) as Button

    init {
        setRightButtonAction()
        setRightButtonText(R.string.cancel)
    }

    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean
    ) : this(
        context,
        context.getString(title),
        context.getString(message),
        icon,
        hasScrollElement
    )

    constructor(
        context: Context,
        title: Int,
        message: String,
        icon: Int,
        hasScrollElement: Boolean
    ) : this(context, context.getString(title), message, icon, hasScrollElement)

    constructor(
        context: Context,
        title: String,
        message: Int,
        icon: Int,
        hasScrollElement: Boolean
    ) : this(context, title, context.getString(message), icon, hasScrollElement)

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
    fun setRightButtonText(text: Int) {
        setButtonText(getRightButton(), getContext().getString(text))
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
        addView(getDatePicker())
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
 */
open class DialogTwoButtons(
    context: Context,
    title: String,
    message: String,
    icon: Int,
    hasScrollElement: Boolean
) :
    DialogOneButton(context, title, message, icon, hasScrollElement) {

    /**
     * Creates dialog with provided parameters and two buttons
     *
     * @param context of currently displayed activity
     * @param title is text, which will displayed at the top of the dialog
     * @param message is int reference to String, which will displayed below title
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
        context.getString(title),
        message,
        icon,
        hasScrollElement
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
        context.getString(title),
        context.getString(message),
        icon,
        hasScrollElement
    )

    private val leftButton = getView().findViewById(R.id.dialog_left_button) as Button
    private val leftSpace = getView().findViewById(R.id.dialog_left_space) as Space

    init {
        getLeftButton().visibility = View.VISIBLE
        setLeftButtonText(R.string.cancel)
        setLeftButtonAction()
        this.leftSpace.visibility = View.GONE
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
 * @param title is int reference to String, which will displayed at the top of the dialog
 * @param message is int reference to String, which will displayed below title
 * @param icon is int reference to drawable, which will displayed next to title
 * @param hasScrollElement is condition. If true, it use layout without scrollView. Else it will
 */
class DialogThreeButtons(
    context: Context,
    title: Int,
    message: Int,
    icon: Int,
    hasScrollElement: Boolean
) :
    DialogTwoButtons(context, title, message, icon, hasScrollElement) {
    private val middleButton = getView().findViewById(R.id.dialog_middle_button) as Button
    private val middleSpace = getView().findViewById(R.id.dialog_middle_space) as Space

    init {
        getMiddleButton().visibility = View.VISIBLE
        this.middleSpace.visibility = View.GONE
    }

    /**
     * @return middle button in the dialog
     */
    fun getMiddleButton(): Button {
        return this.middleButton
    }

    fun setMiddleButtonText(text: Int) {
        setButtonText(getMiddleButton(), getContext().getString(text))
    }
}

/**
 * Create ListView dialog in current context with provided items which can be either stored in HashMap or stored under string array in resources
 *
 * @param context of currently displayed activity
 * @param title is int reference to String, which will be displayed at the top of dialog
 * @param message is int reference to String, which will be displayed under the title
 * @param icon is int reference to drawable, which will be displayed next to title
 * @param editTextToUpdate is reference to editText, which can be updated
 */
class ListViewDialog private constructor(
    context: Context,
    title: Int,
    message: Int,
    icon: Int,
    private val editTextToUpdate: EditText?
) : DialogOneButton(context, title, message, icon, true) {
    private val listView = ListView(getContext())
    private val editText: EditText
    private val textView: TextView
    private var hashMap = HashMap<String, Any>()
    private lateinit var adapter: Adapter

    init {
        val dialogElements = DialogElements(getContext())
        this.editText = dialogElements.getSearchEditText()
        this.textView = dialogElements.getNoItemFoundTextView()
        if (this.editTextToUpdate != null)
            setOnItemClick()
        this.editText.addTextChangedListener {
            if (this.editText.text.isEmpty())
                getAdapter().setData(this.hashMap)
            else {
                val newHashMap = HashMap<String, Any>()
                for (key in this.hashMap.keys) {
                    @SuppressLint("DefaultLocale")
                    if (key.toLowerCase().contains(editText.text.toString().toLowerCase()))
                        newHashMap[key] = this.hashMap[key]!!
                    getAdapter().setData(newHashMap)
                }
            }
        }
        addView(this.editText)
        addView(this.textView)
        addView(this.listView)
    }

    /**
     * @return adapter of the listView
     */
    private fun getAdapter(): Adapter {
        return this.adapter
    }

    /**
     * Create ListView dialog in current context with provided items which can be either stored in HashMap or stored under string array in resources
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at the top of dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param arrayReference in int reference to String array, which values will be displayed
     * @param editTextToUpdate is editText view. If you provided it, will automatically on item click set String as the text and its position in String array as tag. It will also dismiss the dialog
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        arrayReference: Int,
        editTextToUpdate: EditText?
    ) : this(context, title, message, icon, editTextToUpdate) {
        this.hashMap = HashMap()
        val array = getContext().resources.getStringArray(arrayReference)
        for (item in array) {
            this.hashMap[item] = array.indexOf(item)
        }
        setAdapter()
    }

    /**
     * Create ListView dialog in current context with provided items which can be either stored in HashMap or stored under string array in resources
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed at the top of dialog
     * @param message is int reference to String, which will be displayed under the title
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param arrayList are Strings, which values will be displayed
     * @param editTextToUpdate is editText view. If you provided it, will automatically on item click set String as the text and its position in arrayList as tag. It will also dismiss the dialog
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        arrayList: ArrayList<String>,
        editTextToUpdate: EditText?
    ) : this(context, title, message, icon, editTextToUpdate) {
        this.hashMap = HashMap()
        for (item in arrayList) {
            this.hashMap[item] = arrayList.indexOf(item)
        }
        setAdapter()
    }

    /**
     * Creates dialog with listView, which is populated by keys from hashMap. Key values can be obtained by calling getItemValue. If you provide an editText, will assign key value to it as tag.
     *
     * @param context of currently displayed activity
     * @param title is int reference to String, which will be displayed as dialog title
     * @param message is int reference to String, which will be displayed as dialog message
     * @param icon is int reference to drawable, which will be displayed next to title
     * @param hashMap are the values
     * @param editTextToUpdate is editText view. If you provided it, will automatically on item click set key as the text and key value as a tag. It will also dismiss the dialog
     */
    constructor(
        context: Context,
        title: Int,
        message: Int,
        icon: Int,
        hashMap: HashMap<String, Any>,
        editTextToUpdate: EditText?
    ) : this(context, title, message, icon, editTextToUpdate) {
        this.hashMap = HashMap(hashMap)
        setAdapter()
    }

    /**
     * Assign adapter to the listView
     */
    private fun setAdapter() {
        this.adapter = Adapter(getContext(), this.hashMap, this.textView, this.listView)
        this.listView.adapter = this.adapter
    }

    /**
     * @return item position in default array. If the item do not exists in array, it will return -1
     * @param position is int position in the listView
     */
    fun getItemValue(position: Int): Any {
        return this.hashMap[getItem(position)]!!
    }

    /**
     * @return item stored in provided position of listView
     * @param position is int position in the listView
     */
    fun getItem(position: Int): String {
        return this.adapter.getItem(position)
    }

    /**
     * @return listView in the dialog
     */
    fun getListView(): ListView {
        return this.listView
    }


    /**
     * Sets on item click
     */
    private fun setOnItemClick() {
        this.listView.setOnItemClickListener { _, _, position, _ ->
            this.editTextToUpdate!!.setText(getItem(position))
            this.editTextToUpdate.tag = getItemValue(position)
            dismiss()
        }
    }
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

        editText.hint = this.context.getString(R.string.hintSearch)
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

/**
 * Creates dialog with listView, which is populated by checkable textViews
 *
 * @param context of currently displayed activity
 * @param message is text, which will be displayed below title
 * @param icon is int reference to drawable, which will be displayed next to title
 * @param data are Strings, which will be displayed in checkable textViews
 * @param alreadySelectedItems are String, which were already selected on previous open of this dialog
 */
class CheckableTextViewsDialog(
    context: Context,
    message: String,
    icon: Int,
    data: ArrayList<String>,
    alreadySelectedItems: ArrayList<String>?
) : DialogTwoButtons(
    context,
    R.string.selectItems,
    message,
    icon,
    true
) {

    private val listView = ListView(getContext())
    private val adapter = AdapterCheckBox(getContext(), data, alreadySelectedItems)
    private val textView: TextView
    private val editText: EditText

    init {
        val dialogElements = DialogElements(getContext())
        this.editText = dialogElements.getSearchEditText()
        this.textView = dialogElements.getNoItemFoundTextView()
        this.editText.addTextChangedListener {
            this.adapter.setData(
                if (this.editText.text.isEmpty())
                    data
                else {
                    val filteredValues = ArrayList<String>()
                    for (index in 0 until data.size) {
                        @SuppressLint("DefaultLocale")
                        if (data[index].toLowerCase()
                                .contains(this.editText.text.toString().toLowerCase())
                        )
                            filteredValues.add(data[index])
                    }
                    filteredValues
                }
            )
        }
        addView(this.editText)
        addView(this.textView)
        this.listView.adapter = this.adapter
        addView(this.listView)
    }

    /**
     * @return Strings, which were already selected
     */
    fun getSelectedItems(): ArrayList<String> {
        return this.adapter.getSelectedItems()
    }
}

private class Adapter(
    private val context: Context,
    hashMap: HashMap<String, Any>,
    private val textView: TextView,
    private val listView: ListView
) : BaseAdapter() {
    private val layoutInflater = LayoutInflater.from(context)
    private val arrayList: ArrayList<String> = ArrayList()

    init {
        setData(hashMap)
    }

    fun setData(hashMap: HashMap<String, Any>) {
        setData(hashMap, false)
    }

    fun setData(hashMap: HashMap<String, Any>, isFiltered: Boolean) {
        this.arrayList.clear()
        for (key in hashMap.keys) {
            this.arrayList.add(key)
        }
        this.arrayList.sort()
        notifyDataSetChanged()
        when (count) {
            0 -> {
                this.textView.text = this.context.getString(
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
        viewHolder.dialogItem.text = getItem(position)
        return view!!
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     * data set.
     * @return The data at the specified position.
     */
    override fun getItem(position: Int): String {
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
        return this.arrayList.count()
    }

    private class ViewHolder(view: View) {
        val dialogItem = view.findViewById(R.id.item_dialog) as TextView
    }
}

private class AdapterCheckBox(
    private val context: Context,
    arrayList: ArrayList<String>,
    selectedItems: ArrayList<String>?
) :
    BaseAdapter() {
    private val layoutInflater = LayoutInflater.from(this.context)
    private var selectedItems: ArrayList<String> = when (selectedItems == null) {
        true -> ArrayList()
        else -> selectedItems
    }
    private lateinit var displayedValues: ArrayList<String>

    init {
        setData(arrayList)
    }

    /**
     * @param arrayList are data to be displayed
     * sets data to display
     */
    fun setData(arrayList: ArrayList<String>) {
        this.displayedValues = arrayList
        notifyDataSetChanged()
    }

    /**
     * @return selected items
     */
    fun getSelectedItems(): ArrayList<String> {
        return this.selectedItems
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
            view = this.layoutInflater.inflate(R.layout.item_dialog_checkbox, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        viewHolder.checkBox.text = getItem(position)
        viewHolder.checkBox.isChecked = this.selectedItems.contains(getItem(position))
        viewHolder.checkBox.setOnClickListener {
            if (viewHolder.checkBox.isChecked) {
                this.selectedItems.remove(getItem(position))
                viewHolder.checkBox.isChecked = false
            } else {
                this.selectedItems.add(getItem(position))
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
    override fun getItem(position: Int): String {
        return this.displayedValues[position]
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
        return this.displayedValues.size
    }

    private class ViewHolder(view: View) {
        val checkBox = view.findViewById(R.id.check_box) as CheckedTextView
    }
}

