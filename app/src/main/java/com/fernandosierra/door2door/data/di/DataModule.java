package com.fernandosierra.door2door.data.di;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }
}
