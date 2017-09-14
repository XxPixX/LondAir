package com.innercirclesoftware.londair.utils;

import android.support.annotation.NonNull;

import java.util.Calendar;

@SuppressWarnings("WeakerAccess")
public class CalendarUtils {
    /**
     * @param calendar to check if it's in the past
     * @return true if the given calendar represents a time before the current time
     */
    public static boolean isPast(@NonNull Calendar calendar) {
        return isPast(calendar.getTimeInMillis());
    }

    /**
     * @param timeInMilliseconds the time in UTC milliseconds from the epoch
     * @return true if the supplied time is in the past
     */
    public static boolean isPast(long timeInMilliseconds) {
        Calendar now = Calendar.getInstance();
        return timeInMilliseconds < now.getTimeInMillis();
    }
}
