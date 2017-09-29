package com.innercirclesoftware.londair.ui.settings;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.text.format.DateFormat;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.innercirclesoftware.londair.R;
import com.innercirclesoftware.londair.base.BaseActivity;
import com.innercirclesoftware.londair.data.analytics.Analytics;
import com.innercirclesoftware.londair.data.analytics.Screen;
import com.innercirclesoftware.londair.data.tfl.CurrentForecast;
import com.innercirclesoftware.londair.data.tfl.ForecastBand;
import com.innercirclesoftware.londair.injection.components.ApplicationComponent;
import com.innercirclesoftware.londair.utils.CalendarUtils;
import com.innercirclesoftware.londair.utils.ViewUtils;

import java.util.Arrays;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import timber.log.Timber;

public class SettingsActivity extends BaseActivity implements SettingsView {

    private static final Screen SETTINGS_SCREEN = new Screen.Builder()
            .name("Settings")
            .build();

    @Inject SettingsPresenter presenter;
    @Inject Analytics analytics;

    @BindView(R.id.notification_switch) SwitchCompat notificationSwitch;

    @BindView(R.id.notification_time) ViewGroup notificationTime;
    @BindView(R.id.notification_time_summary) TextView notificationTimeSummary;

    @BindView(R.id.notification_min_severity) ViewGroup notificationMinSeverity;
    @BindView(R.id.notification_min_severity_summary) TextView notificationMinSeveritySummary;

    @BindString(R.string.settings_notification_severity_high) String severityHigh;
    @BindString(R.string.settings_notification_severity_moderate) String severityModerate;
    @BindString(R.string.settings_notification_severity_low) String severityLow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        analytics.logScreen(SETTINGS_SCREEN);
        registerPresenter(presenter);
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
    public void setNotificationTime(@NonNull String time) {
        notificationTimeSummary.setText(getString(R.string.settings_notification_time_summary, time));
    }

    @Override
    public void setNotificationEnabled(boolean enabled) {
        notificationSwitch.setChecked(enabled);
        ViewUtils.setEnabled(notificationTime, enabled);
        ViewUtils.setEnabled(notificationMinSeverity, enabled);
    }

    @Override
    public void setNotificationSeverity(@ForecastBand String severity) {
        switch (severity) {
            case CurrentForecast.BAND_HIGH:
                notificationMinSeveritySummary.setText(severityHigh);
                break;
            case CurrentForecast.BAND_MODERATE:
                notificationMinSeveritySummary.setText(severityModerate);
                break;
            case CurrentForecast.BAND_LOW:
                notificationMinSeveritySummary.setText(severityLow);
                break;
            default:
                Timber.w("Unknown notification severity %s", severity);
                break;
        }
    }

    @Override
    public void showNotificationTimePicker(@NonNull Calendar currentTime) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (timePicker, hour, minute) -> {
                    presenter.onNotificationTimeSelected(hour, minute);
                    Calendar newNotificationTime = CalendarUtils.getCalendarWithHourMinute(hour, minute);
                    analytics.logNotificationTimeChanged(newNotificationTime);
                },
                currentTime.get(Calendar.HOUR_OF_DAY),
                currentTime.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this)
        );

        timePickerDialog.show();
    }

    @Override
    public void showMinimumSeverityPicker(@NonNull @ForecastBand String currentSeverity) {
        CharSequence[] severities = getResources().getTextArray(R.array.severities);


        new MaterialDialog.Builder(this)
                .title(R.string.dialog_notification_min_severity_title)
                .content(R.string.dialog_notification_min_severity_content)
                .negativeText(R.string.btn_cancel)
                .items(severities)
                .itemsCallbackSingleChoice(Arrays.asList(severities).indexOf(currentSeverity), (dialog, itemView, which, text) -> {
                    //can't use a switch statement as it needs constants...
                    String band = null;
                    if (text.equals(severityHigh)) {
                        band = CurrentForecast.BAND_HIGH;
                    }

                    if (text.equals(severityModerate)) {
                        band = CurrentForecast.BAND_HIGH;
                    }

                    if (text.equals(severityLow)) {
                        band = CurrentForecast.BAND_LOW;
                    }

                    if (band != null) {
                        analytics.logNotificationMinSeverityChanged(band);
                        presenter.onMinimumSeverityChanged(band);
                        return true;
                    }

                    Timber.w("Unknown severity selected with text %s, index %s", text, which);
                    return false;

                })
                .show();
    }

    @OnCheckedChanged(R.id.notification_switch)
    protected void onNotificationSwitchChecked(boolean isChecked) {
        analytics.logNotificationEnabled(isChecked);
        presenter.onNotificationSwitchChecked(isChecked);
    }

    @OnClick(R.id.notification_time)
    protected void onNotificationTimeClicked() {
        presenter.onNotificationTimeClicked();
    }

    @OnClick(R.id.notification_min_severity)
    protected void onMinimumSeverityClicked() {
        presenter.onMinimumSeverityClicked();
    }
}
