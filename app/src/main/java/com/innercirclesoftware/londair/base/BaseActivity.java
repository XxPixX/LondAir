package com.innercirclesoftware.londair.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.innercirclesoftware.londair.LondAir;
import com.innercirclesoftware.londair.R;
import com.innercirclesoftware.londair.injection.components.ApplicationComponent;
import com.innercirclesoftware.londair.ui.settings.SettingsActivity;
import com.innercirclesoftware.londair.ui.Message;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements Layoutable, BaseView {

    @BindView(R.id.toolbar) @Nullable Toolbar toolbar;
    @BindView(R.id.container) @Nullable CoordinatorLayout coordinatorLayout;

    @Nullable private BasePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        inject(getApplicationComponent());
        ButterKnife.bind(this);
        initToolbar();
    }

    @Override
    protected void onStop() {
        if (presenter != null) presenter.detachAllViews();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) presenter.close();
        super.onDestroy();
    }

    protected void registerPresenter(@NonNull BasePresenter presenter) {
        this.presenter = presenter;
        //noinspection unchecked
        presenter.attachView(this);
    }

    protected abstract void inject(@NonNull ApplicationComponent applicationComponent);

    protected void initToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                if (showToolbarBackButton()) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setDisplayShowHomeEnabled(true);
                }
                onSetActionBar(actionBar);
            }
        }
    }

    /**
     * Override and return true to easily show the back button in the toolbar
     * Default value: false
     *
     * @return true if the app should display the back arrow in the action bar, false does nothing
     */
    protected boolean showToolbarBackButton() {
        return false;
    }

    @SuppressWarnings("UnusedParameters")
    protected void onSetActionBar(@NonNull ActionBar actionBar) {

    }

    @NonNull
    protected ApplicationComponent getApplicationComponent() {
        return getApp().getApplicationComponent();
    }

    @NonNull
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                launchSettingsActivity();
                return true;
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void launchSettingsActivity() {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}
