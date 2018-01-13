package com.fernandosierra.door2door.presentation.di

import com.fernandosierra.door2door.presentation.screens.routes.RoutesActivity
import com.fernandosierra.door2door.presentation.screens.routes.RoutesView
import com.fernandosierra.door2door.presentation.screens.routes.detail.RouteFragment
import com.fernandosierra.door2door.presentation.screens.routes.detail.RouteView
import com.fernandosierra.door2door.presentation.screens.splash.SplashActivity
import com.fernandosierra.door2door.presentation.screens.splash.SplashView
import dagger.Binds
import dagger.Module

@Module
abstract class PresentationModule {

    @Binds
    internal abstract fun bindMainView(splashActivity: SplashActivity): SplashView

    @Binds
    internal abstract fun bindRoutesView(routesActivity: RoutesActivity): RoutesView

    @Binds
    internal abstract fun bindRouteView(routeFragment: RouteFragment): RouteView
}