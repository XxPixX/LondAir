package com.innercirclesoftware.londair.airquality.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.innercirclesoftware.londair.airquality.CurrentForecast;

import timber.log.Timber;

public class AirQualityPresenterImpl implements AirQualityPresenter {

    @Nullable private AirQualityView view;
    @Nullable private CurrentForecast forecast;

    @Override
    public void attachView(@NonNull AirQualityView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void onForecastRefreshed(@Nullable CurrentForecast forecast) {
        this.forecast = forecast;

        if (view != null) {
            if (forecast != null) view.showForecast(forecast);
            else view.hideForecast();
        } else {
            Timber.w("onForecastRefreshed but the view is null");
        }
    }
}
