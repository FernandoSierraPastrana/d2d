package com.fernandosierra.door2door.presentation.screens.routes

import com.fernandosierra.door2door.domain.model.Route
import com.fernandosierra.door2door.presentation.base.BaseView

interface RoutesView : BaseView {

    fun init()

    fun drawRoutes(routes: List<Route>)

    fun updateRoute(position: Int)
}