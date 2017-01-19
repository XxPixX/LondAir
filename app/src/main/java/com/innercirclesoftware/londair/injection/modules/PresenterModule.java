package com.innercirclesoftware.londair.injection.modules;

import com.innercirclesoftware.londair.main.MainPresenter;
import com.innercirclesoftware.londair.main.MainPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    MainPresenter providesMainPresenter() {
        return new MainPresenterImpl();
    }
}
