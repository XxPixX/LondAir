package com.innercirclesoftware.londair.utils;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import timber.log.Timber;

public class PrefUtils {

    public static void setInt(@NonNull SharedPreferences sharedPreferences, @NonNull String key, int value) {
        Timber.v("Settings %s in %s to integer %s", key, sharedPreferences.toString(), key, value);
        sharedPreferences.edit().putInt(key, value).apply();
    }
}
