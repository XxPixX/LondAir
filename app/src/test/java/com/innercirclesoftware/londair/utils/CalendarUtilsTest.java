package com.innercirclesoftware.londair.utils;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class CalendarUtilsTest {

    @Test
    public void isPast() throws Throwable {
        Calendar pastCalendar = Calendar.getInstance();
        pastCalendar.add(Calendar.MILLISECOND, -1); //1ms in the past
        assertTrue(CalendarUtils.isPast(pastCalendar));

        //changes in time could mean that the future will be in the past upon testing
        Calendar futureCalendar = Calendar.getInstance();
        futureCalendar.add(Calendar.MILLISECOND, 1); //1ms in the future
        assertFalse(CalendarUtils.isPast(futureCalendar));

        Calendar now = Calendar.getInstance();
        assertFalse(CalendarUtils.isPast(now));
    }

    @Test
    public void isPast1() throws Exception {
        long past = Calendar.getInstance().getTimeInMillis() - 1; //1ms in the past
        assertTrue(CalendarUtils.isPast(past));

        long now = Calendar.getInstance().getTimeInMillis();
        assertFalse(CalendarUtils.isPast(now));

        long future = Calendar.getInstance().getTimeInMillis() + 1; //1ms in the future
        assertFalse(CalendarUtils.isPast(future));
    }

    @Test
    public void getCalendarWithHourMinute() throws Exception {
        Calendar calendar;
        for (int hour = 0; hour < 24; hour++) {
            for (int minute = 0; minute < 60; minute++) {
                calendar = CalendarUtils.getCalendarWithHourMinute(hour, minute);
                assertEquals(hour, calendar.get(Calendar.HOUR_OF_DAY));
                assertEquals(minute, calendar.get(Calendar.MINUTE));
            }
        }

        Calendar incorrectCalendar = CalendarUtils.getCalendarWithHourMinute(24, 60);
        assertNotEquals(24, incorrectCalendar.get(Calendar.HOUR_OF_DAY));
        assertNotEquals(60, incorrectCalendar.get(Calendar.MINUTE));
    }
}