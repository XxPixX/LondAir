package com.innercirclesoftware.londair.main;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.ArrayAdapter;

import com.innercirclesoftware.londair.R;
import com.innercirclesoftware.londair.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainView {

    @Inject MainPresenter presenter;
    @BindView(R.id.date_spinner) AppCompatSpinner dateSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        initDateSpinner();
        presenter.attachView(this);
    }

    private void initDateSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.forecast_dates,
                R.layout.spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onSetActionBar(@NonNull ActionBar actionBar) {
        super.onSetActionBar(actionBar);
    }
}
