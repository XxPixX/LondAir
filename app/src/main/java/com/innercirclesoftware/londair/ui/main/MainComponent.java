package com.innercirclesoftware.londair.ui.main;

import com.innercirclesoftware.londair.injection.components.ApplicationComponent;

import dagger.Component;

@PerMainActivity
@Component(dependencies = ApplicationComponent.class, modules = MainModule.class)
public interface MainComponent {

    MainPresenter mainPresenter();

    void inject(MainActivity mainActivity);

}