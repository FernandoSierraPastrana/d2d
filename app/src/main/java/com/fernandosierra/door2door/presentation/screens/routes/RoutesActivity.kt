package com.fernandosierra.door2door.presentation.screens.routes

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
import com.fernandosierra.door2door.R
import com.fernandosierra.door2door.domain.model.Route
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dagger.android.AndroidInjection
import javax.inject.Inject

class RoutesActivity : AppCompatActivity(), RoutesView, OnMapReadyCallback {
    companion object {
        private const val POLYLINE_WIDTH = 15.0f
    }

    @Inject
    lateinit var presenter: RoutesPresenter
    private lateinit var googleMap: GoogleMap
    private var currentRoute = 0
    private val routesSparseArray = SparseArray<List<Polyline>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routes)
        presenter.init()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        presenter.drawRoutes()
    }

    override fun init() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_routes) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun drawRoutes(routes: List<Route>) {
        lateinit var routeBounds: LatLngBounds
        val roundCap = RoundCap()
        routes.mapIndexed { index, route ->
            if (index == 0) {
                routeBounds = getRouteBounds(route)
            }
            route.segments.map {
                googleMap.addPolyline(PolylineOptions()
                        .color(Color.parseColor(it.color))
                        .width(POLYLINE_WIDTH)
                        .clickable(true)
                        .geodesic(true)
                        .jointType(JointType.ROUND)
                        .startCap(roundCap)
                        .endCap(roundCap)
                        .visible(index == 0)
                        .addAll(it.polyline))
            }
        }.forEachIndexed { index, polylines -> routesSparseArray.put(index, polylines) }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(routeBounds, 40))
    }

    private fun getRouteBounds(route: Route): LatLngBounds {
        val builder = LatLngBounds.Builder()
        route.segments.flatMap { it.polyline }.forEach { builder.include(it) }
        return builder.build()
    }

    override fun updateRoute(position: Int) {
        routesSparseArray.get(currentRoute).map { it.isVisible = false }
        routesSparseArray.get(position).map { it.isVisible = true }
        currentRoute = position
    }
}
