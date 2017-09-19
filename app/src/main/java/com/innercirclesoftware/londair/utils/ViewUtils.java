package com.innercirclesoftware.londair.utils;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

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
}
