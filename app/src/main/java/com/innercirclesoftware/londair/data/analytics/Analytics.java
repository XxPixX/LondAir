package com.innercirclesoftware.londair.data.analytics;

import android.support.annotation.NonNull;

import com.innercirclesoftware.londair.data.tfl.ForecastBand;

import java.util.Calendar;

public interface Analytics {

    void logScreen(@NonNull Screen screen);

    void logRefresh(@RefreshSource int source);

    void logForecastNavigationMethod(@ForecastNavigationMethod int method);

    void logNotificationTimeChanged(@NonNull Calendar newTime);

    void logNotificationEnabled(boolean enabled);

    void logNotificationMinSeverityChanged(@ForecastBand String newSeverity);

}
