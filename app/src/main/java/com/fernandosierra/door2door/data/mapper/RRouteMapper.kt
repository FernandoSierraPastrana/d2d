package com.fernandosierra.door2door.data.mapper

import com.fernandosierra.door2door.domain.model.*
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import io.realm.RealmList
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RRouteMapper @Inject constructor(private val providerMapper: RProviderMapper,
                                       private val dateFormat: SimpleDateFormat) : RealmMapper<RRoute, Route> {
    private var startDate = 0L
    private var finalDate = 0L

    override fun transform(input: RRoute): Route =
            Route(input.type,
                    providerMapper.transform(input.provider ?: RProvider()),
                    transformSegments(input.segments),
                    transformPrice(input.price),
                    finalDate - startDate)

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
                    .map { rStop ->
                        val dateInMillis = dateFormat.parse(rStop.date).time
                        if (startDate == 0L) {
                            startDate = dateInMillis
                        }
                        if (finalDate < dateInMillis) {
                            finalDate = dateInMillis
                        }
                        Stop(rStop.latitude, rStop.longitude, dateInMillis, rStop.name)
                    }
                    .toList()

    private fun transformPolyline(polyline: String?): List<LatLng> =
            if (polyline == null) {
                listOf()
            } else {
                PolyUtil.decode(polyline)
            }

    private fun transformPrice(rPrice: RPrice?): String? =
            if (rPrice == null) {
                null
            } else {
                val currencyFormat = NumberFormat.getCurrencyInstance()
                currencyFormat.currency = Currency.getInstance(rPrice.currency)
                currencyFormat.format(rPrice.amount)
            }
}