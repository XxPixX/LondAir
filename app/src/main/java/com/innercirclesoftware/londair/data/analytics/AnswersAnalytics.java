package com.innercirclesoftware.londair.data.analytics;

import android.support.annotation.NonNull;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;

import timber.log.Timber;

public class AnswersAnalytics implements Analytics {

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
}
