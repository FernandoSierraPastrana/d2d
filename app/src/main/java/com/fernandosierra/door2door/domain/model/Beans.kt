package com.fernandosierra.door2door.domain.model

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.StringDef
import com.google.android.gms.maps.model.LatLng

data class Provider(val id: String, val icon: String, val disclaimer: String, val displayName: String?, val packageName: String?)
    : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(icon)
        parcel.writeString(disclaimer)
        parcel.writeString(displayName)
        parcel.writeString(packageName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Provider> {
        override fun createFromParcel(parcel: Parcel): Provider {
            return Provider(parcel)
        }

        override fun newArray(size: Int): Array<Provider?> {
            return arrayOfNulls(size)
        }
    }
}

data class Segment(val name: String?, val numStop: Int, val stops: List<Stop>, @Mode val travelMode: String, val description: String?,
                   val color: String, val icon: String, val polyline: List<LatLng>) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            parcel.createTypedArrayList(Stop),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(LatLng.CREATOR))

    @StringDef(WALKING, SUBWAY, BUS, CHANGE, SETUP, DRIVING, PARKING, CYCLING)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Mode

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(numStop)
        parcel.writeTypedList(stops)
        parcel.writeString(travelMode)
        parcel.writeString(description)
        parcel.writeString(color)
        parcel.writeString(icon)
        parcel.writeTypedList(polyline)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Segment> {
        const val WALKING = "walking"
        const val SUBWAY = "subway"
        const val BUS = "bus"
        const val CHANGE = "change"
        const val SETUP = "setup"
        const val DRIVING = "driving"
        const val PARKING = "parking"
        const val CYCLING = "cycling"

        override fun createFromParcel(parcel: Parcel): Segment {
            return Segment(parcel)
        }

        override fun newArray(size: Int): Array<Segment?> {
            return arrayOfNulls(size)
        }
    }
}

data class Stop(val latitude: Double, val longitude: Double, val date: Long, val name: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readLong(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeLong(date)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Stop> {
        override fun createFromParcel(parcel: Parcel): Stop {
            return Stop(parcel)
        }

        override fun newArray(size: Int): Array<Stop?> {
            return arrayOfNulls(size)
        }
    }
}

data class Route(@Type val type: String, val provider: Provider, val segments: List<Segment>, val price: String?, val duration: Long)
    : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(Provider::class.java.classLoader),
            parcel.createTypedArrayList(Segment),
            parcel.readString(),
            parcel.readLong())

    @StringDef(PUBLIC_TRANSPORT, CAR_SHARING, PRIVATE_BIKE, BIKE_SHARING, TAXI)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Type

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeParcelable(provider, flags)
        parcel.writeTypedList(segments)
        parcel.writeString(price)
        parcel.writeLong(duration)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Route> {
        const val PUBLIC_TRANSPORT = "public_transport"
        const val CAR_SHARING = "car_sharing"
        const val PRIVATE_BIKE = "private_bike"
        const val BIKE_SHARING = "bike_sharing"
        const val TAXI = "taxi"

        override fun createFromParcel(parcel: Parcel): Route {
            return Route(parcel)
        }

        override fun newArray(size: Int): Array<Route?> {
            return arrayOfNulls(size)
        }
    }
}