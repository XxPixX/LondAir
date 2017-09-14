package com.innercirclesoftware.londair.injection.modules;

import android.app.AlarmManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidModule {

    @NonNull private final Context context;

    public AndroidModule(@NonNull Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return context;
    }

    @Provides
    @Singleton
    NotificationManagerCompat providesNotificationManagerCompat(@NonNull Context context) {
        return NotificationManagerCompat.from(context);
    }

    @Provides
    @Singleton
    AlarmManager providesAlarmManager(@NonNull Context context) {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }
}
