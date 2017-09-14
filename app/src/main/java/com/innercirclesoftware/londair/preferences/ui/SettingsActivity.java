package com.innercirclesoftware.londair.preferences.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.innercirclesoftware.londair.R;
import com.innercirclesoftware.londair.base.BaseActivity;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_settings;
    }

    @Override
    protected boolean showToolbarBackButton() {
        return true;
    }
}
