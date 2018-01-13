package com.fernandosierra.door2door.domain.interactor

import com.fernandosierra.door2door.data.repository.RouteRepository
import com.fernandosierra.door2door.domain.model.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoutesInteractor @Inject constructor(private val routeRepository: RouteRepository) {

    suspend fun getAllRoutes(): List<Route> = routeRepository.getAllRoutes()
}