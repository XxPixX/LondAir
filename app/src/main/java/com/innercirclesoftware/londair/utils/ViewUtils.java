package com.innercirclesoftware.londair.utils;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ViewUtils {

    public static void setEnabled(@NonNull View view, boolean enabled) {
        view.setEnabled(enabled);

        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = ((ViewGroup) view);

            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                setEnabled(viewGroup.getChildAt(i), enabled);
            }
        }
    }

    public static void show(@NonNull View view, boolean show) {
        if (show) show(view);
        else hide(view);
    }

    public static void show(@NonNull View view) {
        view.setVisibility(View.VISIBLE);
    }

    public static void hide(@NonNull View view) {
        view.setVisibility(View.GONE);
    }

    public static boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }
}
