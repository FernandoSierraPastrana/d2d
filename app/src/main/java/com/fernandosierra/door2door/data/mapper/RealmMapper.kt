package com.fernandosierra.door2door.data.mapper

import io.realm.RealmObject

interface RealmMapper<in R : RealmObject, out O> {

    fun transform(input: R): O
}