package com.innercirclesoftware.londair.injection.components;

import com.innercirclesoftware.londair.injection.modules.AndroidModule;
import com.innercirclesoftware.londair.injection.modules.NetworkModule;
import com.innercirclesoftware.londair.injection.modules.PresenterModule;
import com.innercirclesoftware.londair.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AndroidModule.class,
        PresenterModule.class,
        NetworkModule.class,
})
public interface ApplicationComponent {

    void inject(MainActivity activity);
}
