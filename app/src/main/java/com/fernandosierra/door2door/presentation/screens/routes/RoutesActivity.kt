package com.fernandosierra.door2door.presentation.screens.routes

import android.graphics.*
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.fernandosierra.door2door.R
import com.fernandosierra.door2door.domain.model.Route
import com.fernandosierra.door2door.domain.model.Segment
import com.fernandosierra.door2door.domain.model.Stop
import com.fernandosierra.door2door.presentation.model.MapRoute
import com.fernandosierra.door2door.presentation.model.MapSegment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_routes.*
import javax.inject.Inject
import kotlin.math.roundToInt


class RoutesActivity : AppCompatActivity(), RoutesView, OnMapReadyCallback {
    companion object {
        private const val POLYLINE_WIDTH = 15.0f
        private const val DEFAULT_START = 0
        private const val DEFAULT_ROUTE_PADDING = 120
        private const val DEFAULT_STOP_NAME = "You"
    }

    @Inject
    lateinit var presenter: RoutesPresenter
    private lateinit var googleMap: GoogleMap
    private var currentRoute = 0
    //FIXME check if there is an third party to display for the current route
    private var hasProvider = true
    private val roundCap = RoundCap()
    private lateinit var routeBounds: LatLngBounds
    private lateinit var stopBitmap: Bitmap
    private lateinit var startMarkerBitmap: Bitmap
    private lateinit var endMarkerBitmap: Bitmap
    private lateinit var routesPageAdapter: RoutesPageAdapter
    private val mapRoutes = mutableListOf<MapRoute>()

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

        val marginExpanded = resources.getDimensionPixelSize(R.dimen.height_routes_bottom_sheet)
        val marginCollapsed = resources.getDimensionPixelSize(R.dimen.peek_routes_bottom_sheet)
        val delta = marginExpanded - marginCollapsed

        val view = (supportFragmentManager.findFragmentById(R.id.map_routes) as SupportMapFragment).view
        BottomSheetBehavior.from(card_routes).setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                updateMapMargin(view, marginCollapsed + (delta * slideOffset).roundToInt())
                updateThirdPartyFab(slideOffset)
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                val margin = when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> marginExpanded
                    else -> marginCollapsed
                }
                updateMapMargin(view, margin)
                updateThirdPartyFab(if (margin == marginExpanded) 1.0f else 0.0f)
            }
        })

        googleMap.setOnMapClickListener { focusCurrentRoute(true) }
        googleMap.uiSettings.isMapToolbarEnabled = false
    }

    override fun init() {
        val markerBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_marker)
        startMarkerBitmap = tintBitmap(markerBitmap, ContextCompat.getColor(this, R.color.start_marker))
        endMarkerBitmap = tintBitmap(markerBitmap, ContextCompat.getColor(this, R.color.end_marker))

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_routes) as SupportMapFragment
        stopBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_stop)

        routesPageAdapter = RoutesPageAdapter(supportFragmentManager)
        pager_routes_detail.adapter = routesPageAdapter
        indicator_routes.setViewPager(pager_routes_detail)
        routesPageAdapter.registerDataSetObserver(indicator_routes.dataSetObserver)
        pager_routes_detail.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                // Nothing
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // Nothing
            }

            override fun onPageSelected(position: Int) {
                updateRoute(position)
            }
        })

        mapFragment.getMapAsync(this)
    }

    override fun showError() {
        Snackbar.make(coordinator_routes, R.string.error_routes_message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.error_routes_ok, { finish() })
                .show()
    }

    override fun drawRoutes(routes: List<Route>) {
        currentRoute = 0
        routesPageAdapter.setRoutes(routes)
        routesPageAdapter.notifyDataSetChanged()
        routes.mapIndexed { routeIndex, route ->
            mapRoutes.add(MapRoute(drawSegments(route.segments, routeIndex == 0)))
        }
        updateRouteBounds()
        focusCurrentRoute()
    }

    override fun updateRoute(position: Int) {
        showOrHideSegments(mapRoutes[currentRoute].segments, false)
        showOrHideSegments(mapRoutes[position].segments, true)
        currentRoute = position
        updateRouteBounds()
        focusCurrentRoute(true)
    }

    override fun focusCurrentRoute(animate: Boolean) {
        val newLatLngBounds = CameraUpdateFactory.newLatLngBounds(routeBounds, DEFAULT_ROUTE_PADDING)
        if (animate) {
            googleMap.animateCamera(newLatLngBounds)
        } else {
            googleMap.moveCamera(newLatLngBounds)
        }
    }

    private fun updateMapMargin(view: View?, margin: Int) {
        (view?.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = margin
        view.requestLayout()
        //TODO Improve this handling zoom manually
        focusCurrentRoute(true)
    }

    private fun updateThirdPartyFab(scale: Float) {
        if (hasProvider) {
            fab_routes_thirdparty.scaleX = scale
            fab_routes_thirdparty.scaleY = scale
        }
    }

    private fun drawSegments(segments: List<Segment>, isFirstRoute: Boolean): List<MapSegment> {
        return segments.mapIndexed { segmentIndex, segment ->
            val color = Color.parseColor(segment.color)
            val isFirstSegment = segmentIndex == DEFAULT_START
            val isLastSegment = segmentIndex == segments.size - 1
            val stopMarkers = drawStops(segment.stops, color, isFirstRoute, isFirstSegment, isLastSegment)
            val polyline = googleMap.addPolyline(PolylineOptions()
                    .color(color)
                    .width(POLYLINE_WIDTH)
                    .clickable(true)
                    .geodesic(true)
                    .jointType(JointType.ROUND)
                    .startCap(roundCap)
                    .endCap(roundCap)
                    .visible(isFirstRoute)
                    .addAll(segment.polyline))
            MapSegment(polyline, stopMarkers.toMutableList())
        }.toList()
    }

    private fun drawStops(stops: List<Stop>,
                          color: Int,
                          isFirstRoute: Boolean,
                          isFirstSegment: Boolean,
                          isLastSegment: Boolean): List<Marker> {
        val stopTintedBitmap = tintBitmap(stopBitmap, color)
        return stops.mapIndexed { stopIndex, stop ->
            val isFirstStop = stopIndex == DEFAULT_START
            val isLastStop = stopIndex == stops.size - 1
            googleMap.addMarker(MarkerOptions()
                    .icon(getBitmapDescriptorForStop(isFirstSegment, isFirstStop, isLastSegment, isLastStop, stopTintedBitmap))
                    .position(LatLng(stop.latitude, stop.longitude))
                    .title(stop.name ?: DEFAULT_STOP_NAME)
                    .flat(true)
                    .visible(isFirstRoute))
        }.toList()
    }

    private fun getBitmapDescriptorForStop(isFirstSegment: Boolean,
                                           isFirstStop: Boolean,
                                           isLastSegment: Boolean,
                                           isLastStop: Boolean,
                                           tintedBitmap: Bitmap?): BitmapDescriptor? {
        return if (isFirstSegment && isFirstStop) {
            BitmapDescriptorFactory.fromBitmap(startMarkerBitmap)
        } else if (isLastSegment && isLastStop) {
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

    private fun updateRouteBounds() {
        val builder = LatLngBounds.Builder()
        mapRoutes[currentRoute].segments.map { it.polyline.points.map { builder.include(it) } }
        routeBounds = builder.build()
    }

    private fun showOrHideSegments(segments: List<MapSegment>, show: Boolean) {
        segments.map {
            it.polyline.isVisible = show
            it.stops.map { it.isVisible = show }
        }
    }
}
