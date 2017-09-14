package com.innercirclesoftware.londair.ui.main.airquality;

import android.support.annotation.NonNull;

import com.innercirclesoftware.londair.data.tfl.CurrentForecast;
import com.innercirclesoftware.londair.base.BaseView;

interface AirQualityView extends BaseView {

    void showForecast(@NonNull CurrentForecast forecast);

    void hideForecast();
}
