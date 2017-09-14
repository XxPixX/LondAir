package com.innercirclesoftware.londair.ui.main.airquality;

import com.innercirclesoftware.londair.ui.main.MainComponent;

import dagger.Component;

@PerAirQualityFragment
@Component(dependencies = MainComponent.class, modules = AirQualityModule.class)
public interface AirQualityComponent {

    void inject(AirQualityFragment airQualityFragment);

}
