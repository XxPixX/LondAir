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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
        tflService.getAirQuality().enqueue(new Callback<Air>() {
            @Override
            public void onResponse(Call<Air> call, Response<Air> response) {
                Timber.i("Retrieved air quality in ForecastNotificationService");
                Air air = response.body();
                CurrentForecast today = air.getCurrentForecast().get(0);
                showNotification(today);
            }

            @Override
            public void onFailure(Call<Air> call, Throwable t) {
                Timber.w(t, "Failed to get air quality in ForecastNotificationService");
            }
        });
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
