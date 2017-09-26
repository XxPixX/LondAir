package com.innercirclesoftware.londair.utils;

import org.junit.Test;

public class PrimitiveUtilsTest {
    @Test
    public void assertInRange() throws Exception {
        //0 in [0; 0]
        PrimitiveUtils.assertInRange(0, 0, 0);

        //0 in [0; 1]
        PrimitiveUtils.assertInRange(0, 0, 1);
        PrimitiveUtils.assertInRange(1, 0, 0);

        //1 in [0; 1]
        PrimitiveUtils.assertInRange(0, 1, 1);
        PrimitiveUtils.assertInRange(1, 1, 0);
    }

    @Test(expected = AssertionError.class)
    public void assertInRangeThrowsException() {
        //0 in [-1, -1]
        PrimitiveUtils.assertInRange(-1, 0, -1);
    }
}