package com.innercirclesoftware.londair.injection.components;

import com.innercirclesoftware.londair.LondAir;
import com.innercirclesoftware.londair.data.preferences.PreferenceManager;
import com.innercirclesoftware.londair.data.tfl.TflService;
import com.innercirclesoftware.londair.injection.modules.AndroidModule;
import com.innercirclesoftware.londair.injection.modules.NetworkModule;
import com.innercirclesoftware.londair.injection.modules.NotificationModule;
import com.innercirclesoftware.londair.injection.modules.PreferencesModule;
import com.innercirclesoftware.londair.notifications.ForecastNotificationService;
import com.innercirclesoftware.londair.notifications.NotificationSchedulerReceiver;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AndroidModule.class,
        NetworkModule.class,
        NotificationModule.class,
        PreferencesModule.class
})
public interface ApplicationComponent {

    PreferenceManager preferenceManager();

    TflService tflService();

    void inject(LondAir app);

    void inject(ForecastNotificationService service);

    void inject(NotificationSchedulerReceiver receiver);
}
