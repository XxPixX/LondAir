package com.innercirclesoftware.londair.utils;

public class PrimitiveUtils {

    /**
     * @param min   the minimum value that the value can take
     * @param value the value that should belong to range [min; max]
     * @param max   the maximum value that the value can take
     */
    public static void assertInRange(int min, int value, int max) {
        if ((value < min || value > max))
            throw new AssertionError(String.format("%s is not in [%s, %s]", value, min, max));
    }
}
