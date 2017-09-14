package com.innercirclesoftware.londair.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.innercirclesoftware.londair.utils.CalendarUtils;

import java.util.Calendar;

import javax.inject.Inject;

import timber.log.Timber;

public class NotificationScheduler {

    private static final int MORNING_NOTIFICATION_REQUEST_CODE = 0;
    @NonNull private Context context;
    @NonNull private AlarmManager alarmManager;

    @Inject
    public NotificationScheduler(@NonNull Context context, @NonNull AlarmManager alarmManager) {
        this.context = context;
        this.alarmManager = alarmManager;
    }

    public void scheduleMorningNotification() {
        Timber.i("Scheduling morning notification");
        Calendar notificationTime = Calendar.getInstance();
        //8am sharp
        notificationTime.set(Calendar.HOUR_OF_DAY, 8);
        notificationTime.set(Calendar.MINUTE, 0);
        notificationTime.set(Calendar.MILLISECOND, 0);
        while (CalendarUtils.isPast(notificationTime)) {
            notificationTime.add(Calendar.DATE, 1);
        }

        PendingIntent notificationPendingIntent = getMorningNotificationPendingIntent();

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                notificationTime.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                notificationPendingIntent
        );
    }

    public void unscheduleMorningNotification() {
        Timber.i("Unscheduling morning notification");
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
