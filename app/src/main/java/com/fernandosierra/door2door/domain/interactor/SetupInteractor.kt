package com.fernandosierra.door2door.domain.interactor

import com.fernandosierra.door2door.data.repository.RouteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetupInteractor @Inject constructor(private val routeRepository: RouteRepository) {

    suspend fun setupFromLocalData(localJson: String) {
        routeRepository.loadLocalDataIfItIsNecessary(localJson)
    }
}