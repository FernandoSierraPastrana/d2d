package com.fernandosierra.door2door.presentation.util

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun <reified T : Activity> Activity.launchActivity(noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent)
}

inline fun <reified T : Activity> newIntent(context: Context): Intent =
        Intent(context, T::class.java)