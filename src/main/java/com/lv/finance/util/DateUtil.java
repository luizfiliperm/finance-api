package com.lv.finance.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public final static String DATE_FORMAT = "dd/MM/yyyy";

    public final static String DATE_REGEX = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";

    public final static String LOCAL_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";

    public final static String LOCAL_DATE_TIME_REGEX = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4} (0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";

    public static String formatLocalDateToString(LocalDate date){
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static LocalDate formatStringToLocalDate(String date){
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static String formatLocalDateTimeToString(LocalDateTime date){
        return date.format(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_FORMAT));
    }

    public static LocalDateTime formatStringToLocalDateTime(String date){
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_FORMAT));
    }
}
