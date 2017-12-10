package com.fernandosierra.door2door.presentation.model;

import java.util.List;

import io.reactivex.annotations.NonNull;

public class MapRoute {
    private final List<MapSegment> segments;

    public MapRoute(@NonNull List<MapSegment> segments) {
        this.segments = segments;
    }

    @NonNull
    public List<MapSegment> getSegments() {
        return segments;
    }
}
