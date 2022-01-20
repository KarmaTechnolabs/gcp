package com.app.gcp.utils

object DateTimeConstants {
    const val MONTH_YEAR_FORMAT = "MMM yyyy"
    const val MONTH_DAY_YEAR_FORMAT = "MMM dd, yyyy"
    const val YEAR_MONTH_DAY_FORMAT = "yyyy-MM-dd"
    const val HOUR_MINUTE_AM_FORMAT = "hh:mm a"
    const val HOUR_MINUTE_AM_FORMAT2 = "h:mma"
    const val MONTH_DAY_YEAR_HOUR_MINUTE_AM_FORMAT = "MMM dd, yyyy hh:mm a"
    const val YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val YEAR_MONTH_DAY_HOUR_MINUTE_FORMAT = "yyyy-MM-dd HH:mm"
    const val MONTH_DAY_FORMAT = "MMM d"
    const val MONTH_FORMAT = "MMM"
    const val DAY_FORMAT = "d"
    const val MONTH_DAY_COMMA_HOUR_MINUTE_AM_FORMAT = "MMM dd, hh:mm a"
    const val MONTH_DAY_HOUR_MINUTE_AM_FORMAT = "MMM dd hh:mm a"
    const val DAY_MONTH_DATE_HOUR_MINUTE_SECOND_ZONE_YEAR_FORMAT = "E MMM dd HH:mm:ss z yyyy"
    const val DAY_MONTH_YEAR_HOUR_MINUTE_AM_FORMAT = "dd/MM/yyyy hh:mm a"
    const val DATE_TIME = "Date Time"
    val HOURS_ARRAY_DAY =
        arrayOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
    val MINUTE_ARRAY_HOURS = arrayOf("00", "15", "30", "45")
    val AM_PM_ARRAY = arrayOf("AM", "PM")
    const val HOUR_MINUTE_FULL_FORMAT = "HH:mm:ss"
    const val DAY_MONTH_DATE_FORMAT = "EEE,MMM dd"
    const val HOUR_MINUTEAM_FORMAT = "hh:mma"
    const val MONTH_YEAR_SHORT = "MMM yy"
    const val DAY_DATE_MONTH_YEAR = "EEE, dd MMM yyyy"
    const val DAY_DATE_HOUR_MINUTE_ET_FORMAT = "EEE d hh:mma 'ET'"
    const val DAY_DATE_MONTH_HOUR_MINUTE_EST_FORMAT = "EEE d/MM hh:mma 'EST'"
    const val DATE_MONTH_YEAR_HOUR_MINUTE_EST_FORMAT = "dd/MM/yyyy hh:mma 'EST'"
    const val DAY_DATE_HOUR_MINUTE_FORMAT = "EEE d hh:mma"
    const val HOUR_MINUTE_AM_MONTH_DAY_YEAR_FORMAT = "hh:mm a MMM dd, yyyy"
    const val MONTH_APOSTROPHY_YEAR = "MMM''yy"
    const val DAY_MONTH_YEAR = "dd-MM-yyyy"
    const val MONTH = "MM"
    const val YEAR = "yyyy"
    const val DAY_DATE_HOUR_MINUTE_AM_TIME = "EEE, HH:mm aa"
    const val HOUR_MINUTE_AM_TIME = "hh:mm aa"
    const val MONTH_DD_YYYY = "MMMM dd, yyyy"

    const val secondsInMilli: Long = 1000
    const val minutesInMilli = secondsInMilli * 60
    const val hoursInMilli = minutesInMilli * 60
    const val daysInMilli = hoursInMilli * 24
    const val weeksInMilli = daysInMilli * 7
}