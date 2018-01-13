package com.fernandosierra.door2door.di

import com.fernandosierra.door2door.presentation.screens.routes.RoutesActivity
import com.fernandosierra.door2door.presentation.screens.routes.detail.RouteFragment
import com.fernandosierra.door2door.presentation.screens.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): SplashActivity

    @ContributesAndroidInjector
    internal abstract fun bindRoutesActivity(): RoutesActivity

    @ContributesAndroidInjector
    internal abstract fun bindRouteFragment(): RouteFragment
}