package com.innercirclesoftware.londair.main;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;

import com.innercirclesoftware.londair.R;
import com.innercirclesoftware.londair.base.BaseActivity;

public class MainActivity extends BaseActivity implements MainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onSetActionBar(@NonNull ActionBar actionBar) {
        super.onSetActionBar(actionBar);
    }
}
