package com.innercirclesoftware.londair.data.analytics;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.innercirclesoftware.londair.data.analytics.Navigation.TOOLBAR_SPINNER;
import static com.innercirclesoftware.londair.data.analytics.Navigation.VIEW_PAGER;

@Retention(RetentionPolicy.SOURCE)
@IntDef(value = {VIEW_PAGER, TOOLBAR_SPINNER})
@interface ForecastNavigationMethod {
}
