package com.innercirclesoftware.londair.preferences.ui;

import android.support.annotation.NonNull;

import com.innercirclesoftware.londair.preferences.PreferenceManager;

import dagger.Module;
import dagger.Provides;

@Module
class SettingsModule {

    @Provides
    @PerSettingsActivity
    SettingsPresenter providesSettingsPresenter(@NonNull PreferenceManager preferenceManager) {
        return new SettingsPresenterImpl(preferenceManager);
    }
}
