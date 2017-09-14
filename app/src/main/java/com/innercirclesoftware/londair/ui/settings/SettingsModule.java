package com.innercirclesoftware.londair.ui.settings;

import android.support.annotation.NonNull;

import com.innercirclesoftware.londair.data.preferences.PreferenceManager;

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
