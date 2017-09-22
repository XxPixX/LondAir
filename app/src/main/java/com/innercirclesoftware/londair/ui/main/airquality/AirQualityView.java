package com.innercirclesoftware.londair.ui.main.airquality;

import android.support.annotation.NonNull;

import com.innercirclesoftware.londair.base.BaseView;
import com.innercirclesoftware.londair.data.tfl.CurrentForecast;

public interface AirQualityView extends BaseView {

    void showForecast(@NonNull CurrentForecast forecast);

    void onShowForecastRequested(@NonNull CurrentForecast forecast);

    void showDetailedPollutantSummaries(boolean detailed);

}
