package com.innercirclesoftware.londair.data.notifications;

import android.content.Context;
import android.content.Intent;

import timber.log.Timber;

public class ForecastBootReceiver extends NotificationSchedulerReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.i("Received boot action");
        super.onReceive(context, intent);
    }
}
