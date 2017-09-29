package com.innercirclesoftware.londair.data.analytics;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef(value = {Preference.NOTIFICATION_HOUR, Preference.NOTIFICATION_MINUTE, Preference.NOTIFICATION_ENABLED})
@interface PreferenceChange {
}
