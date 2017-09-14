package com.innercirclesoftware.londair.ui.main.airquality;

import android.support.annotation.Nullable;

import com.innercirclesoftware.londair.data.tfl.CurrentForecast;
import com.innercirclesoftware.londair.base.BasePresenter;

public interface AirQualityPresenter extends BasePresenter<AirQualityView> {

    void onForecastRefreshed(@Nullable CurrentForecast forecast);

}
