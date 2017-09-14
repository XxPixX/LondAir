package com.innercirclesoftware.londair.preferences.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.innercirclesoftware.londair.preferences.PreferenceManager;
import com.innercirclesoftware.londair.utils.RxUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.reactivex.disposables.Disposable;
import timber.log.Timber;

class SettingsPresenterImpl implements SettingsPresenter {

    @NonNull private final PreferenceManager preferenceManager;
    @NonNull private static final DateFormat TIME_FORMAT = SimpleDateFormat.getTimeInstance();

    @Nullable private SettingsView view;

    @Nullable private Disposable notificationTimeDisposable;
    @Nullable private String formattedTime;

    public SettingsPresenterImpl(@NonNull PreferenceManager preferenceManager) {
        this.preferenceManager = preferenceManager;
        subscribeToNotificationTimeUpdates();
    }

    @Override
    public void attachView(@NonNull SettingsView view) {
        Timber.v("Attaching settings view");
        this.view = view;

        //check if the notification time has been fetched before updating the view
        if (formattedTime != null) view.setMorningNotificationTime(formattedTime);
    }

    @Override
    public void detachAllViews() {
        Timber.v("Detaching settings view");
        this.view = null;
    }

    @Override
    public void close() {
        Timber.v("Closing %s", this);
        RxUtils.dispose(notificationTimeDisposable);
        this.notificationTimeDisposable = null;
    }

    private void subscribeToNotificationTimeUpdates() {
        if (RxUtils.isRunning(notificationTimeDisposable)) {
            Timber.w("Requested to subscribe to the notification time updates but we are already subscribed");
            return;
        }

        this.notificationTimeDisposable = preferenceManager.morningNotificationTime()
                .map(Calendar::getTime)
                .map(TIME_FORMAT::format)
                .subscribe((@NonNull String formattedTime) -> {
                    Timber.i("Notification time changed to %s", formattedTime);
                    this.formattedTime = formattedTime;
                    if (view != null) view.setMorningNotificationTime(formattedTime);
                });
    }
}
