package com.innercirclesoftware.londair.ui.main;

import android.support.annotation.NonNull;

import com.innercirclesoftware.londair.data.tfl.CurrentForecast;
import com.innercirclesoftware.londair.base.BaseView;

public interface MainView extends BaseView {

    void showForecastFragment(int position);

    void selectSpinnerDate(int position);

    void showForecast(int position, @NonNull CurrentForecast forecast);

    void setRefreshing(boolean refreshing);
}
