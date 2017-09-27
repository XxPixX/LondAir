package com.innercirclesoftware.londair.utils;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

public class ViewUtils {

    /**
     * Enables/disabled the view and all it's children
     *
     * @param view    the {@link View} that should be enabled/disabled
     * @param enabled whether the view should be enabled or not
     */
    public static void setEnabled(@NonNull View view, boolean enabled) {
        view.setEnabled(enabled);

        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = ((ViewGroup) view);

            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                setEnabled(viewGroup.getChildAt(i), enabled);
            }
        }
    }

    /**
     * Shows or hides a view by setting the visibility to {@link View#VISIBLE} when it should be shown,
     * and {@link View#GONE} when it shouldn't be shown
     *
     * @param view The {@link View} that should be shown/hidden
     * @param show true to show, false to hide
     */
    public static void show(@NonNull View view, boolean show) {
        if (show) show(view);
        else hide(view);
    }

    /**
     * Shows a view by changing it's visibility to {@link View#VISIBLE}
     *
     * @param view The {@link View} that should be shown
     */
    public static void show(@NonNull View view) {
        view.setVisibility(View.VISIBLE);
    }

    /**
     * Hides a view by changing it's visibility to {@link View#GONE}
     *
     * @param view The {@link View} that should be hidden
     */
    public static void hide(@NonNull View view) {
        view.setVisibility(View.GONE);
    }

    /**
     * Returns true if the view's visibility is {@link View#VISIBLE}
     *
     * @param view The {@link View} that should be shown
     */
    public static boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }
}
