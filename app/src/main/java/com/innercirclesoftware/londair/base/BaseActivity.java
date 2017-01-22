package com.innercirclesoftware.londair.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.innercirclesoftware.londair.LondAir;
import com.innercirclesoftware.londair.R;
import com.innercirclesoftware.londair.injection.components.ApplicationComponent;
import com.innercirclesoftware.londair.main.MainView;
import com.innercirclesoftware.londair.ui.Message;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public abstract class BaseActivity extends AppCompatActivity implements Layoutable, BaseView {

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

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
        switch (message) {
            case REFRESHED:
                showSnackbar(R.string.refreshed);
                break;
            case NO_INTERNET:
                showSnackbar(R.string.no_internet_connection);
                break;
            default:
                showSnackbar(message.toString());
                Timber.w("Unknown message %s", message);
        }
    }

    public void showSnackbar(@StringRes int stringRes) {
        showSnackbar(getString(stringRes));
    }

    public abstract void showSnackbar(String message);
}
