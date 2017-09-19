package com.innercirclesoftware.londair.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.innercirclesoftware.londair.data.preferences.PreferenceManager;

import java.util.Calendar;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class NotificationScheduler {

    private static final int MORNING_NOTIFICATION_REQUEST_CODE = 0;
    @NonNull private final Context context;
    @NonNull private final AlarmManager alarmManager;

    @Inject
    public NotificationScheduler(@NonNull Context context, @NonNull AlarmManager alarmManager, @NonNull PreferenceManager preferenceManager) {
        this.context = context;
        this.alarmManager = alarmManager;

        Observable.combineLatest(preferenceManager.notificationEnabled(), preferenceManager.notificationTime(), NotificationPreferences::new)
                .subscribeOn(Schedulers.io())
                .doOnNext(this::handleNotificationPreferenceChanged)
                .subscribe();
    }

    private void handleNotificationPreferenceChanged(@NonNull NotificationPreferences preferences) {
        if (preferences.isEnabled()) scheduleNotification(preferences.getTime());
        else unscheduleMorningNotification();
    }

    private void scheduleNotification(@NonNull Calendar time) {
        Timber.i("Scheduling pollution notification for %s", time.getTime());
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                time.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                getMorningNotificationPendingIntent()
        );
    }

    private void unscheduleMorningNotification() {
        Timber.i("Canceling pollution notification");
        alarmManager.cancel(getMorningNotificationPendingIntent());
    }

    @NonNull
    private PendingIntent getMorningNotificationPendingIntent() {
        Intent notificationIntent = new Intent(context, ForecastNotificationService.class);
        return PendingIntent.getService(
                context,
                MORNING_NOTIFICATION_REQUEST_CODE,
                notificationIntent,
                0 //no flags for the intent
        );
    }
}
