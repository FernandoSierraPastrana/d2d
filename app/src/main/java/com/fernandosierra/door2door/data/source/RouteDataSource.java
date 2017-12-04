package com.fernandosierra.door2door.data.source;

import com.fernandosierra.door2door.domain.model.Route;

import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RouteDataSource extends DataSource<Route> {

    @Inject
    public RouteDataSource() {
        super(Route.class);
    }

    @Nullable
    @Override
    public String getPrimaryKey() {
        return null;
    }
}
