package com.fernandosierra.door2door.presentation.splash

import com.fernandosierra.door2door.presentation.base.BaseView

interface SplashView : BaseView {

    fun init()

    fun showLoader()

    fun hideLoader()
}