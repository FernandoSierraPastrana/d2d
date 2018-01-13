package com.fernandosierra.door2door.data.repository

import com.fernandosierra.door2door.data.LocalDataParser
import com.fernandosierra.door2door.data.mapper.RRouteMapper
import com.fernandosierra.door2door.data.source.ProviderDataSource
import com.fernandosierra.door2door.data.source.RouteDataSource
import com.fernandosierra.door2door.domain.model.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RouteRepository @Inject constructor(private val routeDataSource: RouteDataSource,
                                          private val providerDataSource: ProviderDataSource,
                                          private val localDataParser: LocalDataParser,
                                          private val rRouteMapper: RRouteMapper) {

    suspend fun loadLocalDataIfItIsNecessary(localJson: String) {
        if (routeDataSource.isEmpty() || providerDataSource.isEmpty()) {
            val parsedValues = localDataParser.readAndParseJson(localJson)
            providerDataSource.createOrUpdate(parsedValues.first)
            val routes = parsedValues.second.map {
                it.providerObject = providerDataSource.getByPrimaryKey(it.providerId)
                it
            }.toList()
            routeDataSource.deleteAll()
            routeDataSource.create(routes)
        }
    }

    suspend fun getAllRoutes(): List<Route> =
            routeDataSource.getAll().map { rRouteMapper.transform(it) }.toList()
}