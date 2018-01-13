package com.fernandosierra.door2door.presentation.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

fun Context.readJsonAsset(asset: String): String? {
    var json: String? = null
    try {
        val bufferedReader: BufferedReader? = BufferedReader(InputStreamReader(this.assets?.open(asset)))
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

inline fun <reified T : Activity> Activity.launchActivity(noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent)
}

inline fun <reified T : Activity> newIntent(context: Context): Intent =
        Intent(context, T::class.java)