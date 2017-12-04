package com.fernandosierra.door2door.domain.interactor;

import android.support.annotation.NonNull;

import com.fernandosierra.door2door.data.repository.RouteRepository;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;

public class SetupInteractor {
    private final RouteRepository routeRepository;

    @Inject
    public SetupInteractor(@NonNull RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public void setupFromLocalData(@NonNull String localJson, @NonNull DisposableCompletableObserver observer) {
        routeRepository.loadLocalDataIfItIsNecessary(localJson, observer);
    }
}
