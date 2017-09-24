package com.innercirclesoftware.londair.ui.main.airquality;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.innercirclesoftware.londair.data.tfl.CurrentForecast;

import timber.log.Timber;

class AirQualityPresenterImpl implements AirQualityPresenter {

    @Nullable private AirQualityView view;
    @Nullable private CurrentForecast forecast;

    private boolean detailedPollutantSummaries = false;

    @Override
    public void attachView(@NonNull AirQualityView view) {
        this.view = view;
        if (forecast != null) view.showForecast(forecast);
        view.showDetailedPollutantSummaries(detailedPollutantSummaries);
    }

    @Override
    public void detachAllViews() {
        this.view = null;
    }

    @Override
    public void close() {

    }

    @Override
    public void onShowForecastRequested(@NonNull CurrentForecast forecast) {
        this.forecast = forecast;

        if (view != null) view.showForecast(forecast);
        else Timber.w("onShowForecastRequested but the view is null");
    }

    @Override
    public void onExpandClicked() {
        this.detailedPollutantSummaries = !detailedPollutantSummaries;

        if (view != null) view.showDetailedPollutantSummaries(detailedPollutantSummaries);
        else Timber.w("onExpandClicked but the view is null");
    }
}
