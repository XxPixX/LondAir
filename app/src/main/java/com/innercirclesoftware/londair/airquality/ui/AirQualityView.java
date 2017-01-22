package com.innercirclesoftware.londair.airquality.ui;

import android.support.annotation.NonNull;

import com.innercirclesoftware.londair.airquality.CurrentForecast;
import com.innercirclesoftware.londair.base.BaseView;

interface AirQualityView extends BaseView {

    void showForecast(@NonNull CurrentForecast forecast);

    void hideForecast();
}
