package com.fernandosierra.door2door.presentation.screens.splash

import com.fernandosierra.door2door.presentation.base.BaseView

interface SplashView : BaseView {

    fun init()

    fun showLoader()

    fun hideLoader(callback: (() -> Unit)? = null)

    fun showErrorDialog()

    fun goRoutes()
}