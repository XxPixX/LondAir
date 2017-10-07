package com.innercirclesoftware.londair.data.notifications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import timber.log.Timber;

public class ForecastExternalStorageMountedReceiver extends NotificationSchedulerReceiver {

    //we're not using the intent in any way so ignore the security warning that we're not checking the intent
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.i("Received external storage mounted action");
        super.onReceive(context, intent);
    }
}
