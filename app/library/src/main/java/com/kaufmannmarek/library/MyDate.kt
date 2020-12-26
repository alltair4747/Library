package com.kaufmannmarek.library

import android.content.Context
import java.lang.StringBuilder
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Suppress(
    "MemberVisibilityCanBePrivate",
    "unused"
)
class MyDate {
    private var numberOfElapsedDays = 0
    private var text = ""

    companion object {
        private const val DAY_MILLISECONDS = 86400000L
        private val DATE_FORMAT = SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN)

        /**
         * This constant is used to solve difference between timeFormat of google sheets and android
         */
        private const val DAYS_DIFFERENCE = 25569
    }

    /**
     * Creates today´s date according to time of the system
     */
    constructor() {
        setNumberOfElapsedDays(getNumberOfElapsedDaysUntilToday())
        setText(getTextFromNumberOfElapsedDays())
    }

    /**
     * Creates date from number of elapsed days since 30.12.1899
     * @param numberOfElapsedDays is number of elapsed days since 30.12.1899
     */
    constructor(numberOfElapsedDays: Int) {
        setNumberOfElapsedDays(numberOfElapsedDays)
        setText(getTextFromNumberOfElapsedDays())
    }

    /**
     * Creates date from provided date String, which must be in specified format
     * @param date is date in format dd.MM.yyyy - f.e. 31.12.2020
     */
    constructor(date: String) {
        setText(date)
        setNumberOfElapsedDays(toNumberOfElapsedDays(date))
    }

    /**
     * Sets text version of the date
     */
    private fun setText(date: String) {
        this.text = date
    }

    /**
     * @return date in format dd.MM.yyyy - f.e. 31.12.2020
     */
    fun text(): String {
        return this.text
    }

    /**
     * @return number of elapsed days since 30.12.1899 until this day
     */
    fun getNumberOfElapsedDays(): Int {
        return this.numberOfElapsedDays
    }

    /**
     * Assign numberOfElapsedDays variable with value. It represents number of elapsed days since 30.12.1899
     * @param numberOfElapsedDays variable that will be assigned
     */
    private fun setNumberOfElapsedDays(numberOfElapsedDays: Int) {
        this.numberOfElapsedDays = numberOfElapsedDays
    }

    /**
     * @return used dateFormat
     */
    private fun getDateFormat(): SimpleDateFormat {
        return DATE_FORMAT
    }

    /**
     * @return number of milliseconds in one day
     */
    private fun getDayMilliSeconds(): Long {
        return DAY_MILLISECONDS
    }

    /**
     * @return name of the day, which is associated with the date (f.e. Monday)
     * @param context is context of currently displayed activity
     */
    fun getDayName(context: Context): String {
        return context.resources.getStringArray(R.array.daysOfWeek)[getDayIndex()]
    }

    /**
     * return index of the day, which is associated with this date, where 0 is monday and 6 is sunday
     */
    fun getDayIndex(): Int {
        return when {
            getNumberOfElapsedDays() < 0 -> when (getNumberOfElapsedDays() % 7) {
                -1 -> 4
                -2 -> 3
                -3 -> 2
                -4 -> 1
                -5 -> 0
                -6 -> 6
                else -> 5
            }
            else -> when (getNumberOfElapsedDays() % 7) {
                1 -> 6
                2 -> 0
                3 -> 1
                4 -> 2
                5 -> 3
                6 -> 4
                else -> 5
            }
        }
    }

    /**
     * @return number of elapsed days as String
     */
    fun numberOfElapsedDaysToString(): String {
        return getNumberOfElapsedDays().toString()
    }

    /**
     * @return number of days since 30.12.1899
     */
    private fun getNumberOfElapsedDaysUntilToday(): Int {
        val today = Date().time
        return ((today / getDayMilliSeconds()) + 25569).toInt()
    }

    /**
     * @param date is date in format dd.MM.yyyy f.e. 31.12.2020
     * @return number of elapsed days since 30.12.1899. If the provided date is in incorrect format, -1 will be returned
     */
    private fun toNumberOfElapsedDays(date: String): Int {
        return try {
            var day = getDateFormat().parse(date)!!.time
            day /= getDayMilliSeconds()
            (1 + day + 25569).toInt()
        } catch (e: ParseException) {
            return -1
        }
    }

    /**
     * @return year from this date
     */
    private fun getYearFromDate(): Int {
        return text().split(".")[2].toInt()
    }

    /**
     * @return index of month from this date, where 1 is january and 12 is december
     */
    fun getMonth(): Int {
        return text().split(".")[1].toInt()
    }

    /**
     * @return number of elapsed days since the beginning of the month´s date
     */
    fun getDays(): Int {
        return text().split(".")[0].toInt()
    }

    /**
     * @return this date is later than today´s date
     */
    fun isGreaterThanToday(): Boolean {
        return isGreaterThan(getNumberOfElapsedDays(), getNumberOfElapsedDaysUntilToday())
    }

    /**
     * @return this date is later or same as today´s date
     */
    fun isGreaterOrEqualToday(): Boolean {
        return isGreaterOrEqual(getNumberOfElapsedDays(), getNumberOfElapsedDaysUntilToday())
    }

    /**
     * @return this date is later than another´s date
     * @param anotherDate is date used as comparator
     */
    fun isGreaterThan(anotherDate: MyDate): Boolean {
        return isGreaterThan(getNumberOfElapsedDays(), anotherDate.getNumberOfElapsedDays())
    }

    /**
     * @return this date is later or same as another´s date
     * @param anotherDate is date used as comparator
     */
    fun isGreaterOrEqual(anotherDate: MyDate): Boolean {
        return isGreaterOrEqual(getNumberOfElapsedDays(), anotherDate.getNumberOfElapsedDays())
    }

    /**
     * @return if number of elapsed days of one date is higher than another one
     * @param numberOfElapsedDays is number of elapsed days of the date, which should higher
     * @param comparedNumberOfElapsedDays is number of elapsed days of the date, which should be smaller
     */
    private fun isGreaterThan(numberOfElapsedDays: Int, comparedNumberOfElapsedDays: Int): Boolean {
        return numberOfElapsedDays > comparedNumberOfElapsedDays
    }

    /**
     * @return if number of elapsed days of one date is higher or equal than another one
     * @param numberOfElapsedDays is number of elapsed days of the date, which should higher
     * @param comparedNumberOfElapsedDays is number of elapsed days of the date, which should be smaller
     */
    private fun isGreaterOrEqual(
        numberOfElapsedDays: Int,
        comparedNumberOfElapsedDays: Int
    ): Boolean {
        return numberOfElapsedDays >= comparedNumberOfElapsedDays
    }

    /**
     * @return if this date is same as today´s
     */
    fun isEqualToToday(): Boolean {
        return getNumberOfElapsedDays() == getNumberOfElapsedDaysUntilToday()
    }

    /**
     * @return if this date is earlier than today
     */
    fun isLessThanToday(): Boolean {
        return !isGreaterThanToday()
    }

    /**
     * @return if this date is earlier or same as today´s
     */
    fun isLessOrEqualToday(): Boolean {
        return !isGreaterOrEqualToday()
    }

    /**
     * @return if this date is earlier than another´s date
     * @param anotherDate is date used as comparator
     */
    fun isLessThan(anotherDate: MyDate): Boolean {
        return !isGreaterThan(anotherDate)
    }

    /**
     * @return if this date is earlier or same as another´s date
     * @param anotherDate is date used as comparator
     */
    fun isLessOrEqual(anotherDate: MyDate): Boolean {
        return !isGreaterOrEqual(anotherDate)
    }

    /**
     * Converts number of elapsed days into date in format dd.MM.yyyy f.e. 30.12.2020 from number of elapsed days since 30.12.1899
     */
    private fun getTextFromNumberOfElapsedDays(): String {
        return if (getNumberOfElapsedDays() < 2)
            StringBuilder().append("3").append(numberOfElapsedDays).append(".12.1899").toString()
        else {
            var year = 1900
            var remainingDays = getNumberOfElapsedDays() - 2
            var nextYearNumberOfDays = 365
            while (remainingDays - nextYearNumberOfDays > 0) {
                year++
                remainingDays -= nextYearNumberOfDays
                nextYearNumberOfDays = if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))
                    366
                else
                    365
            }
            if (nextYearNumberOfDays == 366) {
                when {
                    remainingDays < 32 -> StringBuilder().append(remainingDays).append(".1.")
                        .append(year).toString()
                    remainingDays < 61 -> StringBuilder().append(remainingDays - 31).append(".2.")
                        .append(year).toString()
                    remainingDays < 92 -> StringBuilder().append(remainingDays - 60).append(".3.")
                        .append(year).toString()
                    remainingDays < 122 -> StringBuilder().append(remainingDays - 91).append(".4.")
                        .append(year).toString()
                    remainingDays < 153 -> StringBuilder().append(remainingDays - 121).append(".5.")
                        .append(year)
                        .toString()
                    remainingDays < 183 -> StringBuilder().append(remainingDays - 152).append(".6.")
                        .append(year)
                        .toString()
                    remainingDays < 214 -> StringBuilder().append(remainingDays - 182).append(".7.")
                        .append(year)
                        .toString()
                    remainingDays < 245 -> StringBuilder().append(remainingDays - 213).append(".8.")
                        .append(year)
                        .toString()
                    remainingDays < 275 -> StringBuilder().append(remainingDays - 244).append(".9.")
                        .append(year)
                        .toString()
                    remainingDays < 306 -> StringBuilder().append(remainingDays - 274)
                        .append(".10.").append(year)
                        .toString()
                    remainingDays < 336 -> StringBuilder().append(remainingDays - 305)
                        .append(".11.").append(year)
                        .toString()
                    else -> StringBuilder().append(remainingDays - 335).append(".12.").append(year)
                        .toString()
                }
            } else {
                when {
                    remainingDays < 32 -> StringBuilder().append(remainingDays).append(".1.")
                        .append(year).toString()
                    remainingDays < 60 -> StringBuilder().append(remainingDays - 31).append(".2.")
                        .append(year).toString()
                    remainingDays < 91 -> StringBuilder().append(remainingDays - 59).append(".3.")
                        .append(year).toString()
                    remainingDays < 121 -> StringBuilder().append(remainingDays - 90).append(".4.")
                        .append(year).toString()
                    remainingDays < 152 -> StringBuilder().append(remainingDays - 120).append(".5.")
                        .append(year)
                        .toString()
                    remainingDays < 182 -> StringBuilder().append(remainingDays - 151).append(".6.")
                        .append(year)
                        .toString()
                    remainingDays < 213 -> StringBuilder().append(remainingDays - 181).append(".7.")
                        .append(year)
                        .toString()
                    remainingDays < 244 -> StringBuilder().append(remainingDays - 212).append(".8.")
                        .append(year)
                        .toString()
                    remainingDays < 274 -> StringBuilder().append(remainingDays - 243).append(".9.")
                        .append(year)
                        .toString()
                    remainingDays < 305 -> StringBuilder().append(remainingDays - 273)
                        .append(".10.").append(year)
                        .toString()
                    remainingDays < 335 -> StringBuilder().append(remainingDays - 304)
                        .append(".11.").append(year)
                        .toString()
                    else -> StringBuilder().append(remainingDays - 334).append(".12.").append(year)
                        .toString()
                }
            }
        }
    }
}