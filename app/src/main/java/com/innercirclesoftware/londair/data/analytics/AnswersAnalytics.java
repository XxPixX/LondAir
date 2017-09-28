package com.innercirclesoftware.londair.data.analytics;

import android.support.annotation.NonNull;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;

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

    @NonNull private final Answers answers;

    public AnswersAnalytics(@NonNull Answers answers) {
        this.answers = answers;
    }

    @Override
    public void logScreen(@NonNull Screen screen) {
        Timber.d("logScreen: %s", screen);

        ContentViewEvent event = new ContentViewEvent();
        if (screen.getName() != null) event.putContentName(screen.getName());
        if (screen.getId() != null) event.putContentId(screen.getId());
        if (screen.getType() != null) event.putContentType(screen.getType());

        answers.logContentView(event);
    }

    @Override
    public void logRefresh(@RefreshSource int source) {
        Timber.d("logRefresh: %s", source);

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
        Timber.d("logForecastNavigationMethod: %s", method);

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
}
