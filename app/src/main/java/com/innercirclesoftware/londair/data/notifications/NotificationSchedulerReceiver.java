package com.innercirclesoftware.londair.data.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.innercirclesoftware.londair.Londair;

import javax.inject.Inject;

public class NotificationSchedulerReceiver extends BroadcastReceiver {

    @Inject NotificationScheduler scheduler = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (scheduler == null) Londair.getApplicationComponent(context).inject(this);
    }
}
