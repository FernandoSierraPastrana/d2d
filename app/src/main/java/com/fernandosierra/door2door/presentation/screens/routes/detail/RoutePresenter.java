package com.fernandosierra.door2door.presentation.screens.routes.detail;

import android.support.annotation.NonNull;

import com.fernandosierra.door2door.presentation.base.BasePresenter;

import javax.inject.Inject;

public class RoutePresenter extends BasePresenter<RouteView> {

    @Inject
    public RoutePresenter(@NonNull RouteView view) {
        super(view);
    }

    public void init() {
        secureViewCall(RouteView::init);
    }
}
