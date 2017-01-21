package com.innercirclesoftware.londair.main;

import com.innercirclesoftware.londair.base.BasePresenter;

public interface MainPresenter extends BasePresenter<MainView> {

    void onSpinnerDateItemSelected(int position);

    void onRefreshSwiped();
}
