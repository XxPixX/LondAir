package com.innercirclesoftware.londair.data.preferences;

import android.support.annotation.IntRange;

import com.innercirclesoftware.londair.data.tfl.ForecastBand;

import java.util.Calendar;

import io.reactivex.Observable;

public interface PreferenceManager {

    void setNotificationHour(@IntRange(from = 0, to = 23) int hour);

    void setNotificationMinute(@IntRange(from = 0, to = 59) int minute);

    void setNotificationMinSeverity(@ForecastBand String severity);

    void setNotificationEnabled(boolean isChecked);

    /**
     * @return an observable which when subscribed to, emits the hour for when the notification is scheduled.
     * If the user changes the hour, the observable will emit the new hour. The hour is always from 0 to 23.
     */
    Observable<Integer> notificationHour();

    /**
     * @return and observable which when subscribed to, emits the minute for when the notification is scheduled.
     * If the user changes the minute, the observable will emit the new minute. The minutes is always from 0 to 59.
     */
    Observable<Integer> notificationMinute();

    /**
     * @return a calendar class containing the hour and minute of when the notification time should go off.
     * No assumptions are made on the date, second, millisecond of the calendar.
     */
    Observable<Calendar> notificationTime();


    /**
     * @return an observable which when subscribed to, emits whether the notification is enabled.
     * If the user disables or enables notification, the observable will emit the new user preferences.
     */
    Observable<Boolean> notificationEnabled();


    /**
     * @return an observable which when subscribed to, emits the minimum severity/forecast band that the pollution level
     * must meet before showing a notification
     */
    Observable<String> notificationMinSeverity();

}
