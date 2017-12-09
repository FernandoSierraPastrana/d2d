package com.fernandosierra.door2door.presentation.screens.routes;

import android.support.annotation.NonNull;

import com.fernandosierra.door2door.domain.interactor.RoutesInteractor;
import com.fernandosierra.door2door.domain.model.Route;
import com.fernandosierra.door2door.presentation.base.BasePresenter;
import com.fernandosierra.door2door.presentation.util.observer.SingleObserverAdapter;

import java.util.List;

import javax.inject.Inject;

public class RoutesPresenter extends BasePresenter<RoutesView> {
    private final RoutesInteractor routesInteractor;
    private List<Route> routes;

    @Inject
    public RoutesPresenter(@NonNull RoutesView view, @NonNull RoutesInteractor routesInteractor) {
        super(view);
        this.routesInteractor = routesInteractor;
    }

    public void init() {
        secureViewCall(RoutesView::init);
    }

    public void drawRoutes() {
        routesInteractor.getAllRoutes(new SingleObserverAdapter<List<Route>>() {
            @Override
            public void onSuccess(List<Route> routes) {
                RoutesPresenter.this.routes = routes;
                secureViewCall(RoutesView::showError);
                secureViewCall(view -> view.drawRoutes(routes));
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                secureViewCall(RoutesView::showError);
            }
        });
    }
}
