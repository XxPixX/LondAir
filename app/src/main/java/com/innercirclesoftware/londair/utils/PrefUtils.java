package com.innercirclesoftware.londair.utils;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import timber.log.Timber;

public class PrefUtils {

    public static void setInt(@NonNull SharedPreferences sharedPreferences, @NonNull String key, int value) {
        Timber.v("Settings %s to integer %s", key, value);
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public static void setBoolean(@NonNull SharedPreferences sharedPreferences, @NonNull String key, boolean value) {
        Timber.v("Settings %s to boolean %s", key, value);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static void setString(@NonNull SharedPreferences sharedPreferences, @NonNull String key, @NonNull String value) {
        Timber.v("Settings %s to string %s", key, value);
        sharedPreferences.edit().putString(key, value).apply();
    }
}
