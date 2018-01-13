package com.fernandosierra.door2door.presentation.base

import java.lang.ref.WeakReference

abstract class BasePresenter<out V : BaseView>(view: V) {
    private val viewRef = WeakReference(view)

    fun view(block: (V) -> Unit) {
        val view = viewRef.get()
        if (view != null) {
            block.invoke(view)
        }
    }
}