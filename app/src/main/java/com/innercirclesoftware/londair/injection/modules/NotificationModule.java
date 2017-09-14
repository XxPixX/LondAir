package com.innercirclesoftware.londair.injection.modules;

import android.app.AlarmManager;
import android.content.Context;
import android.support.annotation.NonNull;

import com.innercirclesoftware.londair.notifications.NotificationScheduler;

import dagger.Module;
import dagger.Provides;

@Module
public class NotificationModule {

    @Provides
    NotificationScheduler providesNotificationScheduler(@NonNull Context context, @NonNull AlarmManager alarmManager) {
        return new NotificationScheduler(context, alarmManager);
    }
}
