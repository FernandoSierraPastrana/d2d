package com.fernandosierra.door2door.presentation.screens.routes.detail;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.fernandosierra.door2door.domain.model.Provider;
import com.fernandosierra.door2door.domain.model.Route;
import com.fernandosierra.door2door.presentation.screens.routes.detail.delegates.RouteDelegate;
import com.fernandosierra.door2door.presentation.screens.routes.detail.delegates.RouteHeaderDelegate;
import com.fernandosierra.door2door.presentation.screens.routes.detail.viewtypes.RouteHeaderViewType;
import com.fernandosierra.door2door.presentation.screens.routes.detail.viewtypes.ViewType;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class RouteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ViewType> viewTypes = new ArrayList<>();
    private SparseArray<RouteDelegate> delegatesSparseArray = new SparseArray<>();

    public RouteAdapter() {
        delegatesSparseArray.append(ViewType.TYPE_HEADER, new RouteHeaderDelegate());
    }

    public void setRoute(@NonNull Route route) {
        Provider provider = route.getProvider();
        viewTypes.clear();
        String displayName = provider.getDisplayName();
        viewTypes.add(new RouteHeaderViewType(provider.getIcon(), displayName == null ? provider.getId() : displayName, route.getType(),
                route.getDuration(), route.getPrice()));
    }

    @Override
    public int getItemCount() {
        return viewTypes.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@Nullable ViewGroup parent, int viewType) {
        return delegatesSparseArray.get(viewType).onCreateViewHolder(parent);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(@Nullable RecyclerView.ViewHolder holder, int position) {
        delegatesSparseArray.get(getItemViewType(position)).onBindViewHolder(holder, viewTypes.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return ViewType.TYPE_HEADER;
            default:
                return 0;
        }
    }
}