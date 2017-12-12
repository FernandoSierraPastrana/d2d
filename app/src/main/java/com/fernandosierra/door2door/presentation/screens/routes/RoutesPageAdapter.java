package com.fernandosierra.door2door.presentation.screens.routes;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fernandosierra.door2door.domain.model.Route;
import com.fernandosierra.door2door.presentation.screens.routes.detail.RouteFragment;

import java.util.List;

import io.reactivex.annotations.NonNull;

public class RoutesPageAdapter extends FragmentPagerAdapter {
    private FragmentManager fragmentManager;
    private List<Route> routes;
    private int viewPagerId;

    public RoutesPageAdapter(@NonNull FragmentManager fm, @IdRes int viewPagerId) {
        super(fm);
        this.fragmentManager = fm;
        this.viewPagerId = viewPagerId;
    }

    public void setRoutes(@NonNull List<Route> routes) {
        this.routes = routes;
    }

    @Override
    public RouteFragment getItem(int position) {
        return RouteFragment.Companion.newInstance(routes.get(position));
    }

    @Override
    public int getCount() {
        return routes == null ? 0 : routes.size();
    }

    public void resetPage(int position) {
        Fragment fragment = fragmentManager.findFragmentByTag(getFragmentTag(position));
        if (fragment != null && fragment instanceof RouteFragment) {
            ((RouteFragment) fragment).reset();
        }
    }

    private String getFragmentTag(int fragmentPosition) {
        return "android:switcher:" + viewPagerId + ":" + fragmentPosition;
    }
}
