package com.innercirclesoftware.londair.utils;

public class PrimitiveUtils {

    /**
     * Asserts that the specific value is between min and max. If max is less than min, runs it again with the params swapped
     *
     * @param min   the minimum value that the value can take
     * @param value the value that should belong to range [min; max]
     * @param max   the maximum value that the value can take
     * @throws AssertionError if it's not in range
     */
    public static void assertInRange(int min, int value, int max) {
        if (min > max) {
            assertInRange(max, value, min);
            return;
        }

        if ((value < min || value > max))
            throw new AssertionError(String.format("%s is not in [%s, %s]", value, min, max));
    }
}
