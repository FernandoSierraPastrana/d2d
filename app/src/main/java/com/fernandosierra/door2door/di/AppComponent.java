package com.fernandosierra.door2door.di;

import android.app.Application;

import com.fernandosierra.door2door.Door2DoorApp;
import com.fernandosierra.door2door.data.di.DataModule;
import com.fernandosierra.door2door.presentation.di.PresentationModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Component(modules = {
        AppModule.class,
        AndroidInjectionModule.class,
        ActivityBuilder.class,
        PresentationModule.class,
        DataModule.class
})
@Singleton
public interface AppComponent {

    void inject(Door2DoorApp app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
