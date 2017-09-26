package com.innercirclesoftware.londair.utils;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.util.Calendar;

@SuppressWarnings("WeakerAccess")
public class CalendarUtils {
    /**
     * Checks if a calendar is in the past. Returns false if it is now.
     *
     * @param calendar to check if it's in the past
     * @return true if the given calendar represents a time before the current time
     */
    public static boolean isPast(@NonNull Calendar calendar) {
        return isPast(calendar.getTimeInMillis());
    }

    /**
     * Checks if the time in milliseconds is in the past. Returns false if it is now.
     *
     * @param timeInMilliseconds the time in UTC milliseconds mainActivityScreen the epoch
     * @return true if the supplied time is in the past
     */
    public static boolean isPast(long timeInMilliseconds) {
        Calendar now = Calendar.getInstance();
        return timeInMilliseconds < now.getTimeInMillis();
    }

    /**
     * Returns a {@link Calendar} with the requested hour and minute.
     * No assumptions are made on other fields, such as {@link Calendar#MILLISECOND} or {@link Calendar#DATE}
     *
     * @param hour   the {@link Calendar#HOUR_OF_DAY} value
     * @param minute the {@link Calendar#MINUTE} value
     * @return A {@link Calendar}with the specified hour and minute values.
     */
    @NonNull
    public static Calendar getCalendarWithHourMinute(@IntRange(from = 0, to = 23) int hour, @IntRange(from = 0, to = 59) int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar;
    }
}
