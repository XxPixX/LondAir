package com.innercirclesoftware.londair.utils;

import org.junit.Test;

public class StringUtilsTest {

    private static final String[] POSSIBILITIES = new String[]{"one", "two", "three"};

    @Test
    public void assertContains() throws Exception {
        StringUtils.assertContains(POSSIBILITIES, "one");
        StringUtils.assertContains(POSSIBILITIES, "two");
        StringUtils.assertContains(POSSIBILITIES, "three");
    }

    @Test(expected = AssertionError.class)
    public void assertContainsThrowsException() {
        StringUtils.assertContains(POSSIBILITIES, "ONE"); //capitals everywhere
        StringUtils.assertContains(POSSIBILITIES, "Two"); //first letter capitalised
        StringUtils.assertContains(POSSIBILITIES, " three"); //whitespace
    }
}