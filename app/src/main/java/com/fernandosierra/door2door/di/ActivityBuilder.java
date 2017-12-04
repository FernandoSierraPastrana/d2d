package com.fernandosierra.door2door.di;

import com.fernandosierra.door2door.presentation.screens.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract SplashActivity bindMainActivity();
}
