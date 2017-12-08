package com.fernandosierra.door2door.presentation.screens.routes

import android.graphics.*
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
import com.fernandosierra.door2door.R
import com.fernandosierra.door2door.domain.model.Route
import com.fernandosierra.door2door.domain.model.Segment
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
        private const val DEFAULT_START = 0
        private const val DEFAULT_ROUTE_PADDING = 40
        private const val DEFAULT_STOP_NAME = "You"
    }

    @Inject
    lateinit var presenter: RoutesPresenter
    private lateinit var googleMap: GoogleMap
    private var currentRoute = 0
    private val roundCap = RoundCap()
    private lateinit var routeBounds: LatLngBounds
    private lateinit var stopBitmap: Bitmap
    private lateinit var startMarkerBitmap: Bitmap
    private lateinit var endMarkerBitmap: Bitmap
    private val routesSparseArray = SparseArray<List<Polyline>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routes)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        presenter.init()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        presenter.drawRoutes()
        googleMap.setOnMapClickListener { focusCurrentRoute(true) }
        googleMap.uiSettings.isMapToolbarEnabled = false
    }

    override fun init() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_routes) as SupportMapFragment
        stopBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_stop)
        val markerBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_marker)
        startMarkerBitmap = tintBitmap(markerBitmap, ContextCompat.getColor(this, R.color.start_marker))
        endMarkerBitmap = tintBitmap(markerBitmap, ContextCompat.getColor(this, R.color.end_marker))
        mapFragment.getMapAsync(this)
    }

    override fun drawRoutes(routes: List<Route>) {
        routes.mapIndexed { index, route ->
            if (index == 0) {
                routeBounds = getRouteBounds(route)
            }
            drawSegments(route, index)
        }.forEachIndexed { index, polylines -> routesSparseArray.put(index, polylines) }
        focusCurrentRoute()
    }

    override fun focusCurrentRoute(animate: Boolean) {
        val newLatLngBounds = CameraUpdateFactory.newLatLngBounds(routeBounds, DEFAULT_ROUTE_PADDING)
        if (animate) {
            googleMap.animateCamera(newLatLngBounds)
        } else {
            googleMap.moveCamera(newLatLngBounds)
        }
    }

    private fun drawSegments(route: Route, routeIndex: Int): List<Polyline> {
        return route.segments.mapIndexed { segmentIndex, segment ->
            val color = Color.parseColor(segment.color)
            val stopTintedBitmap = tintBitmap(stopBitmap, color)
            segment.stops.mapIndexed { stopIndex, stop ->
                googleMap.addMarker(MarkerOptions()
                        .icon(getBitmapDescriptorForStop(segmentIndex, stopIndex, route, segment, stopTintedBitmap))
                        .position(LatLng(stop.latitude, stop.longitude))
                        .title(stop.name ?: DEFAULT_STOP_NAME)
                        .flat(true)
                        .visible(routeIndex == DEFAULT_START))
            }
            googleMap.addPolyline(PolylineOptions()
                    .color(color)
                    .width(POLYLINE_WIDTH)
                    .clickable(true)
                    .geodesic(true)
                    .jointType(JointType.ROUND)
                    .startCap(roundCap)
                    .endCap(roundCap)
                    .visible(routeIndex == DEFAULT_START)
                    .addAll(segment.polyline))
        }
    }

    private fun getBitmapDescriptorForStop(segmentIndex: Int,
                                           stopIndex: Int,
                                           route: Route,
                                           segment: Segment,
                                           tintedBitmap: Bitmap?): BitmapDescriptor? {
        return if (segmentIndex == 0 && stopIndex == 0) {
            BitmapDescriptorFactory.fromBitmap(startMarkerBitmap)
        } else if (segmentIndex == route.segments.size - 1 && stopIndex == segment.stops.size - 1) {
            BitmapDescriptorFactory.fromBitmap(endMarkerBitmap)
        } else {
            BitmapDescriptorFactory.fromBitmap(tintedBitmap)
        }
    }

    private fun tintBitmap(bitmap: Bitmap, color: Int): Bitmap {
        val paint = Paint()
        paint.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
        val tintedBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(tintedBitmap)
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint)
        return tintedBitmap
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
        focusCurrentRoute(true)
    }
}
