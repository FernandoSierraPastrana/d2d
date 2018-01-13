package com.fernandosierra.door2door.presentation.screens.routes.detail

import com.fernandosierra.door2door.presentation.base.BasePresenter
import javax.inject.Inject

class RoutePresenter @Inject constructor(view: RouteView) : BasePresenter<RouteView>(view) {

    fun init() = view { it.init() }
}