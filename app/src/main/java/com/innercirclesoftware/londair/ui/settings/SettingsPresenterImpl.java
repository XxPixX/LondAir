package com.innercirclesoftware.londair.ui.settings;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.innercirclesoftware.londair.data.preferences.PreferenceManager;
import com.innercirclesoftware.londair.data.tfl.ForecastBand;
import com.innercirclesoftware.londair.utils.RxUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.reactivex.disposables.Disposable;
import timber.log.Timber;

class SettingsPresenterImpl implements SettingsPresenter {

    @NonNull private final PreferenceManager preferenceManager;
    @NonNull private static final DateFormat TIME_FORMAT = SimpleDateFormat.getTimeInstance(DateFormat.SHORT);

    @Nullable private SettingsView view;

    @Nullable private Disposable notifEnabledDisposable;
    @Nullable private Disposable notifTimeDisposable;
    @Nullable private Disposable notifMinSeverityDisposable;

    @Nullable private Boolean notifEnabled;
    @Nullable private String formattedNotifTime;
    @Nullable private Calendar notifTime;
    @Nullable @ForecastBand private String minNotifSeverity;

    SettingsPresenterImpl(@NonNull PreferenceManager preferenceManager) {
        this.preferenceManager = preferenceManager;
        subscribeToNotificationEnabledUpdates();
        subscribeToNotificationTimeUpdates();
        subscribeToNotificationSeverityUpdates();
    }

    @Override
    public void attachView(@NonNull SettingsView view) {
        Timber.v("Attaching settings view");
        this.view = view;

        //check if the preferences values have been fetched before updating the view
        if (formattedNotifTime != null) view.setNotificationTime(formattedNotifTime);
        if (notifEnabled != null) view.setNotificationEnabled(notifEnabled);
        if (minNotifSeverity != null) view.setNotificationSeverity(minNotifSeverity);
    }

    @Override
    public void detachAllViews() {
        Timber.v("Detaching settings view");
        this.view = null;
    }

    @Override
    public void close() {
        Timber.v("Closing %s", this);
        RxUtils.dispose(notifTimeDisposable, notifEnabledDisposable, notifMinSeverityDisposable);
        this.notifTimeDisposable = null;
        this.notifEnabledDisposable = null;
        this.notifMinSeverityDisposable = null;
    }

    private void subscribeToNotificationTimeUpdates() {
        if (RxUtils.isRunning(notifTimeDisposable)) {
            Timber.w("Requested to subscribe to the notification time updates but we are already subscribed");
            return;
        }

        this.notifTimeDisposable = preferenceManager.notificationTime()
                .doOnNext(calendar -> this.notifTime = calendar)
                .map(Calendar::getTime)
                .map(TIME_FORMAT::format)
                .subscribe((@NonNull String formattedTime) -> {
                    Timber.i("Notification time changed to %s", formattedTime);
                    this.formattedNotifTime = formattedTime;
                    if (view != null) view.setNotificationTime(formattedTime);
                });
    }

    private void subscribeToNotificationEnabledUpdates() {
        if (RxUtils.isRunning(notifEnabledDisposable)) {
            Timber.w("Requested to subscribe to the notification enabled updates but we are already subscribed");
            return;
        }

        this.notifEnabledDisposable = preferenceManager.notificationEnabled()
                .subscribe(isEnabled -> {
                    Timber.i("Notification enabled changed to %s", isEnabled);
                    this.notifEnabled = isEnabled;
                    if (view != null) view.setNotificationEnabled(isEnabled);
                });


    }

    private void subscribeToNotificationSeverityUpdates() {
        if (RxUtils.isRunning(notifMinSeverityDisposable)) {
            Timber.w("Requested to subscribe to the notification severity updates but we are already subscribed");
            return;
        }

        this.notifMinSeverityDisposable = preferenceManager.notificationMinSeverity()
                .subscribe(newMinSeverity -> {
                    Timber.i("Notification minimum severity changed to %s", newMinSeverity);
                    this.minNotifSeverity = newMinSeverity;
                    if (view != null) view.setNotificationSeverity(newMinSeverity);
                });
    }

    @Override
    public void onNotificationSwitchChecked(boolean isChecked) {
        Timber.i("onNotificationSwitchChecked, isChecked=%s", isChecked);
        preferenceManager.setNotificationEnabled(isChecked);
    }

    @Override
    public void onNotificationTimeClicked() {
        if (notifTime != null) {
            if (view != null) view.showNotificationTimePicker(notifTime);
            else Timber.w("onNotificationTimeClicked with null view");
        } else {
            Timber.w("onNotificationTimeClicked but the currently fetched time is null. formatted time is %s", formattedNotifTime);
        }
    }

    @Override
    public void onMinimumSeverityClicked() {
        if (view != null) {
            if (minNotifSeverity != null) view.showMinimumSeverityPicker(minNotifSeverity);
            else Timber.w("onMinimumSeverityClicked but minNotifSeverity is null");
        } else {
            Timber.w("onMinimumSeverityClicked but the view is null");
        }
    }

    @Override
    public void onNotificationTimeSelected(int hour, int minute) {
        Timber.i("onNotificationTimeClicked with hour %s, minute %s", hour, minute);
        preferenceManager.setNotificationHour(hour);
        preferenceManager.setNotificationMinute(minute);
    }

    @Override
    public void onMinimumSeverityChanged(@ForecastBand String newSeverity) {
        Timber.i("onMinimumSeverityChanged with severity %s", newSeverity);
        preferenceManager.setNotificationMinSeverity(newSeverity);
    }
}
