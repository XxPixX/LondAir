package com.innercirclesoftware.londair.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
}
