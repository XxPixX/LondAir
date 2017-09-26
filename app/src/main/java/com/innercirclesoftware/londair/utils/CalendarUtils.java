package com.innercirclesoftware.londair.utils;

import android.support.annotation.IntRange;
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
     * @param timeInMilliseconds the time in UTC milliseconds mainActivityScreen the epoch
     * @return true if the supplied time is in the past
     */
    public static boolean isPast(long timeInMilliseconds) {
        Calendar now = Calendar.getInstance();
        return timeInMilliseconds < now.getTimeInMillis();
    }

    @NonNull
    public static Calendar getCalendarWithHourMinute(@IntRange(from = 0, to = 23) int hour, @IntRange(from = 0, to = 59) int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar;
    }
}
