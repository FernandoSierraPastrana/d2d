package com.fernandosierra.door2door.presentation.screens.routes

import com.fernandosierra.door2door.domain.interactor.RoutesInteractor
import com.fernandosierra.door2door.presentation.base.BasePresenter
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class RoutesPresenter @Inject constructor(view: RoutesView, private val routesInteractor: RoutesInteractor)
    : BasePresenter<RoutesView>(view) {

    fun init() = view { it.init() }

    fun drawRoutes() =
            launch(UI) {
                val routes = routesInteractor.getAllRoutes()
                view { it.drawRoutes(routes) }
            }

    fun openThirdParty() = view {
        val thirdPartyIntent = it.getThirdPartyIntent()
        if (thirdPartyIntent == null) {
            it.launchStore()
        } else {
            it.launchThirdParty(thirdPartyIntent)
        }
    }
}