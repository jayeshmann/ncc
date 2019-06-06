package com.tsa.NCC_dte_punjab.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Akhil Tripathi on 29-06-2017.
 */

public class TimeConverter {
    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;

    public static String getDate(long ms) {
        // TODO: this is the value in ms

        StringBuffer text = new StringBuffer("");
        if (ms > DAY) {
            text.append(ms / DAY).append(" d ");
            ms %= DAY;
        }
        if (ms > HOUR) {
            text.append(ms / HOUR).append(" h ");
            ms %= HOUR;
        }
        if (ms > MINUTE) {
            text.append(ms / MINUTE).append(" m ");
            ms %= MINUTE;
        }
        if (ms > SECOND) {
            text.append(ms / SECOND).append(" s ");
            ms %= SECOND;
        }
        //text.append(ms + " ms");
        System.out.println(text.toString());
        return text.toString();
    }

    public static String getTimeDefference(long start, long end) {
        // TODO: this is the value in ms

        return "" + (end - start) / 1000;
    }

    public static String getDateFromMilli() {

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm:ss");
        // you can get seconds by adding  "...:ss" to it
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));

        String localTime = date.format(currentLocalTime);

        return localTime;
    }

    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }

    String getAfterDate(String sendDate, int afterDays) {
        String sDate1 = sendDate;
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("dd-MM-yyyy").parse(sDate1);
            // convert date to calendar
            Calendar c = Calendar.getInstance();
            c.setTime(date1);

            // manipulate date
            c.add(Calendar.DATE, afterDays); //same with c.add(Calendar.DAY_OF_MONTH, 1);

            // convert calendar to date
            date1 = c.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1.toString();
    }

}
