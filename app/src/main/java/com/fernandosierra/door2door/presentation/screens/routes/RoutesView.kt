package com.fernandosierra.door2door.presentation.screens.routes

import com.fernandosierra.door2door.domain.model.Route
import com.fernandosierra.door2door.presentation.base.BaseView

interface RoutesView : BaseView {

    fun init()

    fun showError()

    fun drawRoutes(routes: List<Route>)

    fun updateRoute(position: Int)

    fun focusCurrentRoute(animate: Boolean = false)
}