package com.innercirclesoftware.londair.ui.main;

import android.support.annotation.NonNull;

import com.innercirclesoftware.londair.ui.base.BasePresenter;
import com.innercirclesoftware.londair.ui.main.airquality.AirQualityView;

public interface MainPresenter extends BasePresenter<MainView> {

    void attachTodaysView(@NonNull AirQualityView todaysView);

    void detachTodaysView();

    void attachTomorrowsView(@NonNull AirQualityView tomorrowsView);

    void detachTomorrowsView();

    void onSpinnerDateItemSelected(int position);

    void onRefreshSwiped();

    void onPageSelected(int position);

}
