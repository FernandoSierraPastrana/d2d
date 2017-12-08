package com.fernandosierra.door2door.data.di;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Locale;

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

    @Provides
    @Singleton
    SimpleDateFormat provideSimpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
    }
}
