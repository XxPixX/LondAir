package com.innercirclesoftware.londair.injection.modules;

import android.support.annotation.NonNull;

import com.crashlytics.android.answers.Answers;
import com.innercirclesoftware.londair.data.analytics.Analytics;
import com.innercirclesoftware.londair.data.analytics.AnswersAnalytics;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AnalyticsModule {

    @Provides
    @Singleton
    Answers providesAnswers() {
        return Answers.getInstance();
    }

    @Provides
    @Singleton
    Analytics providesAnalytics(@NonNull Answers answers) {
        return new AnswersAnalytics(answers);
    }
}
