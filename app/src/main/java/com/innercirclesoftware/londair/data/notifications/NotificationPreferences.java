package com.innercirclesoftware.londair.data.notifications;

import android.support.annotation.NonNull;

import com.innercirclesoftware.londair.utils.CalendarUtils;

import java.util.Calendar;

class NotificationPreferences {
    private final boolean enabled;
    @NonNull private final Calendar time;

    NotificationPreferences(boolean enabled, @NonNull Calendar time) {
        this.enabled = enabled;
        this.time = (Calendar) time.clone();
        this.time.set(Calendar.MILLISECOND, 0);
        this.time.set(Calendar.SECOND, 0);
        while (CalendarUtils.isPast(this.time)) this.time.add(Calendar.DATE, 1);
    }

    boolean isEnabled() {
        return enabled;
    }

    @NonNull
    Calendar getTime() {
        return time;
    }
}
