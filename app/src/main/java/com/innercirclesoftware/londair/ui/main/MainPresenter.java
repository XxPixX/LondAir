package com.innercirclesoftware.londair.ui.main;

import android.support.annotation.NonNull;

import com.innercirclesoftware.londair.base.BasePresenter;
import com.innercirclesoftware.londair.ui.main.airquality.AirQualityView;

public interface MainPresenter extends BasePresenter<MainView> {

    void attachTodaysView(@NonNull AirQualityView todaysView);

    void attachTomorrowsView(@NonNull AirQualityView tomorrowsView);

    void onSpinnerDateItemSelected(int position);

    void onRefreshSwiped();

    void onPageSelected(int position);
}
