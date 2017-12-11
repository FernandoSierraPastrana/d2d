package com.fernandosierra.door2door.data.source;

import com.fernandosierra.door2door.data.model.RRoute;

import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RouteDataSource extends DataSource<RRoute> {

    @Inject
    RouteDataSource() {
        super(RRoute.class);
    }

    @Nullable
    @Override
    public String getPrimaryKey() {
        return null;
    }
}
