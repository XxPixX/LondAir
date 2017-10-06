package com.innercirclesoftware.londair;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.innercirclesoftware.londair.data.analytics.CrashlyticsTree;
import com.innercirclesoftware.londair.data.notifications.NotificationScheduler;
import com.innercirclesoftware.londair.data.tfl.TflService;
import com.innercirclesoftware.londair.injection.components.ApplicationComponent;
import com.innercirclesoftware.londair.injection.components.DaggerApplicationComponent;
import com.innercirclesoftware.londair.injection.modules.AndroidModule;
import com.innercirclesoftware.londair.injection.modules.NetworkModule;

import javax.inject.Inject;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class Londair extends Application {

    private ApplicationComponent component;

    @Inject NotificationScheduler notificationScheduler;

    @Override
    public void onCreate() {
        super.onCreate();
        initFabric();
        initTimber();
        initDependencyInjection();
    }

    private void initFabric() {
        Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics(), new Answers())
                .debuggable(BuildConfig.DEBUG)
                .build();

        Fabric.with(fabric);
    }

    private void initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
        else Timber.plant(new CrashlyticsTree());
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

    private static Londair from(@NonNull Context context) {
        return (Londair) context.getApplicationContext();
    }

    public static ApplicationComponent getApplicationComponent(@NonNull Context context) {
        return from(context).getApplicationComponent();
    }
}
