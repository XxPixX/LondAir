package com.innercirclesoftware.londair.base;

import android.support.annotation.NonNull;

public interface BasePresenter<T extends BaseView> {

    void attachView(@NonNull T view);

    void detachAllViews();

    void close();

}
