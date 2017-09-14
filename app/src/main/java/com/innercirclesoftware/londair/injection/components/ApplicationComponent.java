package com.innercirclesoftware.londair.injection.components;

import com.innercirclesoftware.londair.LondAir;
import com.innercirclesoftware.londair.ui.main.airquality.AirQualityFragment;
import com.innercirclesoftware.londair.injection.modules.AndroidModule;
import com.innercirclesoftware.londair.injection.modules.NetworkModule;
import com.innercirclesoftware.londair.injection.modules.NotificationModule;
import com.innercirclesoftware.londair.injection.modules.PreferencesModule;
import com.innercirclesoftware.londair.injection.modules.PresenterModule;
import com.innercirclesoftware.londair.ui.main.MainActivity;
import com.innercirclesoftware.londair.notifications.ForecastNotificationService;
import com.innercirclesoftware.londair.data.preferences.PreferenceManager;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AndroidModule.class,
        PresenterModule.class,
        NetworkModule.class,
        NotificationModule.class,
        PreferencesModule.class
})
public interface ApplicationComponent {

    PreferenceManager preferenceManager();

    void inject(MainActivity activity);

    void inject(AirQualityFragment fragment);

    void inject(ForecastNotificationService service);

    void inject(LondAir app);
}
