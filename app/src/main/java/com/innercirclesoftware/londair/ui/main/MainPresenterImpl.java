package com.innercirclesoftware.londair.ui.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.innercirclesoftware.londair.R;
import com.innercirclesoftware.londair.data.tfl.Air;
import com.innercirclesoftware.londair.data.tfl.CurrentForecast;
import com.innercirclesoftware.londair.data.tfl.TflService;
import com.innercirclesoftware.londair.ui.Message;
import com.innercirclesoftware.londair.ui.main.airquality.AirQualityView;
import com.innercirclesoftware.londair.utils.RxUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

class MainPresenterImpl implements MainPresenter {

    @Nullable private MainView view;

    @Nullable private AirQualityView todaysView;
    @Nullable private AirQualityView tomorrowsView;

    @Nullable private CurrentForecast todaysForecast;
    @Nullable private CurrentForecast tomorrowsForecast;

    @NonNull private final TflService tflService;
    @Nullable private Disposable forecastFetcher;

    MainPresenterImpl(@NonNull TflService tflService) {
        this.tflService = tflService;
        fetchForecast();
    }

    @Override
    public void attachView(@NonNull MainView view) {
        Timber.v("Attaching view %s to %s", view, this);
        this.view = view;
        view.setRefreshing(RxUtils.isRunning(forecastFetcher));
        view.selectSpinnerDate(0);
        view.showForecastFragment(0);
    }

    @Override
    public void attachTodaysView(@NonNull AirQualityView todaysView) {
        Timber.v("Attaching todays view %s to %s", todaysView, this);
        this.todaysView = todaysView;
        if (todaysForecast != null) this.todaysView.onShowForecastRequested(todaysForecast);
    }

    @Override
    public void detachTodaysView() {
        Timber.v("Detaching todays view %s in %s", todaysView, this);
        this.todaysView = null;
    }

    @Override
    public void attachTomorrowsView(@NonNull AirQualityView tomorrowsView) {
        Timber.v("Attaching tomorrows view %s to %s", tomorrowsView, this);
        this.tomorrowsView = tomorrowsView;
        if (tomorrowsForecast != null) tomorrowsView.onShowForecastRequested(tomorrowsForecast);
    }

    @Override
    public void detachTomorrowsView() {
        Timber.v("Detaching tomorrows view %s in %s", tomorrowsView, this);
        this.tomorrowsView = null;
    }

    @Override
    public void detachAllViews() {
        Timber.v("Detaching all views in %s", this);
        this.view = null;
        this.todaysView = null;
        this.tomorrowsView = null;
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
                .map(Air::getCurrentForecast)
                .subscribe(forecasts -> {
                    this.todaysForecast = forecasts.get(0);
                    this.tomorrowsForecast = forecasts.get(1);

                    //today
                    if (todaysForecast != null) {
                        if (todaysView != null) todaysView.onShowForecastRequested(todaysForecast);
                        else Timber.i("Fetched todays forecast but the today view hasn't been attached yet");
                    } else {
                        Timber.w("Fetched forecasts %s but today is null", forecasts);
                    }

                    //tomorrow
                    if (tomorrowsForecast != null) {
                        if (tomorrowsView != null) tomorrowsView.onShowForecastRequested(tomorrowsForecast);
                        else Timber.i("Fetched tomorrows forecast but the tomorrow view hasn't been attached yet");
                    } else {
                        Timber.w("Fetched forecasts %s but tomorrow is null", forecasts);
                    }

                    if (view != null) {
                        view.setRefreshing(false);
                        view.showMessage(Message.REFRESHED);
                    }
                });
    }
}
