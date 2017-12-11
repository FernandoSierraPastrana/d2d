package com.fernandosierra.door2door.presentation.screens.routes;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.fernandosierra.door2door.domain.interactor.RoutesInteractor;
import com.fernandosierra.door2door.domain.model.Route;
import com.fernandosierra.door2door.presentation.base.BasePresenter;
import com.fernandosierra.door2door.presentation.util.observer.SingleObserverAdapter;

import java.util.List;

import javax.inject.Inject;

public class RoutesPresenter extends BasePresenter<RoutesView> {
    private final RoutesInteractor routesInteractor;

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
                secureViewCall(view -> view.drawRoutes(routes));
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                secureViewCall(RoutesView::showError);
            }
        });
    }

    public void openThirdParty() {
        secureViewCall(view -> {
            Intent thirdPartyIntent = view.getThirdPartyIntent();
            if (thirdPartyIntent == null) {
                view.launchStore();
            } else {
                view.launchThirdParty(thirdPartyIntent);
            }
        });
    }
}
