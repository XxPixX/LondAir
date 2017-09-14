package com.innercirclesoftware.londair.ui.main.airquality;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.innercirclesoftware.londair.R;
import com.innercirclesoftware.londair.data.tfl.CurrentForecast;
import com.innercirclesoftware.londair.base.BaseFragment;
import com.innercirclesoftware.londair.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import timber.log.Timber;

public class AirQualityFragment extends BaseFragment implements AirQualityView {

    private static final String ARG_KEY_POSITION = "ARG_KEY_POSITION";
    @Inject AirQualityPresenter presenter;

    @BindView(R.id.container) LinearLayout container;

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
    @BindColor(R.color.card_none) int cardColorNone;

    @BindColor(android.R.color.primary_text_dark) int whiteTextTitleColour;
    @BindColor(android.R.color.secondary_text_dark) int whiteTextSubtitleColour;
    @BindColor(android.R.color.primary_text_light) int blackTextTitleColour;
    @BindColor(android.R.color.secondary_text_light) int blackTextSubtitleColour;

    public static AirQualityFragment getInstance(int position) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_KEY_POSITION, position);

        AirQualityFragment fragment = new AirQualityFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getComponent().inject(this);
        registerPresenter(presenter);
        int position = getArguments().getInt(ARG_KEY_POSITION);
        MainActivity mainActivity = (MainActivity) getBaseActivity();
        if (position == 0) {
            CurrentForecast todaysForecast = mainActivity.getTodaysForecast();
            if (todaysForecast != null) setForecast(todaysForecast);
        } else {
            CurrentForecast tomorrowsForecast = mainActivity.getTomorrowsForecast();
            if (tomorrowsForecast != null) setForecast(tomorrowsForecast);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_air_quality;
    }

    public void setForecast(@Nullable CurrentForecast forecast) {
        presenter.onForecastRefreshed(forecast);
    }

    @Override
    public void showForecast(@NonNull CurrentForecast forecast) {
        String text = Html.fromHtml(forecast.getForecastText()).toString();
        String lineSep = System.getProperty("line.separator");
        forecastText.setText(text.replaceAll("<br/>", lineSep).trim());

        summaryPm25.setText(forecast.getPM25Band());
        summaryPm10.setText(forecast.getPM10Band());
        summaryNo2.setText(forecast.getNO2Band());
        summaryO3.setText(forecast.getO3Band());
        summarySo2.setText(forecast.getSO2Band());


        forecastBand.setText(forecast.getForecastBand());
        forecastSummary.setText(forecast.getForecastSummary());
        int cardColour;
        switch (forecast.getForecastBand()) {
            case CurrentForecast.BAND_HIGH:
                cardColour = cardColorHigh;
                break;
            case CurrentForecast.BAND_MODERATE:
                cardColour = cardColorMedium;
                break;
            case CurrentForecast.BAND_LOW:
                cardColour = cardColorLow;
                break;
            case CurrentForecast.BAND_NONE:
                cardColour = cardColorNone;
                break;
            default:
                Timber.w("Unknown forecast band %s when determining appropriate card color", forecast.getForecastBand());
                cardColour = Color.RED;
                break;
        }

        if (cardColour != cardPollutionSummary.getCardBackgroundColor().getDefaultColor()) {
            cardPollutionSummary.setCardBackgroundColor(cardColour);
        }

        Transition transition = TransitionInflater.from(getContext()).inflateTransition(R.transition.air_quality_transition);
        TransitionManager.beginDelayedTransition(container, transition);
        container.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideForecast() {
        TransitionManager.beginDelayedTransition(container);
        container.setVisibility(View.INVISIBLE);
    }
}
