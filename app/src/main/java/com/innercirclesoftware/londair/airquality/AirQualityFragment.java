package com.innercirclesoftware.londair.airquality;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.innercirclesoftware.londair.R;
import com.innercirclesoftware.londair.base.BaseFragment;

import butterknife.BindColor;
import butterknife.BindView;

public class AirQualityFragment extends BaseFragment {

    private static final String EXTRA_CURRENT_FORECAST = "EXTRA_CURRENT_FORECAST";

    @BindView(R.id.card_pollution_summary) CardView cardPollutionSummary;
    @BindView(R.id.forecast_band) TextView forecastBand;
    @BindView(R.id.forecast_summary) TextView forecastSummary;

    @BindView(R.id.summary_pm10) TextView summaryPm10;
    @BindView(R.id.summary_pm25) TextView summaryPm25;
    @BindView(R.id.summary_no2) TextView summaryNo2;
    @BindView(R.id.summary_o3) TextView summaryO3;
    @BindView(R.id.summary_so2) TextView summarySo2;

    @BindView(R.id.forecast_text) TextView forecastText;

    @BindColor(R.color.card_high) int cardColorHigh;
    @BindColor(R.color.card_medium) int cardColorMedium;
    @BindColor(R.color.card_low) int cardColorLow;

    @Nullable private CurrentForecast forecast;

    private static int whiteTextTitleColour;
    private static int whiteTextSubtitleColour;
    private static int blackTextTitleColour;
    private static int blackTextSubtitleColour;

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
        resolveTextColours();
        forecast = getArguments().getParcelable(EXTRA_CURRENT_FORECAST);
    }

    private void resolveTextColours() {
        TypedValue typedValue = new TypedValue();

        int[] textSizeAttr = new int[]{
                android.R.attr.textColorPrimary,
                android.R.attr.textColorSecondary,
                android.R.attr.textColorPrimaryInverse,
                android.R.attr.textColorSecondaryInverse,
        };

        TypedArray a = getContext().obtainStyledAttributes(typedValue.data, textSizeAttr);

        whiteTextTitleColour = a.getColor(0, Color.RED);
        whiteTextSubtitleColour = a.getColor(1, Color.RED);
        blackTextTitleColour = a.getColor(2, Color.RED);
        blackTextSubtitleColour = a.getColor(3, Color.RED);

        a.recycle();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_air_quality;
    }

    public void setForecast(@Nullable CurrentForecast forecast) {
        this.forecast = forecast;
        updatePollutionSummary();
        updatePollutantsSummaries();
        updateForecastText();
    }

    private void updatePollutionSummary() {
        if (forecast == null) return;
        forecastBand.setText(forecast.getForecastBand());
        forecastSummary.setText(forecast.getForecastSummary());
        int cardColour;
        switch (forecast.getForecastBand()) {
            case "High":
                cardColour = cardColorHigh;
                break;
            case "Moderate":
                cardColour = cardColorMedium;
                break;
            case "Low":
                cardColour = cardColorLow;
                break;
            default:
                cardColour = Color.RED;
        }

        if (cardColour != cardPollutionSummary.getCardBackgroundColor().getDefaultColor()) {
            cardPollutionSummary.setCardBackgroundColor(cardColour);
        }
    }

    private void updatePollutantsSummaries() {
        if (forecast == null) return;
        summaryPm25.setText(forecast.getPM25Band());
        summaryPm10.setText(forecast.getPM10Band());
        summaryNo2.setText(forecast.getNO2Band());
        summaryO3.setText(forecast.getO3Band());
        summarySo2.setText(forecast.getSO2Band());
    }

    private void updateForecastText() {
        forecastText.setText(Html.fromHtml(forecast.getForecastText()));
    }
}
