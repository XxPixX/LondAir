package com.innercirclesoftware.londair.ui.main;

import com.innercirclesoftware.londair.ui.base.BaseView;

interface MainView extends BaseView {

    void showForecastFragment(int position);

    void selectSpinnerDate(int position);

    void setRefreshing(boolean refreshing);
}
