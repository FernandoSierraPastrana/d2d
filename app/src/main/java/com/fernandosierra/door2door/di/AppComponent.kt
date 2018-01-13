package com.fernandosierra.door2door.di

import android.app.Application
import com.fernandosierra.door2door.Door2DoorApp
import com.fernandosierra.door2door.data.di.DataModule
import com.fernandosierra.door2door.presentation.di.PresentationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(modules = [(AppModule::class),
    (AndroidInjectionModule::class),
    (ActivityBuilder::class),
    (PresentationModule::class),
    (DataModule::class)])
@Singleton
interface AppComponent {

    fun inject(app: Door2DoorApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}