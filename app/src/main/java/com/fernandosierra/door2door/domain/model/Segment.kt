package com.fernandosierra.door2door.domain.model

import android.support.annotation.StringDef
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject

open class Segment(@SerializedName(NAME) var name: String? = null,
                   @SerializedName(NUM_STOPS) var numStop: Int = 0,
                   @SerializedName(STOPS) var stops: RealmList<Stop> = RealmList(),
                   @SerializedName(TRAVEL_MODE) @Mode var travelMode: String = "",
                   @SerializedName(DESCRIPTION) var description: String? = null,
                   @SerializedName(COLOR) var color: String = "",
                   @SerializedName(ICON) var icon: String = "",
                   @SerializedName(POLYLINE) var polyline: String? = null
) : RealmObject() {
    companion object {
        private const val NAME = "name"
        private const val NUM_STOPS = "num_stops"
        private const val STOPS = "stops"
        private const val TRAVEL_MODE = "travel_mode"
        private const val DESCRIPTION = "description"
        private const val COLOR = "color"
        private const val ICON = "icon_url"
        private const val POLYLINE = "polyline"

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