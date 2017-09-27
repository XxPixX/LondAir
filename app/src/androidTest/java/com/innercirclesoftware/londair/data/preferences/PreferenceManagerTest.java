package com.innercirclesoftware.londair.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.innercirclesoftware.londair.data.tfl.CurrentForecast;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class PreferenceManagerTest {

    private static SharedPreferences sharedPreferences;
    private static RxSharedPreferences rxSharedPreferences;
    private static PreferenceManager preferenceManager;

    @BeforeClass
    public static void beforeClass() {
        Context context = InstrumentationRegistry.getTargetContext();
        sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        rxSharedPreferences = RxSharedPreferences.create(sharedPreferences);
        preferenceManager = new PreferenceManagerImpl(sharedPreferences, rxSharedPreferences);
    }

    @AfterClass
    public static void afterClass() {
        sharedPreferences = null;
        rxSharedPreferences = null;
        preferenceManager = null;
    }

    @Before
    public void setUp() throws Exception {
        sharedPreferences.edit().clear().apply(); //delete all preferences
    }

    @Test
    public void setNotificationHour() throws Exception {
        String key = "KEY_NOTIFICATION_HOUR";
        int defaultValue = 9;
        int newValue = 0;

        preferenceManager.setNotificationHour(newValue);
        assertEquals(newValue, sharedPreferences.getInt(key, defaultValue));
    }

    @Test
    public void setNotificationMinute() throws Exception {
        String key = "KEY_NOTIFICATION_MINUTE";
        int defaultValue = 0;
        int newValue = 30;

        preferenceManager.setNotificationMinute(newValue);
        assertEquals(newValue, sharedPreferences.getInt(key, defaultValue));
    }

    @Test
    public void setNotificationMinSeverity() throws Exception {
        String key = "KEY_NOTIFICATION_MIN_SEVERITY";
        String defaultValue = CurrentForecast.BAND_LOW;
        String newValue = CurrentForecast.BAND_HIGH;

        preferenceManager.setNotificationMinSeverity(newValue);
        assertEquals(newValue, sharedPreferences.getString(key, defaultValue));
    }

    @Test
    public void setNotificationEnabled() throws Exception {
        String key = "KEY_NOTIFICATION_ENABLED";

        preferenceManager.setNotificationEnabled(false);
        assertFalse(sharedPreferences.getBoolean(key, true));
    }

    @Test
    public void notificationHour() throws Exception {
        TestObserver<Integer> notificationHour = preferenceManager.notificationHour().test();
        notificationHour.assertSubscribed();
        preferenceManager.setNotificationHour(8);
        notificationHour.awaitCount(2);
        notificationHour.assertValueAt(0, integer -> integer.equals(9));
        notificationHour.assertValueAt(1, integer -> integer.equals(8));
    }

    @Test
    public void notificationMinute() throws Exception {
        TestObserver<Integer> notificationMinute = preferenceManager.notificationMinute().test();
        notificationMinute.assertSubscribed();
        preferenceManager.setNotificationMinute(30);
        notificationMinute.awaitCount(2);
        notificationMinute.assertValueAt(0, integer -> integer.equals(0));
        notificationMinute.assertValueAt(1, integer -> integer.equals(30));
    }

    @Test
    public void notificationTime() throws Exception {
        TestObserver<Calendar> notificationTime = preferenceManager.notificationTime().test();
        notificationTime.assertSubscribed();
        preferenceManager.setNotificationHour(21);
        preferenceManager.setNotificationMinute(30);
        notificationTime.awaitCount(3);
        notificationTime.assertValueAt(0, calendar -> calendar.get(Calendar.HOUR_OF_DAY) == 9 && calendar.get(Calendar.MINUTE) == 0);
        notificationTime.assertValueAt(1, calendar -> calendar.get(Calendar.HOUR_OF_DAY) == 21 && calendar.get(Calendar.MINUTE) == 0);
        notificationTime.assertValueAt(2, calendar -> calendar.get(Calendar.HOUR_OF_DAY) == 21 && calendar.get(Calendar.MINUTE) == 30);
    }

    @Test
    public void notificationEnabled() throws Exception {
        TestObserver<Boolean> notificationEnabled = preferenceManager.notificationEnabled().test();
        notificationEnabled.assertSubscribed();
        preferenceManager.setNotificationEnabled(false);
        notificationEnabled.awaitCount(2);
        notificationEnabled.assertValueAt(0, enabled -> enabled);
        notificationEnabled.assertValueAt(1, enabled -> !enabled);
    }

    @Test
    public void notificationMinSeverity() throws Exception {
        TestObserver<String> minSeverity = preferenceManager.notificationMinSeverity().test();
        minSeverity.assertSubscribed();
        preferenceManager.setNotificationMinSeverity(CurrentForecast.BAND_HIGH);
        minSeverity.awaitCount(2);
        minSeverity.assertValueAt(0, s -> s.equals(CurrentForecast.BAND_LOW));
        minSeverity.assertValueAt(1, s -> s.equals(CurrentForecast.BAND_HIGH));
    }
}