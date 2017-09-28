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
        }

        answers.logCustom(refreshEvent);
    }
}
