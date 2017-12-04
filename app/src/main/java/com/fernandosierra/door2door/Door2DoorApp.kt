package com.fernandosierra.door2door

import android.app.Activity
import android.app.Application
import com.fernandosierra.door2door.di.DaggerAppComponent
import com.fernandosierra.door2door.presentation.util.Door2DoorUtils
import com.fernandosierra.door2door.presentation.util.SchedulersHelper
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.realm.Realm
import timber.log.Timber
import javax.inject.Inject

class Door2DoorApp : Application(), HasActivityInjector {
    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
        SchedulersHelper.init()
        Realm.init(this)
        Door2DoorUtils.init(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector
}