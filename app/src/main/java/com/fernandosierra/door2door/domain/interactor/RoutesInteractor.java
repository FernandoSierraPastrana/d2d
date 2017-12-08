package com.fernandosierra.door2door.domain.interactor;

import android.support.annotation.NonNull;

import com.fernandosierra.door2door.data.repository.RouteRepository;
import com.fernandosierra.door2door.domain.model.Route;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

public class RoutesInteractor {
    private final RouteRepository routeRepository;

    @Inject
    public RoutesInteractor(@NonNull RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public void getAllRoutes(@NonNull DisposableSingleObserver<List<Route>> observer) {
        routeRepository.getAllRoutes(observer);
    }
}
