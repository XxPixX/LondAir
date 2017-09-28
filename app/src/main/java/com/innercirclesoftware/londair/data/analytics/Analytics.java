package com.innercirclesoftware.londair.data.analytics;

import android.support.annotation.NonNull;

public interface Analytics {

    void logScreen(@NonNull Screen screen);

    void logRefresh(@RefreshSource int source);

    void logForecastNavigationMethod(@ForecastNavigationMethod int method);

}
