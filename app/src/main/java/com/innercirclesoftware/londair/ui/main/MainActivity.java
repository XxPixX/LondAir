package com.innercirclesoftware.londair.ui.main;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.innercirclesoftware.londair.R;
import com.innercirclesoftware.londair.base.BaseActivity;
import com.innercirclesoftware.londair.injection.components.ApplicationComponent;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainView {

    @Inject MainPresenter presenter;
    @BindView(R.id.date_spinner) AppCompatSpinner dateSpinner;
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;
    private MainComponent mainComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDateSpinner();
        initViewPager();
        initSwipeRefreshLayout();
        registerPresenter(presenter);
    }

    @Override
    protected void inject(@NonNull ApplicationComponent applicationComponent) {
        mainComponent = DaggerMainComponent.builder().applicationComponent(applicationComponent).build();
        mainComponent.inject(this);
    }

    private void initDateSpinner() {
        ToolbarSpinnerAdapter spinnerAdapter = new ToolbarSpinnerAdapter(getApplicationContext());
        dateSpinner.setAdapter(spinnerAdapter);
        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.onSpinnerDateItemSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initViewPager() {
        ForecastViewPagerAdapter viewPagerAdapter = new ForecastViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                presenter.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Fixes issue where swiping horizontally interferes with the SwipeRefreshLayout
                //http://stackoverflow.com/questions/25978462/swiperefreshlayout-viewpager-limit-horizontal-scroll-only
                swipeRefreshLayout.setEnabled(state == ViewPager.SCROLL_STATE_IDLE);
            }
        });
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.onRefreshSwiped());
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh) {
            swipeRefreshLayout.setRefreshing(true);
            presenter.onRefreshSwiped();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSetActionBar(@NonNull ActionBar actionBar) {
        super.onSetActionBar(actionBar);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public void showForecastFragment(int position) {
        viewPager.setCurrentItem(position, true);
    }

    @Override
    public void selectSpinnerDate(int position) {
        dateSpinner.setSelection(position, true);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        swipeRefreshLayout.setRefreshing(refreshing);
    }

    public MainComponent getMainComponent() {
        return mainComponent;
    }
}
