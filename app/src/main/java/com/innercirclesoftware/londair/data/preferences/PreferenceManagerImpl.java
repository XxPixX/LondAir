package com.innercirclesoftware.londair.data.preferences;

import android.content.SharedPreferences;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.innercirclesoftware.londair.data.tfl.CurrentForecast;
import com.innercirclesoftware.londair.data.tfl.ForecastBand;
import com.innercirclesoftware.londair.utils.CalendarUtils;
import com.innercirclesoftware.londair.utils.PrefUtils;
import com.innercirclesoftware.londair.utils.PrimitiveUtils;
import com.innercirclesoftware.londair.utils.StringUtils;

import java.util.Calendar;

import io.reactivex.Observable;
import timber.log.Timber;

public class PreferenceManagerImpl implements PreferenceManager {

    @NonNull private final SharedPreferences preferences;
    @NonNull private final RxSharedPreferences rxSharedPreferences;

    @NonNull private static final String KEY_NOTIFICATION_HOUR = "KEY_NOTIFICATION_HOUR";
    private static final int DEF_VAL_NOTIFICATION_HOUR = 9;

    @NonNull private static final String KEY_NOTIFICATION_MINUTE = "KEY_NOTIFICATION_MINUTE";
    private static final int DEF_VAL_NOTIFICATION_MINUTE = 0;

    @NonNull private static final String KEY_NOTIFICATION_ENABLED = "KEY_NOTIFICATION_ENABLED";
    private static final boolean DEF_VAL_NOTIFICATION_ENABLED = true;

    @NonNull private static final String KEY_NOTIFICATION_MIN_SEVERITY = "KEY_NOTIFICATION_MIN_SEVERITY";
    @ForecastBand private static final String DEF_VAL_NOTIFICATION_MIN_SEVERITY = CurrentForecast.BAND_LOW;


    public PreferenceManagerImpl(@NonNull SharedPreferences preferences, @NonNull RxSharedPreferences rxSharedPreferences) {
        this.preferences = preferences;
        this.rxSharedPreferences = rxSharedPreferences;
    }

    @Override
    public void setNotificationHour(@IntRange(from = 0, to = 23) int hour) {
        PrimitiveUtils.assertInRange(0, hour, 23); //final check to see if the hour is valid
        Timber.i("Settings the notification time to %s", hour);
        PrefUtils.setInt(preferences, KEY_NOTIFICATION_HOUR, hour);
    }

    @Override
    public void setNotificationMinute(@IntRange(from = 0, to = 59) int minute) {
        PrimitiveUtils.assertInRange(0, minute, 59); //final check to see if minute is valid
        Timber.i("Settings the notification minute to %s", minute);
        PrefUtils.setInt(preferences, KEY_NOTIFICATION_MINUTE, minute);
    }

    @Override
    public void setNotificationMinSeverity(@ForecastBand String severity) {
        StringUtils.assertContains(CurrentForecast.ALL_BANDS, severity);
        Timber.i("Settings notification minimum severity to %s", severity);
        PrefUtils.setString(preferences, KEY_NOTIFICATION_MIN_SEVERITY, severity);
    }

    @Override
    public void setNotificationEnabled(boolean isChecked) {
        Timber.i("Settings the notification enabled to %s", isChecked);
        PrefUtils.setBoolean(preferences, KEY_NOTIFICATION_ENABLED, isChecked);
    }

    @Override
    public Observable<Integer> notificationHour() {
        return rxSharedPreferences.getInteger(KEY_NOTIFICATION_HOUR, DEF_VAL_NOTIFICATION_HOUR)
                .asObservable();
    }

    @Override
    public Observable<Integer> notificationMinute() {
        return rxSharedPreferences.getInteger(KEY_NOTIFICATION_MINUTE, DEF_VAL_NOTIFICATION_MINUTE)
                .asObservable();
    }

    @Override
    public Observable<Calendar> notificationTime() {
        return Observable.combineLatest(notificationHour(), notificationMinute(), CalendarUtils::getCalendarWithHourMinute);
    }

    @Override
    public Observable<Boolean> notificationEnabled() {
        return rxSharedPreferences.getBoolean(KEY_NOTIFICATION_ENABLED, DEF_VAL_NOTIFICATION_ENABLED)
                .asObservable();
    }

    @Override
    public Observable<String> notificationMinSeverity() {
        return rxSharedPreferences.getString(KEY_NOTIFICATION_MIN_SEVERITY, DEF_VAL_NOTIFICATION_MIN_SEVERITY)
                .asObservable();
    }
}
