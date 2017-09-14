package com.innercirclesoftware.londair.injection.modules;

import android.support.annotation.NonNull;

import com.innercirclesoftware.londair.data.tfl.TflService;
import com.innercirclesoftware.londair.ui.main.airquality.AirQualityPresenter;
import com.innercirclesoftware.londair.ui.main.airquality.AirQualityPresenterImpl;
import com.innercirclesoftware.londair.ui.main.MainPresenter;
import com.innercirclesoftware.londair.ui.main.MainPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    MainPresenter providesMainPresenter(@NonNull TflService tflService) {
        return new MainPresenterImpl(tflService);
    }

    @Provides
    AirQualityPresenter providesAirQualityPresenter(){
        return new AirQualityPresenterImpl();
    }
}
