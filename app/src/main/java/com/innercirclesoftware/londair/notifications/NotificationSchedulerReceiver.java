package com.innercirclesoftware.londair.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.innercirclesoftware.londair.LondAir;

import javax.inject.Inject;

public class NotificationSchedulerReceiver extends BroadcastReceiver {

    @Inject NotificationScheduler scheduler = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (scheduler == null) LondAir.getApplicationComponent(context).inject(this);
    }
}
