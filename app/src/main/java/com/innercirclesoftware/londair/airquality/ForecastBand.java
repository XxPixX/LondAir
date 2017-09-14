package com.innercirclesoftware.londair.airquality;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.innercirclesoftware.londair.airquality.CurrentForecast.BAND_HIGH;
import static com.innercirclesoftware.londair.airquality.CurrentForecast.BAND_LOW;
import static com.innercirclesoftware.londair.airquality.CurrentForecast.BAND_MODERATE;
import static com.innercirclesoftware.londair.airquality.CurrentForecast.BAND_NONE;

@Retention(RetentionPolicy.SOURCE)
@StringDef(value = {BAND_HIGH, BAND_MODERATE, BAND_LOW, BAND_NONE})
public @interface ForecastBand {
}
