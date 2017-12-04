package com.fernandosierra.door2door.data

import com.fernandosierra.door2door.domain.model.Provider
import com.fernandosierra.door2door.domain.model.Route
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataParser @Inject constructor(val gson: Gson) {
    companion object {
        private const val PROVIDERS = "provider_attributes"
        private const val ROUTES = "routes"
    }

    fun readAndParseJson(json: String): Pair<List<Provider>, List<Route>> {
        val providers = mutableListOf<Provider>()
        val routes = mutableListOf<Route>()
        try {
            val rootObject = JSONObject(json)
            val providersJsonObject = rootObject.getJSONObject(PROVIDERS)
            val routesJsonArray = rootObject.getJSONArray(ROUTES)
            getProviders(providersJsonObject, providers)
            routes.addAll(getRoutes(routesJsonArray))
        } catch (exception: JSONException) {
            Timber.e(exception)
        } finally {
            return Pair(providers, routes)
        }
    }

    private fun getProviders(providersJsonObject: JSONObject?, providers: MutableList<Provider>) {
        if (providersJsonObject != null) {
            for (providerId in providersJsonObject.keys()) {
                try {
                    val providerFromJson: Provider? = gson.fromJson(providersJsonObject.getJSONObject(providerId).toString(),
                            Provider::class.java)
                    if (providerFromJson != null) {
                        val provider = Provider(providerId, providerFromJson)
                        providers.add(provider)
                    }
                } catch (exception: JsonSyntaxException) {
                    Timber.e(exception)
                }
            }
        }
    }

    private fun getRoutes(routesJsonArray: JSONArray?): List<Route> {
        return if (routesJsonArray != null) {
            gson.fromJson(routesJsonArray.toString(), object : TypeToken<List<Route>>() {}.type)
        } else {
            listOf()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private operator fun JSONArray.iterator(): Iterator<JSONObject> =
            (0 until length()).asSequence().map { get(it) as JSONObject }.iterator()
}