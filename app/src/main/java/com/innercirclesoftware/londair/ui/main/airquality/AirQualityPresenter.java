package com.innercirclesoftware.londair.ui.main.airquality;

import android.support.annotation.NonNull;

import com.innercirclesoftware.londair.base.BasePresenter;
import com.innercirclesoftware.londair.data.tfl.CurrentForecast;

interface AirQualityPresenter extends BasePresenter<AirQualityView> {

    void onShowForecastRequested(@NonNull CurrentForecast forecast);

    void onPollutantsCardClicked();
}
