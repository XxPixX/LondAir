package com.innercirclesoftware.londair.ui.main.airquality;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.Animatable2Compat;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;
import android.transition.AutoTransition;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.innercirclesoftware.londair.R;
import com.innercirclesoftware.londair.base.BaseFragment;
import com.innercirclesoftware.londair.data.tfl.CurrentForecast;
import com.innercirclesoftware.londair.ui.main.ForecastViewPagerAdapter;
import com.innercirclesoftware.londair.ui.main.MainActivity;
import com.innercirclesoftware.londair.ui.main.MainComponent;
import com.innercirclesoftware.londair.ui.main.MainPresenter;
import com.innercirclesoftware.londair.utils.PrimitiveUtils;
import com.innercirclesoftware.londair.utils.ViewUtils;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class AirQualityFragment extends BaseFragment implements AirQualityView {

    private static final String ARG_KEY_POSITION = "ARG_KEY_POSITION";

    /**
     * Defines the relative text size of pollutant subscripts relative to the standard texts size, e.g. the subscript "2" in "NO2"
     */
    private static final float PARTICLE_SUBSCRIPT_RELATIVE_SIZE = 0.8f;

    @Inject AirQualityPresenter presenter;
    @Inject MainPresenter mainPresenter;

    @BindView(R.id.nested_scroll_view) NestedScrollView nestedScrollView;
    @BindView(R.id.container) LinearLayout container;

    @BindView(R.id.card_pollutants) CardView pollutantsCard;

    @BindView(R.id.card_pollution_summary) CardView cardPollutionSummary;
    @BindView(R.id.forecast_band) TextView forecastBand;
    @BindView(R.id.forecast_summary) TextView forecastSummary;

    @BindView(R.id.pm_25_title) TextView titlePm25;
    @BindView(R.id.pm_10_title) TextView titlePm10;
    @BindView(R.id.no2_title) TextView titleNo2;
    @BindView(R.id.o3_title) TextView titleO3;
    @BindView(R.id.so2_title) TextView titleSo2;

    @BindView(R.id.pm_25_subtitle) TextView subtitlePm25;
    @BindView(R.id.pm_10_subtitle) TextView subtitle10;
    @BindView(R.id.no2_subtitle) TextView subtitleNo2;
    @BindView(R.id.o3_subtitle) TextView subtitleO3;
    @BindView(R.id.so2_subtitle) TextView subtitleSo2;

    @BindView(R.id.summary_pm10) TextView summaryPm10;
    @BindView(R.id.summary_pm25) TextView summaryPm25;
    @BindView(R.id.summary_no2) TextView summaryNo2;
    @BindView(R.id.summary_o3) TextView summaryO3;
    @BindView(R.id.summary_so2) TextView summarySo2;

    @BindView(R.id.divider) View pollutantsGeneralAdviceDivider;
    @BindView(R.id.pollutants_general_advice) TextView pollutantsGeneralAdvice;

    @BindView(R.id.expand_btn) AppCompatImageView expandImage;

    @BindView(R.id.forecast_text) TextView forecastText;

    @BindColor(R.color.card_high) int cardColorHigh;
    @BindColor(R.color.card_medium) int cardColorMedium;
    @BindColor(R.color.card_low) int cardColorLow;
    @BindColor(R.color.card_none) int cardColorNone;

    @BindColor(android.R.color.primary_text_dark) int whiteTextTitleColour;
    @BindColor(android.R.color.secondary_text_dark) int whiteTextSubtitleColour;
    @BindColor(android.R.color.primary_text_light) int blackTextTitleColour;
    @BindColor(android.R.color.secondary_text_light) int blackTextSubtitleColour;

    @BindInt((R.integer.expand_collapse_duration)) int expandCollapseDuration;

    private boolean isToday = false;

    public static AirQualityFragment newInstance(int position) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_KEY_POSITION, position);

        AirQualityFragment fragment = new AirQualityFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupPollutantsTextAppearance();
        inject();
        registerPresenter(presenter);

        int position = getArguments().getInt(ARG_KEY_POSITION, -1); //-1 is an impossible position, don't want it to default to 0
        PrimitiveUtils.assertInRange(0, position, 1);

        isToday = position == ForecastViewPagerAdapter.TAB_POSITION_TODAY;
        if (isToday) mainPresenter.attachTodaysView(this);
        else mainPresenter.attachTomorrowsView(this);
    }

    /**
     * Setup the pollutant text appearances by adding subscripts and changing the subscripts text size
     */
    private void setupPollutantsTextAppearance() {
        SpannableString pm25 = new SpannableString("PM2.5");
        pm25.setSpan(new SubscriptSpan(), 2, 5, 0);
        pm25.setSpan(new RelativeSizeSpan(PARTICLE_SUBSCRIPT_RELATIVE_SIZE), 2, 5, 0);
        titlePm25.setText(pm25, TextView.BufferType.SPANNABLE);

        SpannableString pm10 = new SpannableString("PM10");
        pm10.setSpan(new SubscriptSpan(), 2, 4, 0);
        pm10.setSpan(new RelativeSizeSpan(PARTICLE_SUBSCRIPT_RELATIVE_SIZE), 2, 4, 0);
        titlePm10.setText(pm10, TextView.BufferType.SPANNABLE);

        SpannableString no2 = new SpannableString("NO2");
        no2.setSpan(new SubscriptSpan(), 2, 3, 0);
        no2.setSpan(new RelativeSizeSpan(PARTICLE_SUBSCRIPT_RELATIVE_SIZE), 2, 3, 0);
        titleNo2.setText(no2, TextView.BufferType.SPANNABLE);

        SpannableString o3 = new SpannableString("O3");
        o3.setSpan(new SubscriptSpan(), 1, 2, 0);
        o3.setSpan(new RelativeSizeSpan(PARTICLE_SUBSCRIPT_RELATIVE_SIZE), 1, 2, 0);
        titleO3.setText(o3, TextView.BufferType.SPANNABLE);

        SpannableString so2 = new SpannableString("SO2");
        so2.setSpan(new SubscriptSpan(), 2, 3, 0);
        so2.setSpan(new RelativeSizeSpan(PARTICLE_SUBSCRIPT_RELATIVE_SIZE), 2, 3, 0);
        titleSo2.setText(so2, TextView.BufferType.SPANNABLE);
    }

    @Override
    public void onDestroyView() {
        if (isToday) mainPresenter.detachTodaysView();
        else mainPresenter.detachTomorrowsView();
        mainPresenter = null;
        super.onDestroyView();
    }

    private void inject() {
        DaggerAirQualityComponent.builder().mainComponent(getMainComponent()).build().inject(this);
    }

    private MainComponent getMainComponent() {
        return ((MainActivity) getBaseActivity()).getMainComponent();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_air_quality;
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

        Transition transition = new Slide(Gravity.BOTTOM);
        transition.setDuration(600);
        transition.setInterpolator(new DecelerateInterpolator(2.5f));

        if (ViewUtils.isVisible(container)) ViewUtils.hide(container);
        TransitionManager.beginDelayedTransition(container, transition);
        ViewUtils.show(container);
    }

    @Override
    public void onShowForecastRequested(@NonNull CurrentForecast forecast) {
        presenter.onShowForecastRequested(forecast);
    }

    @Override
    public void showDetailedPollutantSummaries(boolean detailed) {
        //TODO interpolators don't seem to work... on any of the following 3 transitions, needs investigatin
        Transition changeBounds = new AutoTransition();
        changeBounds.excludeTarget(pollutantsGeneralAdvice, true);
        changeBounds.excludeTarget(pollutantsGeneralAdviceDivider, true);

        Transition generalAdviceTransition = new Fade(detailed ? Fade.IN : Fade.OUT);
        generalAdviceTransition.addTarget(pollutantsGeneralAdvice);
        generalAdviceTransition.addTarget(pollutantsGeneralAdviceDivider);

        TransitionSet transitions = new TransitionSet();
        transitions.addTransition(generalAdviceTransition);
        transitions.addTransition(changeBounds);
        transitions.setOrdering(TransitionSet.ORDERING_TOGETHER);
        transitions.setDuration(expandCollapseDuration);

        TransitionManager.beginDelayedTransition(nestedScrollView, transitions);
        int maxLineCount = detailed ? Integer.MAX_VALUE : 2;
        subtitlePm25.setMaxLines(maxLineCount);
        subtitle10.setMaxLines(maxLineCount);
        subtitleNo2.setMaxLines(maxLineCount);
        subtitleO3.setMaxLines(maxLineCount);
        subtitleSo2.setMaxLines(maxLineCount);
        ViewUtils.show(pollutantsGeneralAdvice, detailed);
        ViewUtils.show(pollutantsGeneralAdviceDivider, detailed);

        //animate the chevron expand/collapse image
        AnimatedVectorDrawableCompat currentDrawable = AnimatedVectorDrawableCompat.create(getActivity(), detailed ? R.drawable.ic_animated_vector_chevron_up : R.drawable.ic_animated_vector_chevron_down);
        if (currentDrawable != null) {
            expandImage.setImageDrawable(currentDrawable);
            currentDrawable.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
                @Override
                public void onAnimationEnd(Drawable drawable) {
                    super.onAnimationEnd(drawable);
                    currentDrawable.clearAnimationCallbacks();
                    expandImage.setImageResource(detailed ? R.drawable.ic_animated_vector_chevron_down : R.drawable.ic_animated_vector_chevron_up);
                }
            });
            currentDrawable.start();
        } else {
            Timber.w("Error inflating animated vector drawable for detailed = %s", detailed);
        }
    }

    @OnClick({R.id.card_pollutants, R.id.expand_btn})
    protected void onPollutantsCardClicked() {
        presenter.onExpandClicked();
    }
}
