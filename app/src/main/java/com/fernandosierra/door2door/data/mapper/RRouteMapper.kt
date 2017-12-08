package com.fernandosierra.door2door.data.mapper

import com.fernandosierra.door2door.domain.model.*
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import io.realm.RealmList
import java.text.SimpleDateFormat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RRouteMapper @Inject constructor(private val providerMapper: RProviderMapper,
                                       private val dateFormat: SimpleDateFormat) : RealmMapper<RRoute, Route> {

    override fun transform(input: RRoute): Route {
        return Route(input.type,
                providerMapper.transform(input.provider ?: RProvider()),
                transformSegments(input.segments),
                transformPrice(input.price))
    }

    private fun transformSegments(rSegments: RealmList<RSegment>): List<Segment> =
            rSegments.asSequence()
                    .map { rSegment ->
                        Segment(rSegment.name,
                                rSegment.numStop,
                                transformStops(rSegment.stops),
                                rSegment.travelMode,
                                rSegment.description,
                                rSegment.color,
                                rSegment.icon,
                                transformPolyline(rSegment.polyline))
                    }
                    .toList()

    private fun transformStops(rStops: RealmList<RStop>): List<Stop> =
            rStops.asSequence()
                    .map { rStop -> Stop(rStop.latitude, rStop.longitude, dateFormat.parse(rStop.date).time, rStop.name) }
                    .toList()

    private fun transformPolyline(polyline: String?): List<LatLng> =
            if (polyline == null) {
                listOf()
            } else {
                PolyUtil.decode(polyline)
            }

    private fun transformPrice(rPrice: RPrice?): Price? =
            if (rPrice == null) {
                null
            } else {
                Price(rPrice.currency, rPrice.amount)
            }
}