package com.fernandosierra.door2door.presentation.model;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;

import java.util.List;

import io.reactivex.annotations.NonNull;

public class MapSegment {
    private final Polyline polyline;
    private final List<Marker> stops;

    public MapSegment(@NonNull Polyline polyline, @NonNull List<Marker> stops) {
        this.polyline = polyline;
        this.stops = stops;
    }

    @NonNull
    public Polyline getPolyline() {
        return polyline;
    }

    @NonNull
    public List<Marker> getStops() {
        return stops;
    }
}
