package com.innercirclesoftware.londair.airquality.ui;

import android.support.annotation.Nullable;

import com.innercirclesoftware.londair.airquality.CurrentForecast;
import com.innercirclesoftware.londair.base.BasePresenter;

public interface AirQualityPresenter extends BasePresenter<AirQualityView> {

    void onForecastRefreshed(@Nullable CurrentForecast forecast);

}
