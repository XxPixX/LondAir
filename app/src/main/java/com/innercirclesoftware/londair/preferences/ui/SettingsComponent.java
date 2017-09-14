package com.innercirclesoftware.londair.preferences.ui;

import com.innercirclesoftware.londair.injection.components.ApplicationComponent;

import dagger.Component;

@PerSettingsActivity
@Component(dependencies = ApplicationComponent.class, modules = SettingsModule.class)
public interface SettingsComponent {

    void inject(SettingsActivity activity);

}
