package com.innercirclesoftware.londair.data.analytics;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.innercirclesoftware.londair.data.analytics.Refresh.PULL_TO_REFRESH;
import static com.innercirclesoftware.londair.data.analytics.Refresh.TOOLBAR;

@Retention(RetentionPolicy.SOURCE)
@IntDef(value = {PULL_TO_REFRESH, TOOLBAR})
@interface RefreshSource {
}
