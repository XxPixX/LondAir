package com.innercirclesoftware.londair.data.notifications;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;

import com.innercirclesoftware.londair.Londair;
import com.innercirclesoftware.londair.R;
import com.innercirclesoftware.londair.data.preferences.PreferenceManager;
import com.innercirclesoftware.londair.data.tfl.Air;
import com.innercirclesoftware.londair.data.tfl.CurrentForecast;
import com.innercirclesoftware.londair.data.tfl.ForecastBand;
import com.innercirclesoftware.londair.data.tfl.TflService;
import com.innercirclesoftware.londair.ui.main.MainActivity;

import javax.inject.Inject;

import timber.log.Timber;

public class ForecastNotificationService extends IntentService {

    private static final int POLLUTION_NOTIFICATION_ID = 0;

    @Inject NotificationManagerCompat notificationManager;
    @Inject TflService tflService;
    @Inject PreferenceManager preferenceManager;

    @SuppressWarnings("unused") //is actually used by android
    public ForecastNotificationService() {
        super("ForecastNotificationService");
    }

    @SuppressWarnings("unused") //is actually used by android
    public ForecastNotificationService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Londair.getApplicationComponent(getApplicationContext()).inject(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        tflService.getAirQuality()
                .doOnSuccess(air -> Timber.i("Retrieved air quality in ForecastNotificationService"))
                .map(Air::getCurrentForecast)
                .map(currentForecasts -> currentForecasts.get(0))
                .toObservable()
                .withLatestFrom(preferenceManager.notificationMinSeverity().firstOrError().toObservable(), PreferenceForecastHolder::new)
                .filter(this::filterForecast)
                .map(preferenceForecastHolder -> preferenceForecastHolder.forecast)
                .doOnError(throwable -> Timber.w(throwable, "Failed to get air quality in ForecastNotificationService"))
                .subscribe(this::showNotification);
    }

    /**
     * @param holder holds the current forecast and the users preference towards the minimum severity
     * @return true if it should pass the filter, false otherwise
     */
    private boolean filterForecast(@NonNull PreferenceForecastHolder holder) {
        switch (holder.forecast.getForecastBand()) {
            case CurrentForecast.BAND_HIGH:
                return true; //always shown
            case CurrentForecast.BAND_MODERATE:
                return holder.minSeverity.equals(CurrentForecast.BAND_LOW) || holder.minSeverity.equals(CurrentForecast.BAND_MODERATE); //only when minimum is low or moderate
            case CurrentForecast.BAND_LOW:
                return holder.minSeverity.equals(CurrentForecast.BAND_LOW); //must be low
            case CurrentForecast.BAND_NONE:
                Timber.w("Current forecast has band \"None\" so could not determine appropriate action for notification severity filter");
                return false;
            default:
                Timber.w("Unknown forecast band when filtering according to severity filter: %s", holder.forecast.getForecastBand());
                return false;
        }
    }

    private void showNotification(@NonNull CurrentForecast today) {
        AudioAttributes attrs = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();

        Notification pollutionNotification = new Notification.Builder(this)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setCategory(Notification.CATEGORY_STATUS)
                .setStyle(new Notification.BigTextStyle().bigText(today.getForecastSummary()))
                .setSmallIcon(R.drawable.ic_notification_forecast)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_LOW)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), attrs)
                .setContentIntent(getNotificationClickIntent())
                .setContentTitle(getString(R.string.notification_forecast_title, today.getForecastBand()))
                .setContentText(today.getForecastSummary())
                .build();

        notificationManager.notify(POLLUTION_NOTIFICATION_ID, pollutionNotification);
    }

    @NonNull
    private PendingIntent getNotificationClickIntent() {
        //launch the main activity
        return PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), MainActivity.class), 0);
    }

    private static class PreferenceForecastHolder {

        @NonNull private final CurrentForecast forecast;
        @NonNull @ForecastBand private final String minSeverity;

        PreferenceForecastHolder(@NonNull CurrentForecast forecast, @NonNull @ForecastBand String minSeverity) {
            this.forecast = forecast;
            this.minSeverity = minSeverity;
        }
    }
}
