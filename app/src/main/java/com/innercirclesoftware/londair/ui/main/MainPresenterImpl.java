package com.innercirclesoftware.londair.ui.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.innercirclesoftware.londair.R;
import com.innercirclesoftware.londair.data.tfl.CurrentForecast;
import com.innercirclesoftware.londair.data.tfl.TflService;
import com.innercirclesoftware.londair.ui.Message;
import com.innercirclesoftware.londair.utils.RxUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class MainPresenterImpl implements MainPresenter {

    @Nullable private MainView view;

    @NonNull private TflService tflService;
    @Nullable private Disposable forecastFetcher;

    @Inject
    public MainPresenterImpl(@NonNull TflService tflService) {
        this.tflService = tflService;
        fetchForecast();
    }

    @Override
    public void attachView(@NonNull MainView view) {
        Timber.v("Attaching view %s to %s", view, this);
        this.view = view;
        view.setRefreshing(RxUtils.isRunning(forecastFetcher));
    }

    @Override
    public void detachAllViews() {
        Timber.v("Detaching all views in %s", this);
        this.view = null;
    }

    @Override
    public void close() {
        Timber.v("Closing %s", this);
        RxUtils.dispose(forecastFetcher);
        forecastFetcher = null;
    }

    @Override
    public void onSpinnerDateItemSelected(int position) {
        if (view != null) view.showForecastFragment(position);
        else Timber.w("onSpinnerDateItemSelected with position %s but the view is null", position);
    }

    @Override
    public void onRefreshSwiped() {
        if (!RxUtils.isRunning(forecastFetcher)) fetchForecast();
        else Timber.i("Requested to refresh the forecast but already in the process of refreshing it: ignoring");
    }

    @Override
    public void onPageSelected(int position) {
        if (view != null) view.selectSpinnerDate(position);
        else Timber.w("onPageSelected with position %s but the view is null", position);
    }

    private void fetchForecast() {
        if (RxUtils.isRunning(forecastFetcher)) {
            Timber.w("Requested to fetch the forecast but already in the process of fetching");
            return;
        }

        Timber.i("Fetching forecast");
        this.forecastFetcher = this.tflService.getAirQuality()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    Timber.w(throwable, "Failed to get the forecast");
                    if (view != null) {
                        view.showMessage(new Message(R.string.error, throwable.getLocalizedMessage()));
                        view.setRefreshing(false);
                    }
                })
                .subscribe(air -> {
                    CurrentForecast todaysForecast = air.getCurrentForecast().get(0);
                    CurrentForecast tomorrowsForecast = air.getCurrentForecast().get(1);

                    if (view != null) {
                        view.showForecast(0, todaysForecast);
                        view.showForecast(1, tomorrowsForecast);
                        view.setRefreshing(false);
                        view.showMessage(Message.REFRESHED);
                    }
                });
    }
}
