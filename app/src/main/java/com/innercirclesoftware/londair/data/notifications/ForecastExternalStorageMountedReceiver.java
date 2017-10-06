package com.innercirclesoftware.londair.data.notifications;

import android.content.Context;
import android.content.Intent;

import timber.log.Timber;

public class ForecastExternalStorageMountedReceiver extends NotificationSchedulerReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.i("Received external storage mounted action");
        super.onReceive(context, intent);
    }
}
