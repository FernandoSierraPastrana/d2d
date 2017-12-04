package com.fernandosierra.door2door.presentation.util

import android.content.Context
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.ref.WeakReference

class Door2DoorUtils private constructor(context: Context) {
    private val contextRef: WeakReference<Context> = WeakReference(context)

    companion object {
        lateinit var instance: Door2DoorUtils

        fun init(context: Context) {
            instance = Door2DoorUtils(context)
        }
    }

    fun readJsonAsset(asset: String): String? {
        var json: String? = null
        try {
            val bufferedReader: BufferedReader? = BufferedReader(InputStreamReader(contextRef.get()?.assets?.open(asset)))
            if (bufferedReader != null) {
                val jsonBuilder = StringBuilder()
                var lineRead: String? = ""
                do {
                    jsonBuilder.append(lineRead)
                    lineRead = bufferedReader.readLine()
                } while (lineRead != null)
                json = jsonBuilder.toString()
            }
        } catch (exception: IOException) {
            Timber.e(exception)
        } finally {
            return json;
        }
    }
}