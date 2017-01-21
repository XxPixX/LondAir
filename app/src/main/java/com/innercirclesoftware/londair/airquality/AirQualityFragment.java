package com.innercirclesoftware.londair.airquality;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.innercirclesoftware.londair.R;
import com.innercirclesoftware.londair.base.BaseFragment;

import butterknife.BindView;

public class AirQualityFragment extends BaseFragment {

    private static final String EXTRA_CURRENT_FORECAST = "EXTRA_CURRENT_FORECAST";

    @BindView(R.id.text_view) TextView textView;

    @Nullable private CurrentForecast forecast;

    public static AirQualityFragment newInstance(@Nullable CurrentForecast forecast) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_CURRENT_FORECAST, forecast);

        AirQualityFragment fragment = new AirQualityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        forecast = getArguments().getParcelable(EXTRA_CURRENT_FORECAST);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_air_quality;
    }
}
