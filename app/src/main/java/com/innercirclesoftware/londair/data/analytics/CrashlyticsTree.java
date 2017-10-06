package com.innercirclesoftware.londair.data.analytics;

import android.util.Log;

import com.crashlytics.android.Crashlytics;

import timber.log.Timber;

public class CrashlyticsTree extends Timber.Tree {

    @Override
    protected boolean isLoggable(String tag, int priority) {
        return priority >= Log.WARN;
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        Crashlytics.log(priority, tag, message);
        if (t != null) Crashlytics.logException(t);
    }
}
