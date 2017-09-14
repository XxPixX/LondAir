package com.innercirclesoftware.londair;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.crashlytics.android.Crashlytics;
import com.innercirclesoftware.londair.data.tfl.TflService;
import com.innercirclesoftware.londair.injection.components.ApplicationComponent;
import com.innercirclesoftware.londair.injection.components.DaggerApplicationComponent;
import com.innercirclesoftware.londair.injection.modules.AndroidModule;
import com.innercirclesoftware.londair.injection.modules.NetworkModule;
import com.innercirclesoftware.londair.notifications.NotificationScheduler;

import javax.inject.Inject;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class LondAir extends Application {

    private ApplicationComponent component;

    @Inject
    NotificationScheduler notificationScheduler;

    @Override
    public void onCreate() {
        super.onCreate();
        initFabric();
        initTimber();
        initDependencyInjection();
        scheduleNotifications();
    }

    private void scheduleNotifications() {
        notificationScheduler.scheduleMorningNotification();
    }

    private void initFabric() {
        Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(BuildConfig.DEBUG)
                .build();

        Fabric.with(fabric);
    }

    private void initTimber() {
        Timber.plant(new Timber.DebugTree());
    }

    private void initDependencyInjection() {
        component = DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .networkModule(new NetworkModule(TflService.BASE_URL))
                .build();

        component.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return component;
    }

    public static LondAir from(@NonNull Context context) {
        return (LondAir) context;
    }

    public static ApplicationComponent getApplicationComponent(@NonNull Context context) {
        return from(context).getApplicationComponent();
    }
}
