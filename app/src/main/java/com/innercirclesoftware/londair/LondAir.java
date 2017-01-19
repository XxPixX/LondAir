package com.innercirclesoftware.londair;

import android.app.Application;

import com.innercirclesoftware.londair.injection.components.ApplicationComponent;
import com.innercirclesoftware.londair.injection.components.DaggerApplicationComponent;
import com.innercirclesoftware.londair.injection.modules.AndroidModule;

import timber.log.Timber;

public class LondAir extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        initTimber();
        initDependencyInjection();
    }

    private void initTimber() {
        Timber.plant(new Timber.DebugTree());
    }

    private void initDependencyInjection() {
        component = DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return component;
    }
}
