package com.fernandosierra.door2door.presentation.screens.routes

import android.content.Intent
import com.fernandosierra.door2door.domain.model.Route
import com.fernandosierra.door2door.presentation.base.BaseView

interface RoutesView : BaseView {

    fun init()

    fun showError()

    fun drawRoutes(routes: List<Route>)

    fun updateRoute(position: Int)

    fun focusCurrentRoute(animate: Boolean = false)

    fun getThirdPartyIntent(): Intent?

    fun launchStore()

    fun launchThirdParty(intent: Intent)
}