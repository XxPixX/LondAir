package com.innercirclesoftware.londair.data.analytics;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;
import com.innercirclesoftware.londair.data.tfl.ForecastBand;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import timber.log.Timber;

public class AnswersAnalytics implements Analytics {

    private static final String EVENT_NAME_REFRESH = "Refresh";
    private static final String KEY_REFRESH = "Source";
    private static final String VALUE_PULL_TO_REFRESH = "Pull to refresh";
    private static final String VALUE_TOOLBAR = "Toolbar";

    private static final String EVENT_NAME_FORECAST_NAVIGATION = "Forecast navigation";
    private static final String KEY_NAVIGATION_METHOD = "Method";
    private static final String VALUE_TOOLBAR_SPINNER = "Toolbar spinner";
    private static final String VALUE_VIEW_PAGER = "ViewPager swiped";

    private static final String EVENT_NAME_NOTIF_TIME_CHANGED = "Notification time";
    private static final String KEY_NOTIF_TIME = "Time";

    private static final String EVENT_NAME_NOTIF_ENABLED = "Notification enabled";
    private static final String KEY_NOTIF_ENABLED = "Enabled";

    private static final String EVENT_NAME_MIN_SEVERITY_CHANGED = "Minimum notification severity";
    private static final String KEY_NEW_MINIMUM_SEVERITY = "Severity";

    @NonNull private final Answers answers;

    public AnswersAnalytics(@NonNull Answers answers) {
        this.answers = answers;
    }

    @Override
    public void logScreen(@NonNull Screen screen) {
        Timber.v("logScreen: %s", screen);

        ContentViewEvent event = new ContentViewEvent();
        if (screen.getName() != null) event.putContentName(screen.getName());
        if (screen.getId() != null) event.putContentId(screen.getId());
        if (screen.getType() != null) event.putContentType(screen.getType());

        answers.logContentView(event);
    }

    @Override
    public void logRefresh(@RefreshSource int source) {
        Timber.v("logRefresh: %s", source);

        CustomEvent refreshEvent = new CustomEvent(EVENT_NAME_REFRESH);
        switch (source) {
            case Refresh.PULL_TO_REFRESH:
                refreshEvent.putCustomAttribute(KEY_REFRESH, VALUE_PULL_TO_REFRESH);
                break;
            case Refresh.TOOLBAR:
                refreshEvent.putCustomAttribute(KEY_REFRESH, VALUE_TOOLBAR);
                break;
            default:
                Timber.w("Unknown refresh source %s", source);
                break;
        }

        answers.logCustom(refreshEvent);
    }

    @Override
    public void logForecastNavigationMethod(@ForecastNavigationMethod int method) {
        Timber.v("logForecastNavigationMethod: %s", method);

        CustomEvent forecastNavigationEvent = new CustomEvent(EVENT_NAME_FORECAST_NAVIGATION);
        switch (method) {
            case Navigation.TOOLBAR_SPINNER:
                forecastNavigationEvent.putCustomAttribute(KEY_NAVIGATION_METHOD, VALUE_TOOLBAR_SPINNER);
                break;
            case Navigation.VIEW_PAGER:
                forecastNavigationEvent.putCustomAttribute(KEY_NAVIGATION_METHOD, VALUE_VIEW_PAGER);
                break;
            default:
                Timber.w("Unknown forecast navigation method %s", method);
                break;
        }

        answers.logCustom(forecastNavigationEvent);
    }

    @Override
    public void logNotificationTimeChanged(@NonNull Calendar newTime) {
        Timber.v("logNotificationTimeChanged: %s", newTime.getTime().toString());

        @SuppressLint("SimpleDateFormat") String formattedTime = new SimpleDateFormat("HH:mm").format(newTime.getTime());
        CustomEvent notificationTimeChangedEvent = new CustomEvent(EVENT_NAME_NOTIF_TIME_CHANGED);
        notificationTimeChangedEvent.putCustomAttribute(KEY_NOTIF_TIME, formattedTime);
        answers.logCustom(notificationTimeChangedEvent);
    }

    @Override
    public void logNotificationEnabled(boolean enabled) {
        Timber.v("logNotificationEnabled: %s", enabled);

        CustomEvent notificationEnabledEvent = new CustomEvent(EVENT_NAME_NOTIF_ENABLED);
        notificationEnabledEvent.putCustomAttribute(KEY_NOTIF_ENABLED, String.valueOf(enabled));
        answers.logCustom(notificationEnabledEvent);
    }

    @Override
    public void logNotificationMinSeverityChanged(@ForecastBand String newSeverity) {
        Timber.v("logNotificationMinSeverityChanged: %s", newSeverity);

        CustomEvent severityChangedEvent = new CustomEvent(EVENT_NAME_MIN_SEVERITY_CHANGED);
        severityChangedEvent.putCustomAttribute(KEY_NEW_MINIMUM_SEVERITY, newSeverity);
        answers.logCustom(severityChangedEvent);
    }
}
