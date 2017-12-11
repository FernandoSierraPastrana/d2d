package com.fernandosierra.door2door.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RProvider(@PrimaryKey var id: String = "",
                     @SerializedName(ICON) var icon: String = "",
                     @SerializedName(DISCLAIMER) var disclaimer: String = "",
                     @SerializedName(DISPLAY_NAME) var displayName: String? = null,
                     @SerializedName(PACKAGE_NAME) var packageName: String? = null
) : RealmObject() {
    companion object {
        const val PRIMARY_KEY = "id"
        private const val ICON = "provider_icon_url"
        private const val DISCLAIMER = "disclaimer"
        private const val DISPLAY_NAME = "display_name"
        private const val PACKAGE_NAME = "android_package_name"
    }

    constructor(id: String, base: RProvider) : this(id, base.icon, base.disclaimer, base.displayName, base.packageName)
}
