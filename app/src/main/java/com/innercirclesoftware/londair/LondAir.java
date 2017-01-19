package com.innercirclesoftware.londair;

import android.app.Application;

import timber.log.Timber;

public class LondAir extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initTimber();
    }

    private void initTimber() {
        Timber.plant(new Timber.DebugTree());
    }
}
