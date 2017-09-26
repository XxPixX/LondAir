package com.innercirclesoftware.londair.ui.main;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.innercirclesoftware.londair.ui.main.airquality.AirQualityFragment;

public class ForecastViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final int TAB_COUNT = 2;

    public static final int TAB_POSITION_TODAY = 0;
    public static final int TAB_POSITION_TOMORROW = 1;

    ForecastViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public AirQualityFragment getItem(int position) {
        return AirQualityFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

}
