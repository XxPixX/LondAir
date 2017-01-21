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

    @Override
    public void onRefreshSwiped() {

    }

    @Override
    public void onPageSelected(int position) {
        if (view != null) {
            view.selectSpinnerDate(position);
        } else {
            Timber.w("onPageSelected with position %s but the view is null", position);
        }
    }
}
