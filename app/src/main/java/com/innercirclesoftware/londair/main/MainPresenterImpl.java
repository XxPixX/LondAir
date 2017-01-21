package com.innercirclesoftware.londair.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import timber.log.Timber;

public class MainPresenterImpl implements MainPresenter {

    @Nullable private MainView view;

    @Override
    public void attachView(@NonNull MainView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void onSpinnerDateItemSelected(int position) {
        if (view != null) {
            view.showForecastFragment(position);
        } else {
            Timber.w("onSpinnerDateItemSelected with position %s but the view is null", position);
        }
    }
}
