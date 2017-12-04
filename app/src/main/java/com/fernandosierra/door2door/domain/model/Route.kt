package com.fernandosierra.door2door.domain.model

import android.support.annotation.StringDef
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore

open class Route(@SerializedName(TYPE) @Type var type: String = "",
                 @SerializedName(PROVIDER) @Ignore var providerId: String = "",
                 @Transient var provider: Provider? = Provider(),
                 @SerializedName(SEGMENTS) var segments: RealmList<Segment> = RealmList(),
                 @SerializedName(PRICE) var price: Price? = Price()
) : RealmObject() {
    companion object {
        private const val TYPE = "type"
        private const val PROVIDER = "provider"
        private const val SEGMENTS = "segments"
        private const val PRICE = "price"
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