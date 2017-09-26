package com.innercirclesoftware.londair.data.analytics;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.innercirclesoftware.londair.ui.main.ForecastViewPagerAdapter;
import com.innercirclesoftware.londair.ui.main.MainActivity;

import timber.log.Timber;

public class Screen {

    @Nullable private final String name;
    @Nullable private final String type;
    @Nullable private final String id;

    private Screen(@Nullable String name, @Nullable String type, @Nullable String id) {
        this.name = name;
        this.type = type;
        this.id = id;
    }

    private Screen(@NonNull Builder builder) {
        this(builder.name, builder.type, builder.id);
    }

    @Nullable
    String getName() {
        return name;
    }

    @Nullable
    String getType() {
        return type;
    }

    @Nullable
    String getId() {
        return id;
    }


    @Override
    public String toString() {
        return "Screen{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @NonNull
    public static Screen mainActivityScreen(int tabPosition) {
        switch (tabPosition) {
            case ForecastViewPagerAdapter.TAB_POSITION_TODAY:
                return new Screen.Builder()
                        .name("Today forecast")
                        .build();
            case ForecastViewPagerAdapter.TAB_POSITION_TOMORROW:
                return new Screen.Builder()
                        .name("Tomorrow forecast")
                        .build();
            default:
                Timber.w("Unknown position %s in onPageSelected", tabPosition);
                return new Screen.Builder()
                        .name("MainActivity")
                        .build();
        }
    }


    public static final class Builder {

        @Nullable private String name;
        @Nullable private String type;
        @Nullable private String id;

        public Builder() {
        }

        public Builder name(@Nullable String val) {
            name = val;
            return this;
        }

        public Builder type(@Nullable String val) {
            type = val;
            return this;
        }

        public Builder id(@Nullable String val) {
            id = val;
            return this;
        }

        public Screen build() {
            return new Screen(this);
        }
    }
}
