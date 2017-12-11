package com.fernandosierra.door2door.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class RStop(@SerializedName(LATITUDE) var latitude: Double = 0.0,
                 @SerializedName(LONGITUDE) var longitude: Double = 0.0,
                 @SerializedName(DATE) var date: String = "",
                 @SerializedName(NAME) var name: String? = null
) : RealmObject() {
    companion object {
        private const val LATITUDE = "lat"
        private const val LONGITUDE = "lng"
        private const val DATE = "datetime"
        private const val NAME = "name"
    }
}