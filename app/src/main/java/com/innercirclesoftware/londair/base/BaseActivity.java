package com.innercirclesoftware.londair.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.innercirclesoftware.londair.LondAir;
import com.innercirclesoftware.londair.R;
import com.innercirclesoftware.londair.injection.components.ApplicationComponent;
import com.innercirclesoftware.londair.ui.Message;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements Layoutable, BaseView {

    @BindView(R.id.toolbar) @Nullable Toolbar toolbar;
    @BindView(R.id.container) @Nullable CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        setToolbar();
    }

    protected void setToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) onSetActionBar(actionBar);
        }
    }

    @SuppressWarnings("UnusedParameters")
    protected void onSetActionBar(@NonNull ActionBar actionBar) {

    }

    protected ApplicationComponent getComponent() {
        return getApp().getApplicationComponent();
    }

    protected LondAir getApp() {
        return (LondAir) getApplication();
    }

    @Override
    public void showMessage(@NonNull Message message) {
        showSnackbar(getString(message.getStringRes(), message.getArguments()));
    }

    public void showSnackbar(@NonNull String message) {
        Snackbar.make(getSnackbarTargetView(), message, Snackbar.LENGTH_LONG).show();
    }

    @NonNull
    private View getSnackbarTargetView() {
        if (coordinatorLayout != null) return coordinatorLayout;
        return getDecorView();
    }

    @NonNull
    private View getDecorView() {
        return getWindow().getDecorView();
    }
}
