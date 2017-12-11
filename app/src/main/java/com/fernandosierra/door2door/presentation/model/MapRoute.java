package com.fernandosierra.door2door.presentation.model;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class MapRoute {
    private final List<MapSegment> segments;
    private final String thirdPartyPackage;

    public MapRoute(@NonNull List<MapSegment> segments, @Nullable String thirdPartyPackage) {
        this.segments = segments;
        this.thirdPartyPackage = thirdPartyPackage;
    }

    @NonNull
    public List<MapSegment> getSegments() {
        return segments;
    }

    @Nullable
    public String getThirdPartyPackage() {
        return thirdPartyPackage;
    }
}
