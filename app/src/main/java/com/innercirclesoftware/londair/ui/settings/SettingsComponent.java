package com.innercirclesoftware.londair.ui.settings;

import com.innercirclesoftware.londair.injection.components.ApplicationComponent;

import dagger.Component;

@PerSettingsActivity
@Component(dependencies = ApplicationComponent.class, modules = SettingsModule.class)
public interface SettingsComponent {

    void inject(SettingsActivity activity);

}
