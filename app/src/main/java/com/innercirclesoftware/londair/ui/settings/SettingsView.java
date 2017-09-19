package com.innercirclesoftware.londair.ui.settings;

import android.support.annotation.NonNull;

import com.innercirclesoftware.londair.base.BaseView;
import com.innercirclesoftware.londair.data.tfl.ForecastBand;

import java.util.Calendar;

interface SettingsView extends BaseView {


    /**
     * @param time when the notification goes off. Is already formatted in the users locale-specific time preference
     */
    void setMorningNotificationTime(@NonNull String time);

    void setNotificationEnabled(boolean enabled);

    void setNotificationSeverity(@ForecastBand String severity);

    void showNotificationTimePicker(@NonNull Calendar currentTime);

    void showMinimumSeverityPicker(@NonNull @ForecastBand String currentSeverity);
}
