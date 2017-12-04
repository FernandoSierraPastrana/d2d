package com.fernandosierra.door2door.presentation.di;

import com.fernandosierra.door2door.presentation.screens.splash.SplashActivity;
import com.fernandosierra.door2door.presentation.screens.splash.SplashView;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PresentationModule {

    @Binds
    abstract SplashView bindMainView(SplashActivity splashActivity);
}
