package com.fernandosierra.door2door.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class RPrice(@SerializedName(CURRENCY) var currency: String = "",
                  @SerializedName(AMOUNT) var amount: Float = 0f
) : RealmObject() {
    companion object {
        private const val CURRENCY = "currency"
        private const val AMOUNT = "amount"
    }
}