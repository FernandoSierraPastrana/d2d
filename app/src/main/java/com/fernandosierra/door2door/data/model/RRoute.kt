package com.fernandosierra.door2door.domain.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore

open class RRoute(@SerializedName(TYPE) var type: String = "",
                  @SerializedName(PROVIDER) @Ignore var providerId: String = "",
                  @Transient var provider: RProvider? = RProvider(),
                  @SerializedName(SEGMENTS) var segments: RealmList<RSegment> = RealmList(),
                  @SerializedName(PRICE) var price: RPrice? = RPrice()
) : RealmObject() {
    companion object {
        private const val TYPE = "type"
        private const val PROVIDER = "provider"
        private const val SEGMENTS = "segments"
        private const val PRICE = "price"
    }
}