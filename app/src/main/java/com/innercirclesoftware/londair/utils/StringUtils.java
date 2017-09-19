package com.innercirclesoftware.londair.utils;

import android.support.annotation.NonNull;

import java.util.Arrays;

public class StringUtils {
    public static void assertContains(@NonNull String[] possibilities, String value) {
        boolean foundPossibility = false;

        for (String possibility : possibilities) {
            if (possibility.equals(value)) {
                foundPossibility = true;
                break;
            }
        }

        if (!foundPossibility) throw new AssertionError(Arrays.toString(possibilities) + " does not contain " + value);
    }
}
