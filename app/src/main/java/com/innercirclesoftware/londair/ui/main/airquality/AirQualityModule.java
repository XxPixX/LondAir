package com.innercirclesoftware.londair.ui.main.airquality;

import dagger.Module;
import dagger.Provides;

@Module
class AirQualityModule {

    @Provides
    @PerAirQualityFragment
    AirQualityPresenter providesAirQualityPresenter() {
        return new AirQualityPresenterImpl();
    }
}
