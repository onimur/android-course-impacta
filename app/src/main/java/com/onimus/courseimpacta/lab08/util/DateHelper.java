package com.onimus.courseimpacta.lab08.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {

    private static DateHelper helper;

    private DateHelper() {
        super();

    }

    public static DateHelper getInstance() {
        helper = helper == null ? new DateHelper() : helper;
        return helper;
    }

    private Date convertToDate(String format, String value) throws ParseException {
        final SimpleDateFormat sdf = new SimpleDateFormat(format,  Locale.getDefault());

        return sdf.parse(value);
    }

    public Long convertToTimeInMillis(String format, String value) throws ParseException {
        final Date d = convertToDate(format, value);

        return d.getTime();
    }

    public CharSequence format(String format, Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat(format,  Locale.getDefault());

        return sdf.format(date);
    }
}