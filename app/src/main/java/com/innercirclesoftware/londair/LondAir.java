package com.innercirclesoftware.londair;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.innercirclesoftware.londair.airquality.TflService;
import com.innercirclesoftware.londair.injection.components.ApplicationComponent;
import com.innercirclesoftware.londair.injection.components.DaggerApplicationComponent;
import com.innercirclesoftware.londair.injection.modules.AndroidModule;
import com.innercirclesoftware.londair.injection.modules.NetworkModule;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class LondAir extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        initFabric();
        initTimber();
        initDependencyInjection();
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
    }

    public ApplicationComponent getApplicationComponent() {
        return component;
    }
}
