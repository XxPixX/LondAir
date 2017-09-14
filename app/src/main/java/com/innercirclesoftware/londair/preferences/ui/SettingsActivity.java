package com.innercirclesoftware.londair.preferences.ui;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.innercirclesoftware.londair.R;
import com.innercirclesoftware.londair.base.BaseActivity;
import com.innercirclesoftware.londair.injection.components.ApplicationComponent;

import javax.inject.Inject;

import butterknife.BindView;

public class SettingsActivity extends BaseActivity implements SettingsView {

    @Inject SettingsPresenter presenter;

    @BindView(R.id.notification_time_summary) TextView notificationTimeSummary;

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    @Override
    protected void onStop() {
        presenter.detachView();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        presenter.close();
        super.onDestroy();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_settings;
    }

    @Override
    protected void inject(@NonNull ApplicationComponent applicationComponent) {
        DaggerSettingsComponent.builder().applicationComponent(applicationComponent).build().inject(this);
    }

    @Override
    protected boolean showToolbarBackButton() {
        return true;
    }

    @Override
    public void setMorningNotificationTime(@NonNull String time) {
        notificationTimeSummary.setText(getString(R.string.settings_notification_time_summary, time));
    }
}
