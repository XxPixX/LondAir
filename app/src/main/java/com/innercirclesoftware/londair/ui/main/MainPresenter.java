package com.innercirclesoftware.londair.ui.main;

import com.innercirclesoftware.londair.base.BasePresenter;

public interface MainPresenter extends BasePresenter<MainView> {

    void onSpinnerDateItemSelected(int position);

    void onRefreshSwiped();

    void onPageSelected(int position);
}
