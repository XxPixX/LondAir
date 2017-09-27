package com.innercirclesoftware.londair.utils;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ViewUtilsTest {

    private View view;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        view = new View(context);
    }

    @Test
    public void setEnabled() throws Exception {
        ViewGroup viewGroup = new FrameLayout(InstrumentationRegistry.getTargetContext());

        ViewGroup childViewGroup = new FrameLayout(InstrumentationRegistry.getTargetContext());
        childViewGroup.addView(new View(InstrumentationRegistry.getTargetContext()));
        childViewGroup.addView(new View(InstrumentationRegistry.getTargetContext()));

        viewGroup.addView(childViewGroup);
        viewGroup.addView(new View(InstrumentationRegistry.getTargetContext()));

        ViewUtils.setEnabled(viewGroup, true); //set the root ViewGroup to enabled
        assertTrue(viewGroup.isEnabled());
        assertTrue(viewGroup.getChildAt(1).isEnabled()); // child at index 0 is childViewGroup - tested later on

        assertTrue(childViewGroup.isEnabled());
        assertTrue(childViewGroup.getChildAt(0).isEnabled());
        assertTrue(childViewGroup.getChildAt(1).isEnabled());

        ViewUtils.setEnabled(viewGroup, false);
        assertFalse(viewGroup.isEnabled());
        assertFalse(viewGroup.getChildAt(1).isEnabled()); // child at index 0 is childViewGroup - tested later on

        assertFalse(childViewGroup.isEnabled());
        assertFalse(childViewGroup.getChildAt(0).isEnabled());
        assertFalse(childViewGroup.getChildAt(1).isEnabled());
    }

    @Test
    public void show() throws Exception {
        view.setVisibility(View.GONE);
        assertEquals(View.GONE, view.getVisibility());

        ViewUtils.show(view);
        assertEquals(View.VISIBLE, view.getVisibility());
    }

    @Test
    public void show1() throws Exception {
        ViewUtils.show(view, true);
        assertEquals(View.VISIBLE, view.getVisibility());

        ViewUtils.show(view, false);
        assertEquals(View.GONE, view.getVisibility());
    }

    @Test
    public void hide() throws Exception {
        view.setVisibility(View.VISIBLE);
        assertEquals(View.VISIBLE, view.getVisibility());

        ViewUtils.hide(view);
        assertEquals(View.GONE, view.getVisibility());
    }

    @Test
    public void isVisible() throws Exception {
        ViewUtils.show(view);
        assertTrue(ViewUtils.isVisible(view));

        ViewUtils.hide(view);
        assertFalse(ViewUtils.isVisible(view));
    }

}