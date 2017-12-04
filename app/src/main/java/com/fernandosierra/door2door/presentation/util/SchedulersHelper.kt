package com.fernandosierra.door2door.presentation.util

import android.os.HandlerThread
import android.support.annotation.IntDef
import android.support.v4.util.LongSparseArray
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

object SchedulersHelper {
    const val REALM = 0L
    private const val THREAD_PREFIX = "Door2Door_"
    private val threadsMap = LongSparseArray<HandlerThread>()

    fun init() {
        createAndStartHandlerThread(REALM)
    }

    fun getScheduler(@Type key: Long): Scheduler = AndroidSchedulers.from(threadsMap.get(key)?.looper)

    private fun createAndStartHandlerThread(@Type key: Long) {
        val handlerThread = HandlerThread(THREAD_PREFIX + key)
        handlerThread.start()
        threadsMap.append(key, handlerThread)
    }

    @IntDef(REALM)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Type
}