package com.innercirclesoftware.londair.main;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.innercirclesoftware.londair.airquality.AirQualityFragment;

class ForecastViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final int TAB_COUNT = 2;
    private SparseArray<AirQualityFragment> registeredFragments = new SparseArray<>();

    ForecastViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public AirQualityFragment getItem(int position) {
        return AirQualityFragment.newInstance(null);
    }

    @Override
    public AirQualityFragment instantiateItem(ViewGroup container, int position) {
        AirQualityFragment fragment = (AirQualityFragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    AirQualityFragment getFragment(int position) {
        return registeredFragments.get(position);
    }
}
