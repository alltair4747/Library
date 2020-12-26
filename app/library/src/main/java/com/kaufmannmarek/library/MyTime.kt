@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.kaufmannmarek.library

import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class MyTime {
    private var text: String = ""
    private var seconds: Int = 0
    private var elapsedSeconds: Int = 0

    /**
     * Create new time from time in format HH:mm:ss (f.e. 12:31:48) or HH:mm (f.e 12:31)
     * @param time is time in format HH:mm:ss (f.e. 12:31:48) or HH:mm (f.e 12:31)
     */
    constructor(time: String) {
        if (time.length == 3) {
            setText(time.substring(0, 5))
            this.seconds = time.substring(6).toInt()
        } else
            setText(time)
        setElapsedSeconds(getElapsedSecondsFromText())
    }

    /**
     * Create new time from number of elapsed seconds since the midnight
     * @param elapsedSeconds number of elapsed seconds since midnight
     */
    constructor(elapsedSeconds: Int) {
        setElapsedSeconds(elapsedSeconds)
        setText(getTextFromElapsedSeconds())
    }

    /**
     * Create new time, which is equal to current system time
     */
    constructor() {
        setElapsedSeconds(getCurrentlyElapsedDaySeconds())
        setText(getTextFromElapsedSeconds())
    }

    /**
     * @return number of elapsed seconds from midnight until now
     */
    private fun getCurrentlyElapsedDaySeconds(): Int {
        val date = Date()
        return Integer.valueOf(
            SimpleDateFormat(
                "HH",
                Locale.GERMAN
            ).format(date)
        ) * 3600 + Integer.valueOf(
            SimpleDateFormat(
                "mm",
                Locale.GERMAN
            ).format(date)
        ) * 60 + Integer.valueOf(SimpleDateFormat("ss", Locale.GERMAN).format(date))
    }

    /**
     * @return if this time is later than another one
     * @param anotherTime is time used as comparator
     */
    fun isGreaterThan(anotherTime: MyTime): Boolean {
        return isGreaterThan(getElapsedSeconds(), anotherTime.getElapsedSeconds())
    }

    /**
     * @return if this time is later or same as another one
     * @param anotherTime is time used as comparator
     */
    fun isGreaterOrEqual(anotherTime: MyTime): Boolean {
        return isGreaterOrEqual(getElapsedSeconds(), anotherTime.getElapsedSeconds())
    }

    /**
     * @return if number of elapsed seconds of one time is greater than another one´s
     * @param elapsedSeconds is number of elapsed seconds of time, which should be higher
     * @param comparedElapsedSeconds is number of elapsed seconds of time, which should be smaller
     */
    private fun isGreaterThan(elapsedSeconds: Int, comparedElapsedSeconds: Int): Boolean {
        return elapsedSeconds > comparedElapsedSeconds
    }

    /**
     * @return if number of elapsed seconds of one time is greater or equal than another one´s
     * @param elapsedSeconds is number of elapsed seconds of time, which should be higher or equal
     * @param comparedElapsedSeconds is number of elapsed seconds of time, which should be smaller or equal
     */
    private fun isGreaterOrEqual(elapsedSeconds: Int, comparedElapsedSeconds: Int): Boolean {
        return elapsedSeconds >= comparedElapsedSeconds
    }

    /**
     * @return if this time is later than another one
     * @param anotherTime is time used as comparator
     */
    fun isLessThan(anotherTime: MyTime): Boolean {
        return !isLessThan(anotherTime)
    }

    /**
     * @return if this time is earlier or same as another one
     * @param anotherTime is time used as comparator
     */
    fun isLessOrEqual(anotherTime: MyTime): Boolean {
        return !isLessOrEqual(anotherTime)
    }

    /**
     * @return time as text in format HH:mm
     */
    fun text(): String {
        return text(false)
    }

    /**
     * @return time as text in format either in HH:mm or HH:mm:ss
     * @param showSeconds if true, return time in HH:mm:ss format, else in HH:mm format
     */
    fun text(showSeconds: Boolean): String {
        return if (showSeconds)
            StringBuilder().append(this.text).append(":")
                .append(
                    when (this.seconds < 10) {
                        true -> "0" + this.seconds
                        false -> this.seconds
                    }
                ).toString()
        else
            this.text
    }

    /**
     * Sets text format of the time
     */
    private fun setText(time: String) {
        this.text = time
    }

    /**
     * @return number of elapsed seconds in day of this time
     */
    fun getElapsedSeconds(): Int {
        return this.elapsedSeconds
    }

    /**
     * @return set number of elapsed seconds in day of this time
     * @param elapsedSeconds is number of elapsed seconds
     */
    private fun setElapsedSeconds(elapsedSeconds: Int) {
        this.elapsedSeconds = elapsedSeconds
    }

    /**
     * Retrieve text format of this time from number of elapsed seconds in day
     */
    private fun getElapsedSecondsFromText(): Int {
        val timeParts = text.split(":")
        return if (timeParts.size == 3)
            timeParts[0].toInt() * 60 * 60 + timeParts[1].toInt() * 60 + timeParts[2].toInt()
        else
            timeParts[0].toInt() * 60 * 60 + timeParts[1].toInt() * 60
    }

    /**
     * Retrieve text of this time from number of elapsed seconds
     */
    private fun getTextFromElapsedSeconds(): String {
        val hours: Int = getElapsedSeconds() / 3600
        val minutes: Int = (getElapsedSeconds() - hours * 3600) / 60
        val seconds: Int = getElapsedSeconds() - hours * 3600 - minutes * 60
        this.seconds = seconds
        return StringBuilder().append(
            when (hours < 10) {
                true -> "0$hours"
                false -> hours
            }
        ).append(":").append(
            when (minutes < 10) {
                true -> "0$minutes"
                false -> minutes
            }
        ).toString()
    }
}