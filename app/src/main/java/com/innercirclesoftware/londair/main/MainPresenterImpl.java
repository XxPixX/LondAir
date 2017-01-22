package com.innercirclesoftware.londair.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.innercirclesoftware.londair.airquality.Air;
import com.innercirclesoftware.londair.airquality.CurrentForecast;
import com.innercirclesoftware.londair.airquality.TflService;
import com.innercirclesoftware.londair.ui.Message;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainPresenterImpl implements MainPresenter {

    @Nullable private MainView view;

    @Inject
    @NonNull
    TflService tflService;

    @NonNull
    private Callback<Air> airCallback = new Callback<Air>() {
        @Override
        public void onResponse(Call<Air> call, Response<Air> response) {
            Air air = response.body();
            CurrentForecast todaysForecast = air.getCurrentForecast().get(0);
            CurrentForecast tomorrowsForecast = air.getCurrentForecast().get(1);

            if (view != null) {
                view.showForecast(0, todaysForecast);
                view.showForecast(1, tomorrowsForecast);
                view.setRefreshing(false);
                view.showMessage(Message.REFRESHED);
            }
        }

        @Override
        public void onFailure(Call<Air> call, Throwable t) {
            if (view != null) {
                view.showMessage(Message.NO_INTERNET);
                view.setRefreshing(false);
            }
        }
    };

    @Inject
    public MainPresenterImpl(@NonNull TflService tflService) {
        this.tflService = tflService;
        this.tflService.getAirQuality().enqueue(airCallback);
    }

    @Override
    public void attachView(@NonNull MainView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void onSpinnerDateItemSelected(int position) {
        if (view != null) {
            view.showForecastFragment(position);
        } else {
            Timber.w("onSpinnerDateItemSelected with position %s but the view is null", position);
        }
    }

    @Override
    public void onRefreshSwiped() {
        tflService.getAirQuality().enqueue(airCallback);
    }

    @Override
    public void onPageSelected(int position) {
        if (view != null) {
            view.selectSpinnerDate(position);
        } else {
            Timber.w("onPageSelected with position %s but the view is null", position);
        }
    }
}
