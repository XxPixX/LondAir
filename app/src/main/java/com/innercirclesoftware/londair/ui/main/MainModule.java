package com.innercirclesoftware.londair.ui.main;

import android.support.annotation.NonNull;

import com.innercirclesoftware.londair.data.tfl.TflService;

import dagger.Module;
import dagger.Provides;

@Module
class MainModule {

    @Provides
    @PerMainActivity
    MainPresenter providesMainPresenter(@NonNull TflService tflService) {
        return new MainPresenterImpl(tflService);
    }
}
