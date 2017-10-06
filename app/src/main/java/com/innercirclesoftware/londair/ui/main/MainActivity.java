package com.innercirclesoftware.londair.ui.main;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuItem;

import com.innercirclesoftware.londair.R;
import com.innercirclesoftware.londair.ui.base.BaseActivity;
import com.innercirclesoftware.londair.data.analytics.Analytics;
import com.innercirclesoftware.londair.data.analytics.Navigation;
import com.innercirclesoftware.londair.data.analytics.Refresh;
import com.innercirclesoftware.londair.data.analytics.Screen;
import com.innercirclesoftware.londair.injection.components.ApplicationComponent;
import com.innercirclesoftware.londair.utils.RxUtils;
import com.jakewharton.rxbinding2.support.v4.view.RxViewPager;
import com.jakewharton.rxbinding2.widget.RxAdapterView;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends BaseActivity implements MainView {

    @Inject MainPresenter presenter;
    @Inject Analytics analytics;

    @BindView(R.id.date_spinner) AppCompatSpinner dateSpinner;
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;

    private MainComponent mainComponent;
    private PublishSubject<Integer> navigationSourcePublisher;

    @Nullable private Disposable spinnerItemSelections;
    @Nullable private Disposable viewPagerSelections;
    @Nullable private Disposable analyticsNavigationReporter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDateSpinner();
        initViewPager();
        initPageChangeListeners();
        initSwipeRefreshLayout();
        registerPresenter(presenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxUtils.dispose(spinnerItemSelections, viewPagerSelections, analyticsNavigationReporter);
        spinnerItemSelections = null;
        viewPagerSelections = null;
        analyticsNavigationReporter = null;
        navigationSourcePublisher = null;
    }

    /**
     * Initialise the ViewPager page change listeners and spinner item selection listeners.
     * <p>
     * The {@link MainActivity#analyticsNavigationReporter} reports to analytics how the user acted on the UI, whether by swiping pages, or by using the toolbar
     */
    private void initPageChangeListeners() {
        navigationSourcePublisher = PublishSubject.create();

        spinnerItemSelections = RxAdapterView.itemSelections(dateSpinner)
                .doOnNext(integer -> navigationSourcePublisher.onNext(Navigation.TOOLBAR_SPINNER)) //notify our publisher that the spinner was acted upon, but we don't know if by the user or by code
                .doOnNext(position -> presenter.onSpinnerDateItemSelected(position))
                .subscribe();

        viewPagerSelections = RxViewPager.pageSelections(viewPager)
                .doOnNext(integer -> navigationSourcePublisher.onNext(Navigation.VIEW_PAGER))//notify our publisher that the viewpager was acted upon, but we don't know if by the user or by code
                .doOnNext(position -> {
                    presenter.onPageSelected(position);
                    //log the current fragment that is being shown (today, tomorrow)
                    analytics.logScreen(Screen.mainActivityScreen(position));
                })
                .subscribe();

        analyticsNavigationReporter = navigationSourcePublisher.skip(1) // the spinner adapter emits 1 needless item
                .buffer(2) //each listener will emit 1 item when either of the listeners are acted upon
                .map(navigations -> navigations.get(0)) //whichever element in the list came first represents how the user interacted with the UI
                .subscribe(navigation -> analytics.logForecastNavigationMethod(navigation));
    }

    @Override
    protected void inject(@NonNull ApplicationComponent applicationComponent) {
        mainComponent = DaggerMainComponent.builder().applicationComponent(applicationComponent).build();
        mainComponent.inject(this);
    }

    private void initDateSpinner() {
        ToolbarSpinnerAdapter spinnerAdapter = new ToolbarSpinnerAdapter(getApplicationContext());
        dateSpinner.setAdapter(spinnerAdapter);
    }

    private void initViewPager() {
        ForecastViewPagerAdapter viewPagerAdapter = new ForecastViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        RxViewPager.pageScrollStateChanges(viewPager).subscribe(state -> {
            //Fixes issue where swiping horizontally interferes with the SwipeRefreshLayout
            //http://stackoverflow.com/questions/25978462/swiperefreshlayout-viewpager-limit-horizontal-scroll-only
            swipeRefreshLayout.setEnabled(state == ViewPager.SCROLL_STATE_IDLE);
        });
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            analytics.logRefresh(Refresh.PULL_TO_REFRESH);
            presenter.onRefreshSwiped();
        });
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
            analytics.logRefresh(Refresh.TOOLBAR);
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
