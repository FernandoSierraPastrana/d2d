package com.fernandosierra.door2door.presentation.model

import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline

data class MapRoute(val segments: List<MapSegment>, val thirdPartyPackage: String?)

data class MapSegment(val polyline: Polyline, val stops: List<Marker>)
