package com.innercirclesoftware.londair.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.innercirclesoftware.londair.airquality.AirQualityFragment;

class ForecastViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final int TAB_COUNT = 2;

    ForecastViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return AirQualityFragment.newInstance(null);
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
