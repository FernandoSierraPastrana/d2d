package com.fernandosierra.door2door.data.mapper

import com.fernandosierra.door2door.domain.model.Provider
import com.fernandosierra.door2door.domain.model.RProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RProviderMapper @Inject constructor() : RealmMapper<RProvider, Provider> {

    override fun transform(input: RProvider): Provider {
        return Provider(input.id, input.icon, input.disclaimer, input.displayName, input.packageName)
    }
}