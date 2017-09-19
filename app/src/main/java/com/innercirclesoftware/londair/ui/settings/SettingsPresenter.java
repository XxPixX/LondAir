package com.innercirclesoftware.londair.ui.settings;

import com.innercirclesoftware.londair.base.BasePresenter;
import com.innercirclesoftware.londair.data.tfl.ForecastBand;

interface SettingsPresenter extends BasePresenter<SettingsView> {

    void onNotificationSwitchChecked(boolean isChecked);

    void onNotificationTimeClicked();

    void onMinimumSeverityClicked();

    void onNotificationTimeSelected(int hour, int minute);

    void onMinimumSeverityChanged(@ForecastBand String newSeverity);
}
