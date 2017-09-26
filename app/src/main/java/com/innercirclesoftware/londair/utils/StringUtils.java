package com.innercirclesoftware.londair.utils;

import android.support.annotation.NonNull;

import java.util.Arrays;

public class StringUtils {

    /**
     * Checks if the possibilities contains the specified value, ignoring the case.
     *
     * @param possibilities the possible values that value can take
     * @param value         the value that must be present in possibilities
     * @throws AssertionError if possibilities doesn't contain the value
     */
    public static void assertContains(@NonNull String[] possibilities, @NonNull String value) {
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
