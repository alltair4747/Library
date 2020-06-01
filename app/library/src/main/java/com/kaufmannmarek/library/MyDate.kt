package com.kaufmannmarek.library

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

@Suppress(
    "MemberVisibilityCanBePrivate",
    "unused",
    "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)
class MyDate {
    private val dateFormat = SimpleDateFormat("d.M.yyyy", Locale.GERMAN)
    private val defaultDate = getDateFormat().parse("30.12.1899")

    /**
     * @return number of elapsed days from 30.12.1899 to provided date
     * @param date is date in d.M.yyyy format, which number of days since 30.12.1899 we want to know
     */
    private fun getNumberOfElapsedDays(date: String): Int {
        return ((this.dateFormat.parse(date).time - defaultDate.time) / (86400000)).toInt()
    }

    /**
     * @return number of elapsed days from 30.12.1899 to today
     */
    fun getNumberOfElapsedDays(): Int {
        return getNumberOfElapsedDays(getToday())
    }

    /**
     * @return number of elapsed days between provided days
     * @param firstDate is greater date in d.M.yyyy format
     * @param secondDate is smaller date in d.M.yyyy format
     */
    fun getDifferenceBetweenDays(firstDate: String, secondDate: String): Int {
        return getNumberOfElapsedDays(firstDate) - getNumberOfElapsedDays(secondDate)
    }

    /**
     * @return number of elapsed days from 30.12.1899 until date
     * @param date is examined date
     */
    fun getDifferenceBetweenDays(date: String): Int {
        return getNumberOfElapsedDays(date)
    }

    /**
     * @return name of day for provided date (f.e. Monday)
     * @param elapsedDays is number of elapsed days since 30.12.1899
     */
    fun getNameOfDay(elapsedDays: Int, context: Context): String {
        val numberOfDay: Int
        val remainder = elapsedDays % 7
        numberOfDay = when (remainder) {
            0 -> 6
            7 -> 5
            6 -> 4
            5 -> 3
            4 -> 2
            3 -> 1
            else -> 0
        }
        return context.resources.getStringArray(R.array.daysOfWeek)[numberOfDay]
    }

    /**
     * @return name of day for provided date (f.e. Monday)
     * @param date is date in d.M.yyyy format, which name of day we want to know
     */
    fun getNameOfDay(date: String, context: Context): String {
        return getNameOfDay(getNumberOfElapsedDays(date), context)
    }


    /**
     * @return used dateFormat
     */
    fun getDateFormat(): SimpleDateFormat {
        return this.dateFormat
    }

    /**
     * @return today´s date in d.M.yyyy format
     */
    fun getToday(): String {
        val date = Date()
        return getDateFormat().format(date)
    }

    /**
     * @return date in format d.M.yyyy from number of elapsed days since 30.12.1899
     * @param elapsedDays of elapsed days since 30.12.1899
     */
    fun getDate(elapsedDays: Int): String {
        var year = 1900
        var numberOfDays = 1
        var isTransitionYear: Boolean
        if (elapsedDays < 3) {
            return if (elapsedDays < 2) {
                (30 + elapsedDays).toString() + ".12.1899"
            } else {
                "1.1.1900"
            }
        } else {
            do {
                when (year % 4) {
                    0 -> when (year % 100) {
                        0 -> when (year % 400) {
                            0 -> {
                                numberOfDays += 366
                                isTransitionYear = true
                            }
                            else -> {
                                numberOfDays += 365
                                isTransitionYear = false
                            }
                        }
                        else -> {
                            numberOfDays += 366
                            isTransitionYear = true
                        }
                    }
                    else -> {
                        numberOfDays += 365
                        isTransitionYear = false
                    }
                }
                if (numberOfDays < elapsedDays) {
                    year++
                }
            } while (elapsedDays > numberOfDays)
        }
        return if (isTransitionYear) {
            getDayAndMonth(true, 366 - (numberOfDays - elapsedDays)) + year
        } else {
            getDayAndMonth(false, 365 - (numberOfDays - elapsedDays)) + year
        }
    }

    /**
     * @param isTransitionYear if true, year has 366 days
     * @param daysNumber number of elapsed days in year
     * @return date and month in d.M from number of days since 1.1. of year
     */
    private fun getDayAndMonth(isTransitionYear: Boolean, daysNumber: Int): String {
        val part: String
        var february = 28
        if (isTransitionYear) {
            february = 29
        }
        part = when {
            daysNumber >= 307 + february -> (daysNumber - february - 306).toString() + ".12."
            daysNumber >= february + 277 -> (daysNumber - february - 276).toString() + ".11."
            daysNumber >= february + 246 -> (daysNumber - february - 245).toString() + ".10."
            daysNumber >= february + 216 -> (daysNumber - february - 215).toString() + ".9."
            daysNumber >= february + 185 -> (daysNumber - february - 184).toString() + ".8."
            daysNumber >= february + 154 -> (daysNumber - february - 153).toString() + ".7."
            daysNumber >= february + 124 -> (daysNumber - february - 123).toString() + ".6."
            daysNumber >= february + 93 -> (daysNumber - february - 92).toString() + ".5."
            daysNumber >= february + 63 -> (daysNumber - february - 62).toString() + ".4."
            daysNumber >= february + 32 -> (daysNumber - february - 31).toString() + ".3."
            daysNumber >= 32 -> (daysNumber - 31).toString() + ".2."
            else -> "$daysNumber.1."
        }
        return part
    }
}