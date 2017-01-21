package com.innercirclesoftware.londair.main;

import android.support.annotation.NonNull;

import com.innercirclesoftware.londair.airquality.CurrentForecast;
import com.innercirclesoftware.londair.base.BaseView;

public interface MainView extends BaseView {

    void showForecastFragment(int position);

    void selectSpinnerDate(int position);

    void showForecast(int position, @NonNull CurrentForecast forecast);

    void setRefreshing(boolean refreshing);
}
