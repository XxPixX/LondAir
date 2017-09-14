package com.innercirclesoftware.londair.ui;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.innercirclesoftware.londair.R;

public class Message {

    public static final Message REFRESHED = new Message(R.string.refreshed);

    @StringRes private final int stringRes;
    @NonNull private final String[] arguments;

    public Message(@StringRes int stringRes, @NonNull String... arguments) {
        this.stringRes = stringRes;
        this.arguments = arguments;
    }

    @StringRes
    public int getStringRes() {
        return stringRes;
    }

    @NonNull
    public String[] getArguments() {
        return arguments;
    }
}
