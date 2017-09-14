package com.innercirclesoftware.londair.notifications;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;

import com.innercirclesoftware.londair.LondAir;
import com.innercirclesoftware.londair.R;
import com.innercirclesoftware.londair.data.tfl.Air;
import com.innercirclesoftware.londair.data.tfl.CurrentForecast;
import com.innercirclesoftware.londair.data.tfl.TflService;
import com.innercirclesoftware.londair.ui.main.MainActivity;

import javax.inject.Inject;

import timber.log.Timber;

public class ForecastNotificationService extends IntentService {

    private static final int MORNING_NOTIFICATION_ID = 0;

    @Inject NotificationManagerCompat notificationManager;
    @Inject TflService tflService;

    public ForecastNotificationService() {
        this("ForecastNotificationService");
    }

    public ForecastNotificationService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LondAir.getApplicationComponent(getApplicationContext()).inject(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        tflService.getAirQuality()
                .doOnSuccess(air -> Timber.i("Retrieved air quality in ForecastNotificationService"))
                .map(Air::getCurrentForecast)
                .map(currentForecasts -> currentForecasts.get(0))
                .doOnError(throwable -> Timber.w(throwable, "Failed to get air quality in ForecastNotificationService"))
                .subscribe(this::showNotification);
    }

    private void showNotification(@NonNull CurrentForecast today) {
        Notification morningNotification = new Notification.Builder(this)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setStyle(new Notification.BigTextStyle().bigText(today.getForecastSummary()))
                .setSmallIcon(android.R.drawable.ic_menu_add)
                .setPriority(Notification.PRIORITY_LOW)
                .setAutoCancel(true)
                .setContentIntent(getNotificationClickIntent())
                .setContentTitle(getString(R.string.notification_forecast_title, today.getForecastBand()))
                .setContentText(today.getForecastSummary())
                .build();

        notificationManager.notify(MORNING_NOTIFICATION_ID, morningNotification);
    }

    @NonNull
    private PendingIntent getNotificationClickIntent() {
        //launch the main activity
        return PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), MainActivity.class), 0);
    }
}
