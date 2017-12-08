package com.fernandosierra.door2door.domain.model

import android.support.annotation.StringDef
import com.google.android.gms.maps.model.LatLng

data class Provider(val id: String, val icon: String, val disclaimer: String, val displayName: String?, val packageName: String?)

data class Price(val currency: String, val amount: Float)

data class Segment(val name: String?, val numStop: Int, val stops: List<Stop>, @Mode val travelMode: String, val description: String?,
                   val color: String, val icon: String, val polyline: List<LatLng>) {
    companion object {
        const val WALKING = "walking"
        const val SUBWAY = "subway"
        const val BUS = "bus"
        const val CHANGE = "change"
        const val SETUP = "setup"
        const val DRIVING = "driving"
        const val PARKING = "parking"
        const val CYCLING = "cycling"
    }

    @StringDef(WALKING, SUBWAY, BUS, CHANGE, SETUP, DRIVING, PARKING, CYCLING)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Mode
}

data class Stop(val latitude: Double, val longitude: Double, val date: Long, val name: String?)

data class Route(@Type val type: String, val provider: Provider, val segments: List<Segment>, val price: Price?) {
    companion object {
        const val PUBLIC_TRANSPORT = "public_transport"
        const val CAR_SHARING = "car_sharing"
        const val PRIVATE_BIKE = "private_bike"
        const val BIKE_SHARING = "bike_sharing"
        const val TAXI = "taxi"
    }

    @StringDef(PUBLIC_TRANSPORT, CAR_SHARING, PRIVATE_BIKE, BIKE_SHARING, TAXI)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Type
}