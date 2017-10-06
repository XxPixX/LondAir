package com.innercirclesoftware.londair.ui.base;

import android.support.annotation.NonNull;

public interface BasePresenter<T extends BaseView> {

    void attachView(@NonNull T view);

    void detachAllViews();

    void close();

}
