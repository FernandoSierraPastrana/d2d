package com.fernandosierra.door2door.presentation.di;

import com.fernandosierra.door2door.presentation.screens.routes.RoutesActivity;
import com.fernandosierra.door2door.presentation.screens.routes.RoutesView;
import com.fernandosierra.door2door.presentation.screens.splash.SplashActivity;
import com.fernandosierra.door2door.presentation.screens.splash.SplashView;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PresentationModule {

    @Binds
    abstract SplashView bindMainView(SplashActivity splashActivity);

    @Binds
    abstract RoutesView bindRoutesView(RoutesActivity routesActivity);
}
