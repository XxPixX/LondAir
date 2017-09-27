package com.innercirclesoftware.londair.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class PrefUtilsTest {

    private static SharedPreferences preferences;

    @BeforeClass
    public static void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    @Before
    public void beforeEachTest() throws Exception {
        preferences.edit().clear().apply(); //delete all preferences before running each test
    }

    @Test
    public void setInt() throws Exception {
        String key = "key";
        int value = 0;

        PrefUtils.setInt(preferences, key, value);
        assertEquals(value, preferences.getInt(key, -1));
    }

    @Test
    public void setBoolean() throws Exception {
        String key = "key";

        PrefUtils.setBoolean(preferences, key, true);
        assertTrue(preferences.getBoolean(key, false));
    }

    @Test
    public void setString() throws Exception {
        String key = "key";
        String value = "value";

        PrefUtils.setString(preferences, key, value);
        assertEquals(value, preferences.getString(key, ""));
    }

    @AfterClass
    public static void tearDown() throws Exception {
        preferences = null;
    }
}