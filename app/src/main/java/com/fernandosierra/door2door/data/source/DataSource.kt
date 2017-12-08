package com.fernandosierra.door2door.data.source

import io.realm.Realm
import io.realm.RealmObject

abstract class DataSource<T : RealmObject>(private val clazz: Class<T>) {

    fun createOrUpdate(realmObjects: List<T>) {
        Realm.getDefaultInstance().use {
            it.executeTransaction({
                for (realmObject in realmObjects) {
                    createOrUpdate(realmObject, it)
                }
            })
        }
    }

    fun createOrUpdate(realmObject: T, realm: Realm? = null) {
        when (realm) {
            null -> Realm.getDefaultInstance().copyToRealmOrUpdate(realmObject)
            else -> realm.copyToRealmOrUpdate(realmObject)
        }
    }

    fun create(realmObjects: List<T>) =
            Realm.getDefaultInstance().use {
                it.executeTransaction({
                    for (realmObject in realmObjects) {
                        create(realmObject)
                    }
                })
            }

    fun create(realmObject: T, realm: Realm? = null) {
        when (realm) {
            null -> Realm.getDefaultInstance().copyToRealm(realmObject)
            else -> realm.copyToRealm(realmObject)
        }
    }

    fun deleteAll() =
            Realm.getDefaultInstance().use {
                it.executeTransaction({ it.delete(clazz) })
            }

    fun getAll(): List<T> {
        val realmObjects = mutableListOf<T>()
        realmObjects.addAll(Realm.getDefaultInstance().where(clazz).findAll())
        return realmObjects
    }

    fun getByPrimaryKey(key: String, realm: Realm? = null): T? {
        val primaryKey = getPrimaryKey()
        if (primaryKey == null) {
            throw UnsupportedOperationException()
        } else {
            return when (realm) {
                null -> Realm.getDefaultInstance().where(clazz).equalTo(primaryKey, key).findFirst()
                else -> realm.where(clazz).equalTo(primaryKey, key).findFirst()
            }
        }
    }

    fun isEmpty(): Boolean {
        var isEmpty = false
        Realm.getDefaultInstance().use {
            isEmpty = it.where(clazz).count() == 0L
        }
        return isEmpty;
    }

    abstract fun getPrimaryKey(): String?
}