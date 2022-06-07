package com.gcptrack.utils

import android.annotation.SuppressLint
import android.text.TextUtils
import android.text.format.DateUtils
import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@SuppressLint("SimpleDateFormat")
object DateTimeUtils {
    const val DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss"
    const val DATE_FORMAT_FULL_TIME_AM_PM = "dd MMM yyyy, HH:mm aa"
    const val FORMAT_DATE = "dd/MM/yyyy"
    const val FORMAT_DATE_DD_MMM_YYYY = "dd MMM yyyy"
    const val FORMAT_DATE_YYYY_MM_DD = "yyyy-MM-dd"
    const val FORMAT_DATE_MMMM_yyyy = "MMMM-yyyy"
    const val FORMAT_TIME_AM_PM = "HH:mm a"
    const val FORMAT_TIME_FULL = "HH:mm:ss"
    const val DAY_DATE_HOUR_MINUTE_ET_FORMAT = "EEE d hh:mma 'ET'"
    const val DAY_DATE_MONTH_HOUR_MINUTE_FORMAT = "EEE d MMM,hh:mma"
    const val DAY_DATE_AM_PM_FORMAT = "EEEE, d a"
    const val DAY_DATE_MONTH_HOUR_MINUTE_AM_PM_FORMAT = "dd MMM,hh:mma"
    const val DAY_SHORT_DATE_AM_PM_FORMAT = "EE, d a"
    const val DAY_MONTH_DATE_FORMAT = "EE, MMMM dd"
    const val MONTH_DATE_FORMAT = "MM/dd"
    const val DAY_DATE_FORMAT = "EEEE, d"
    const val DAY_DATE_HOUR_MINUTE_AM_PM_FORMAT = "EEEE, HH:mm a"
    const val DAY_DATE_HOUR_MINUTE_SECOND_AM_PM_FORMAT = "EEEE, HH:mm:ss a"
    const val YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val YEAR_MONTH_DAY_HOUR_MINUTE_AM_PM_FORMAT = "yyyy-MM-dd HH:mm a"
    const val YEAR_MONTH_DAY_T_HOUR_MINUTE_SECOND_Z_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val YEAR_MONTH_DAY_T_HOUR_MINUTE_SECOND_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    const val secondsInMilli: Long = 1000
    const val minutesInMilli = secondsInMilli * 60
    const val hoursInMilli = minutesInMilli * 60
    const val daysInMilli = hoursInMilli * 24
    const val monthInMilli = daysInMilli * 30
    const val weeksInMilli = daysInMilli * 7

    @JvmStatic
    public fun formatDateForString(
        inputFormat: String?,
        outputFormat: String?,
        inputDate: String?
    ): String? {
        val parsed: Date
        var outputDate = ""
        if (TextUtils.isEmpty(inputDate))
            return inputDate
        val dfInput = SimpleDateFormat(inputFormat, Locale.US)
        val dfOutput = SimpleDateFormat(outputFormat, Locale.US)
        try {
            parsed = dfInput.parse(inputDate)
            outputDate = dfOutput.format(parsed)

        } catch (e: ParseException) {
            return inputDate
            Log.e("DateTime", "ParseException - dateFormat")
        }
        return outputDate
    }

    @JvmStatic
    public fun getTodayOrTomorrow( inputFormat: String?, outputFormat: String?,inputDate: String?): String? {
        if (TextUtils.isEmpty(inputDate))
            return inputDate
        val dateFormat = SimpleDateFormat(inputFormat, Locale.US)
        var date: Date? = null
        try {
            date = dateFormat.parse(inputDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return if (date != null) {
            if (DateUtils.isToday(date.time)) {
                "Today ".plus(formatDateForString(inputFormat,FORMAT_TIME_AM_PM,inputDate))
            } else if (DateUtils.isToday(date.getTime() - DateUtils.DAY_IN_MILLIS)) {
                return "Tomorrow ".plus(formatDateForString(inputFormat,FORMAT_TIME_AM_PM,inputDate))
            } else {
                return formatDateForString(inputFormat,outputFormat,inputDate)
            }
        } else ""
    }

    fun formatDateForDate(inputFormat: String?, inputDate: String?): Date? {
        var parsed: Date? = null
        val dfInput = SimpleDateFormat(inputFormat, Locale.US)
        dfInput.timeZone = TimeZone.getTimeZone("UTC")
        try {
            parsed = dfInput.parse(inputDate)
        } catch (e: ParseException) {
            Log.e("DateTime", "ParseException - dateFormat")
        }
        return parsed
    }

    fun relativeToCurrentTime(dateTime: Long): String {

        val simpleDateFormat = SimpleDateFormat("MMM dd")
        val simpleTimeFormat = SimpleDateFormat("HH:mm a")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateTime

        if (dateTime == 0L) {
            return ""
        }

        var diff = System.currentTimeMillis() - dateTime

        if (diff < 0)
            diff = diff.unaryMinus()

        val hours = TimeUnit.MILLISECONDS.toHours(diff)

        if (hours > 48) {
            return simpleDateFormat.format(calendar.time)
        } else if (hours > 24) {
            return "Yesterday"
        } else {
            return simpleTimeFormat.format(calendar.time)
        }
    }

    fun getDateInMillieSecondsUTC(dateTime: String, inputFormat: String): Long {
        val simpleDateFormat = SimpleDateFormat(inputFormat)
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.time = simpleDateFormat.parse(dateTime) ?: Date()
        val default = TimeZone.getDefault()
        val offset = default.getOffset(calendar.timeInMillis)
        return calendar.timeInMillis + offset
    }

    fun getDateFromMillieSecondsUTC(milliSecondSinceEpoch: Long, inputFormat: String): String {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = milliSecondSinceEpoch
        val simpleDateFormat = SimpleDateFormat(inputFormat)
        return simpleDateFormat.format(calendar.time)
    }

    fun getDateStringAccordingToFormat(date: Date, outputFormat: String): String {
        val simpleDateFormat = SimpleDateFormat(outputFormat)
        return simpleDateFormat.format(date) ?: ""
    }

    fun getDateStringAccordingToFormat(
        date: String,
        inputFormat: String = DATE_FORMAT_FULL,
        outputFormat: String = FORMAT_DATE
    ): String {

        if (date.isBlank() or date.isEmpty())
            return ""

        val simpleDateFormat = SimpleDateFormat(outputFormat)
        val inputDate = Date(getDateInMillieSecondsUTC(date, inputFormat))
        return simpleDateFormat.format(inputDate) ?: ""
    }

}