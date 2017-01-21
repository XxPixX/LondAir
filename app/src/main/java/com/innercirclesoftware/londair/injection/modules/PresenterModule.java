package com.innercirclesoftware.londair.injection.modules;

import android.support.annotation.NonNull;

import com.innercirclesoftware.londair.airquality.TflService;
import com.innercirclesoftware.londair.main.MainPresenter;
import com.innercirclesoftware.londair.main.MainPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    MainPresenter providesMainPresenter(@NonNull TflService tflService) {
        return new MainPresenterImpl(tflService);
    }
}
