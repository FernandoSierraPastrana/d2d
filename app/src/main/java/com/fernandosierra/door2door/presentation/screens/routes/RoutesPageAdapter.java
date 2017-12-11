package com.fernandosierra.door2door.presentation.screens.routes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fernandosierra.door2door.domain.model.Route;
import com.fernandosierra.door2door.presentation.screens.routes.detail.RouteFragment;

import java.util.List;

import io.reactivex.annotations.NonNull;

public class RoutesPageAdapter extends FragmentPagerAdapter {
    private List<Route> routes;

    public RoutesPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void setRoutes(@NonNull List<Route> routes) {
        this.routes = routes;
    }

    @Override
    public Fragment getItem(int position) {
        return RouteFragment.Companion.newInstance(routes.get(position));
    }

    @Override
    public int getCount() {
        return routes == null ? 0 : routes.size();
    }
}
