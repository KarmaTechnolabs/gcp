package com.app.gcp.utils;


import android.text.TextUtils;
import android.util.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;

public class DateTimeUtils {

    public static final SimpleDateFormat[] ACCEPTED_TIMESTAMP_FORMATS = {
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US),
            new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US),
            new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.US),
            new SimpleDateFormat("dd,MMM,yyyy HH:mm:ss aa", Locale.US),
            new SimpleDateFormat("dd MMM,yyyy HH:mm:ss aa", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US),
            new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US),
            new SimpleDateFormat("dd-MMM-yyyy", Locale.US),
            new SimpleDateFormat("dd MMM, yyyy", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd", Locale.US),
    };

    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final SimpleDateFormat PATTERN_YYYY_MM_DD_HH_MM = new SimpleDateFormat("dd-MMM-yyyy | hh:mm:ss a", Locale.US);
    public static final SimpleDateFormat PATTERN_DD_MM_YYYY = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    public static final SimpleDateFormat ddMMMYY = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    public static final SimpleDateFormat mmYYMMHHMMSSFormat = new SimpleDateFormat("'yyyy-MM-dd HH:mm:ss'", Locale.US);
    public static final SimpleDateFormat mmYYMonthformat = new SimpleDateFormat("MM-yyyy", Locale.US);
    public static final SimpleDateFormat mmmYYMonthformat = new SimpleDateFormat("MMM-yyyy", Locale.US);
    public static final SimpleDateFormat ddmmyyyyFormat = new SimpleDateFormat("dd MMM,yyyy hh:mm aa", Locale.US);
    public static final SimpleDateFormat ddmmmmyyyyFormat = new SimpleDateFormat("dd MMMM, yyyy", Locale.US);
    public static final SimpleDateFormat hhmmaFormat = new SimpleDateFormat("hh:mm a", Locale.US);
    public static final int SECOND_MILLIS = 1000;
    public static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    public static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    public static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    private static final long SEC = 1000;
    private static final long MIN = SEC * 60;
    private static final long HOUR = MIN * 60;
    private static final long DAY = HOUR * 24;
    public static final long YESTERDAY = DAY * 2;
    private static final long YEAR = DAY * 365;
    private static final long MONTH;
    private static final int[] DAY_IN_MONTH;

    static {
        int dayInFebruary = 28;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if (year % 4 == 0 && year % 100 == 0 && year % 400 == 0) {
            dayInFebruary = 29;
        }
        DAY_IN_MONTH = new int[]{31, dayInFebruary, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        MONTH = DAY * (DAY_IN_MONTH[Calendar.getInstance().get(Calendar.MONTH)]);
    }

    private DateTimeUtils() {
    }

    public static long getTimeFromStringDate(String format, String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        try {
            return sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getFormattedDate(SimpleDateFormat format, long time) {
        return format.format(time);
    }

    public static String getFormattedDate(String format, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(date);
    }

    public static Date parseTimestamp(String timestamp) {
        for (SimpleDateFormat format : ACCEPTED_TIMESTAMP_FORMATS) {
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                return format.parse(timestamp);
            } catch (ParseException ex) {
                continue;
            }
        }

        // All attempts to parse have failed
        return null;
    }


    /**
     * Returns the GMT offset in seconds as a String.
     *
     * @return GMT offset in seconds
     */
    public static String getGMTOffsetInSeconds() {
        return String.valueOf(getGMTOffsetInSecondsLong());
    }

    public static long getGMTOffsetInSecondsLong() {
        final Calendar calendar = new GregorianCalendar();
        final TimeZone timeZone = calendar.getTimeZone();
        return TimeUnit.SECONDS.convert(timeZone.getRawOffset() + (timeZone.inDaylightTime(new Date()) ? timeZone.getDSTSavings() : 0), TimeUnit.MILLISECONDS);
    }

    /**
     * @param format date format
     * @param time   date time in long
     * @return formatted date time
     */
    public static String format(String format, long time) {
        return new SimpleDateFormat(format, Locale.US).format(new Date(time));
    }

    public static String getDaysFromToday(String dob, SimpleDateFormat ddmmyyyyFormat) {

        if (!TextUtils.isEmpty(dob)) {

            try {
                Date userDob = ddmmyyyyFormat.parse(dob);
                Date today = new Date();
                long diff = today.getTime() - userDob.getTime();
                int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));

                if (numOfDays <= 1)
                    return numOfDays + " day";
                else return numOfDays + " days";
            /*int numOfYear = (int) ((diff / (1000 * 60 * 60 * 24))/365);
            int hours = (int) (diff / (1000 * 60 * 60));
            int minutes = (int) (diff / (1000 * 60));
            int seconds = (int) (diff / (1000));*/

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";

    }



    /**
     * show formatted time from now
     *
     * @param dateTime date time in long
     * @return return formatted time
     */
    /*public static String fromNow(Context context, long dateTime) {
        String formatted = "";

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        Date currentLocalTime = cal.getTime();
        long current = currentLocalTime.getTime();
        long diff = current - dateTime;

        int sec = (int) (diff / SEC);
        int min = (int) (diff / MIN);
        int hour = (int) (diff / HOUR);
        int day = (int) (diff / DAY);
        int month = (int) (diff / MONTH);
        int year = (int) (diff / YEAR);

        //Log.e("TAG", "Month : " + month);

        long secLimit = SEC * 10;

        if (diff <= secLimit) {
            formatted = context.getString(R.string.str_just_now_camel_case);
        } else if (diff > secLimit && diff < MIN) {
            formatted = sec + " " + context.getString(R.string.str_seconds_ago);
        } else if (diff > MIN && diff < HOUR) {
            formatted = min + " " + (min > 1 ? context.getString(R.string.str_minutes_ago) : context.getString(R.string.str_minute_ago));
        } else if (diff > HOUR) {
            formatted = hour + " " + (hour > 1 ? context.getString(R.string.str_hours_ago) : context.getString(R.string.str_hour_ago));

            if(hour > 48){
                formatted = DateTimeUtils.parseDateVideo(dateTime);
            }
        }
        return formatted;
    }*/


    /**
     * show formatted time from now
     *
     * @param dateTime date time in long
     * @return return formatted time
     */
    /*public static String chatAgo(Context context, long dateTime) {
        String formatted = "";

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        Date currentLocalTime = cal.getTime();
        long current = currentLocalTime.getTime();
        long diff = current - dateTime;

        int sec = (int) (diff / SEC);
        int min = (int) (diff / MIN);
        int hour = (int) (diff / HOUR);
        int day = (int) (diff / DAY);
        int month = (int) (diff / MONTH);
        int year = (int) (diff / YEAR);

        //Log.e("TAG", "Month : " + month);

        long secLimit = SEC * 10;

        if (diff <= secLimit) {
            formatted = context.getString(R.string.str_just_now_camel_case);
        } else if (diff > secLimit && diff < MIN) {
            formatted = sec + " " + context.getString(R.string.str_seconds_ago);
        } else if (diff > MIN && diff < HOUR) {
            formatted = min + " " + (min > 1 ? context.getString(R.string.str_minutes_ago) : context.getString(R.string.str_minute_ago));
        } else if (diff > HOUR) {
            formatted = hour + " " + (hour > 1 ? context.getString(R.string.str_hours_ago) : context.getString(R.string.str_hour_ago));
        }
        return formatted;
    }*/

    /**
     * show formatted time from now
     *
     * @param dateTime date time in long
     * @return return formatted time
     */
   /* public static String fromAgo(Context context, long dateTime) {

        String formatted = "";

        // TODO: localize
        if (dateTime == 0) {
            return formatted;
        }

        long diff = System.currentTimeMillis() - dateTime;
        if (diff < 0) {
            formatted = context.getString(R.string.str_just_now_camel_case);
        }

        if (diff < MINUTE_MILLIS) {
            diff = diff / SECOND_MILLIS;
            if (diff == 0) {
                diff = 1;
            }
            formatted =  diff + " " + context.getString(R.string.str_seconds_ago_camel_case);
        } else if (diff < 2 * MINUTE_MILLIS) {
            diff = diff / MINUTE_MILLIS;
            if (diff == 0) {
                diff = 1;
            }
            formatted =  diff + " " + context.getString(R.string.str_minute_ago_camel_case);
        } else if (diff < 50 * MINUTE_MILLIS) {
            diff = diff / MINUTE_MILLIS;
            if (diff == 0) {
                diff = 1;
            }
            formatted =  diff + " " + context.getString(R.string.str_minutes_ago_camel_case);
        } else if (diff < 90 * MINUTE_MILLIS) {
            diff = diff / HOUR_MILLIS;
            if (diff == 0) {
                diff = 1;
            }
            formatted =  diff + " " + context.getString(R.string.str_hour_ago_camel_case);
        } else if (diff < 24 * HOUR_MILLIS) {
            diff = diff / HOUR_MILLIS;
            if (diff == 0) {
                diff = 1;
            }
            formatted =  diff + " " + context.getString(R.string.str_hours_ago_camel_case);
        } else if (diff < 48 * HOUR_MILLIS) {
            diff = diff / DAY_MILLIS;
            if (diff == 0) {
                diff = 1;
            }
            formatted =  diff + " " + context.getString(R.string.str_day_ago_camel_case);
        } else {
            diff = diff / DAY_MILLIS;
            if (diff == 0) {
                diff = 1;
            }
            formatted =  diff + " " + context.getString(R.string.str_days_ago_camel_case);
        }

        return formatted;
    }*/


    /**
     * show formatted time from now
     *
     * @param dateTime date time in long
     * @return return formatted time
     */
   /* public static String fromAgoSmall(Context context, long dateTime) {

        String formatted = "";

        // TODO: localize
        if (dateTime == 0) {
            return formatted;
        }

        long diff = System.currentTimeMillis() - dateTime;
        if (diff < 0) {
            formatted = context.getString(R.string.str_just_now_small);
        }

        if (diff < MINUTE_MILLIS) {
            diff = diff / SECOND_MILLIS;
            if (diff == 0) {
                diff = 1;
            }
            formatted =  diff + " " + context.getString(R.string.str_seconds_ago);
        } else if (diff < 2 * MINUTE_MILLIS) {
            diff = diff / MINUTE_MILLIS;
            if (diff == 0) {
                diff = 1;
            }
            formatted =  diff + " " + context.getString(R.string.str_minute_ago);
        } else if (diff < 50 * MINUTE_MILLIS) {
            diff = diff / MINUTE_MILLIS;
            if (diff == 0) {
                diff = 1;
            }
            formatted =  diff + " " + context.getString(R.string.str_minutes_ago);
        } else if (diff < 90 * MINUTE_MILLIS) {
            diff = diff / HOUR_MILLIS;
            if (diff == 0) {
                diff = 1;
            }
            formatted =  diff + " " + context.getString(R.string.str_hour_ago);
        } else if (diff < 24 * HOUR_MILLIS) {
            diff = diff / HOUR_MILLIS;
            if (diff == 0) {
                diff = 1;
            }
            formatted =  diff + " " + context.getString(R.string.str_hours_ago);
        } else if (diff < 48 * HOUR_MILLIS) {
            diff = diff / DAY_MILLIS;
            if (diff == 0) {
                diff = 1;
            }
            formatted =  diff + " " + context.getString(R.string.str_day_ago_camel_case);
        } else {
            diff = diff / DAY_MILLIS;
            if (diff == 0) {
                diff = 1;
            }
            formatted =  diff + " " + context.getString(R.string.str_days_ago_camel_case);
        }

        return formatted;
    }*/

    /**
     * show formatted time from now
     *
     * @param dateTime            date time in string format
     * @param inputDateTimeFormat format for display time if max recent time is over
     * @return return formatted time
     */
    public static String fromNow(String dateTime, String inputDateTimeFormat) {
        String formatted = "";
        /*try {
            SimpleDateFormat sdf = new SimpleDateFormat(inputDateTimeFormat, Locale.getDefault());
            Date date = sdf.parse(dateTime);
            formatted = fromNow(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        return formatted;
    }

    /**
     * show formatted time from now
     *
     * @param dateTime      date time in long
     * @param maxRecentTime showing time from now until max recent time
     * @param format        format for display time if max recent time is over
     * @return return formatted time
     */
    public static String fromNow(long dateTime, long maxRecentTime, String format) {
        String formatted = "";
        /*try {
            long current = System.currentTimeMillis();
            long diff = current - dateTime;

            if (diff < maxRecentTime) {
                formatted = fromNow(dateTime);
            } else {
                formatted = new SimpleDateFormat(format, Locale.getDefault()).format(dateTime);
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }*/
        return formatted;
    }

    /*public static String parseDate(double time) {
        String datetime = "";
        SimpleDateFormat myFormat = new SimpleDateFormat("MMM dd, yyyy");
        Date date = new Date();
        date.setTime(time);
        datetime = myFormat.format(date);
//        System.out.println("Current Date Time in give format: " + datetime);

        return datetime;
    }*/

    public static String parseDate(long time) {
        String datetime = "";
        SimpleDateFormat myFormat = new SimpleDateFormat("hh:mm aa");
        Date date = new Date();
        date.setTime((long) time);
        datetime = myFormat.format(date);
        return datetime;
    }

    public static String parseChatSectioDate(long time) {
        String datetime = "";
        SimpleDateFormat myFormat = new SimpleDateFormat("MMMM dd, yyyy");
        Date date = new Date();
        date.setTime((long) time);
        datetime = myFormat.format(date);
        return datetime;
    }

    public static long parseLongDate(long time) {
        Date date = new Date();
        date.setTime((long) time);
        return date.getTime();
    }
    public static String parseDate1(long time) {
        String datetime = "";
        SimpleDateFormat myFormat = new SimpleDateFormat("MMM dd, yyyy HH:MM");
        Date date = new Date();
        date.setTime(time);
        datetime = myFormat.format(date);
//        System.out.println("Current Date Time in give format: " + datetime);

        return datetime;
    }

    public static String parseDateVideo(long time) {
        String datetime = "";
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/M/yyyy");
        Date date = new Date();
        date.setTime(time);
        datetime = myFormat.format(date);
//        System.out.println("Current Date Time in give format: " + datetime);

        return datetime;
    }

    /* utc to local date conversion */
    public static String convertUtcToLocal(String dateToConvert) {
        if (!TextUtils.isEmpty(dateToConvert)) {
            String formattedDate = "";
            SimpleDateFormat df = ACCEPTED_TIMESTAMP_FORMATS[ACCEPTED_TIMESTAMP_FORMATS.length - 1];
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = null;
            try {
                date = df.parse(dateToConvert);
                df.setTimeZone(TimeZone.getDefault());
                formattedDate = df.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return formattedDate;
        }
        return "";
    }

    /* utc to local date time conversion */
    public static String convertUtcDateTimeToLocal(String dateToConvert, SimpleDateFormat dateToFormat) {
        if (!TextUtils.isEmpty(dateToConvert)) {
            String formattedDate = "";
            SimpleDateFormat df = ACCEPTED_TIMESTAMP_FORMATS[10];
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = null;
            try {
                date = df.parse(dateToConvert);
                df.setTimeZone(TimeZone.getDefault());
                formattedDate = dateToFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return formattedDate;
        }
        return "";
    }

    /* utc to local date time conversion */
    public static String convertUtcDateTimeToLocalDate(String dateToConvert) {
        if (!TextUtils.isEmpty(dateToConvert)) {
            String formattedDate = "";
            SimpleDateFormat df = ACCEPTED_TIMESTAMP_FORMATS[10];
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = null;
            try {
                date = df.parse(dateToConvert);
                df.setTimeZone(TimeZone.getDefault());
                formattedDate = ACCEPTED_TIMESTAMP_FORMATS[ACCEPTED_TIMESTAMP_FORMATS.length - 1].format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return formattedDate;
        }
        return "";
    }

    public static boolean isEndDateGreaterthanStartDate(String startDateString, String endDateString, SimpleDateFormat format){
        if (!TextUtils.isEmpty(startDateString) && !TextUtils.isEmpty(endDateString)) {
            try {
                Date startDate = format.parse(startDateString);
                Date endDate = format.parse(endDateString);
                return endDate.after(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /* dd-MM-yyyy utc to local date conversion */
    public static String convertDMYUtcToLocalDate(String dateToConvert) {
        if (!TextUtils.isEmpty(dateToConvert)) {
            String formattedDate = "";
            SimpleDateFormat df = ACCEPTED_TIMESTAMP_FORMATS[11];
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = null;
            try {
                date = df.parse(dateToConvert);
                df.setTimeZone(TimeZone.getDefault());
                formattedDate = ACCEPTED_TIMESTAMP_FORMATS[ACCEPTED_TIMESTAMP_FORMATS.length - 1].format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return formattedDate;
        }
        return "";
    }

    public static String currentFormattedDate(SimpleDateFormat format){
        Calendar calendar = Calendar.getInstance();
        return format.format(calendar.getTime());
    }

    public static String previousWeekDate(SimpleDateFormat format){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE ,-30);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return format.format(calendar.getTime());
    }

    public static String previousDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE ,-1);
        return ACCEPTED_TIMESTAMP_FORMATS[5].format(calendar.getTime());
    }

    // get start time/end time of current day
    public static Pair<String, String> currentStartEndDateTime(SimpleDateFormat format, boolean isYesterday) {
        String startDate,endDate;
        Calendar calendar = Calendar.getInstance();
        if (isYesterday)
            calendar.add(Calendar.DATE ,-1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        startDate = format.format(calendar.getTime());

        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);

        endDate = format.format(calendar.getTime());

        return new Pair<>(startDate, endDate);
    }
    public static String getWeekStartDate(SimpleDateFormat format){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return format.format(calendar.getTime());
    }

    public static String getMonthStartDate(SimpleDateFormat format){
        Calendar calendar = Calendar.getInstance();   // this takes current date
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return format.format(calendar.getTime());
    }
    /* utc to local date conversion */
    public static Pair<String, String> convertDate(int month, int year) {

        month++;
        String selectedMonth;
        if (month < 10)
            selectedMonth = "0" + month;
        else selectedMonth = "" + month;

        String monthYear = selectedMonth + "-" + year;

        if (!TextUtils.isEmpty(monthYear)) {
            Date myDate = null;
            try {
                myDate = mmYYMonthformat.parse(monthYear);
                String formattedDate = mmmYYMonthformat.format(myDate);
                return new Pair<>(monthYear, formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return new Pair<>("", "");
    }

    /* convert local format to utc format */
    public static String convertDate(String dateToConvert, SimpleDateFormat formatFromConvert , SimpleDateFormat formatToConvert) {
        if (!TextUtils.isEmpty(dateToConvert)) {
            String formattedDate = "";
            SimpleDateFormat df = formatFromConvert;
            df.setTimeZone(TimeZone.getDefault());
            Date date = null;
            try {
                date = df.parse(dateToConvert);
                formattedDate = formatToConvert.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return formattedDate;
        }
        return "";
    }
    public static Date formatDate(String dateToConvert, SimpleDateFormat formatFromConvert){
        try {
            return formatFromConvert.parse(dateToConvert);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getDayDifference(Date startDate, Date endDate){
        long diff = endDate.getTime() - startDate.getTime();
         long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
         return days;
    }

    /* convert local format to utc format */
    public static boolean isEndDateFuture(String startDateString, String endDateString, SimpleDateFormat df) {
        if (!TextUtils.isEmpty(startDateString)) {
            try {
                Date startDate = df.parse(startDateString);
                Date endDate = df.parse(endDateString);
                if (endDate.getTime() < startDate.getTime())
                    return false;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return true;
        }
        return true;
    }

    public static ArrayList<Date> getWeeksOfMonth(int month, int year) {
        ArrayList<Date> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd-MMM-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(DAY_OF_MONTH, 1);
        int ndays = cal.getActualMaximum(DAY_OF_MONTH);
        Timber.e( ndays + "<<<ff");
        while (cal.get(DAY_OF_WEEK) != Calendar.FRIDAY) {
            cal.add(DAY_OF_MONTH, 1);
            ndays--;
        }
        int remainingDays = ndays%7;
        if(remainingDays==0)
            ndays += 7;
        else
            ndays = ndays + 7 - remainingDays;

        int inc = 1;
        for (int i = 1; i <= ndays; i++) {
            String day = sdf.format(cal.getTime());
            Timber.e( day + "<<<");
            list.add(cal.getTime());
            inc++;
            if (i % 7 == 0) {
                Timber.e("=======week days===========");
                inc = 0;
            }
            if (inc >= 1 && i == ndays) {
                for (int ii = inc; ii <= 6; ii++) {
                    String dayi = sdf.format(cal.getTime());
                    Timber.e(dayi + "<<<");
                    inc++;
                }
            }
            cal.add(Calendar.DATE, 1);
        }
        return list;
    }
    public static long getDateAsHeaderId(long milliseconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
        return Long.parseLong(dateFormat.format(new Date(milliseconds)));
    }
}
