package com.innercirclesoftware.londair.data.tfl;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef(value = {CurrentForecast.BAND_HIGH, CurrentForecast.BAND_MODERATE, CurrentForecast.BAND_LOW, CurrentForecast.BAND_NONE})
public @interface ForecastBand {
}
