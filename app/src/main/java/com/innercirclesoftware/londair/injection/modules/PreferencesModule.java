package com.innercirclesoftware.londair.injection.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.innercirclesoftware.londair.preferences.PreferenceManager;
import com.innercirclesoftware.londair.preferences.PreferenceManagerImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PreferencesModule {

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(@NonNull Context context) {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    RxSharedPreferences providesRxSharedPreferences(@NonNull SharedPreferences sharedPreferences) {
        return RxSharedPreferences.create(sharedPreferences);
    }

    @Provides
    @Singleton
    PreferenceManager providesPreferenceManager(@NonNull SharedPreferences preferences, @NonNull RxSharedPreferences rxPreferences) {
        return new PreferenceManagerImpl(preferences, rxPreferences);
    }

}
