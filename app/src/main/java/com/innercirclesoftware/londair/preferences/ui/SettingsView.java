package com.innercirclesoftware.londair.preferences.ui;

import android.support.annotation.NonNull;

import com.innercirclesoftware.londair.base.BaseView;

interface SettingsView extends BaseView {


    /**
     * @param time when the notification goes off. Is already formatted in the users locale-specific time preference
     */
    void setMorningNotificationTime(@NonNull String time);
}
