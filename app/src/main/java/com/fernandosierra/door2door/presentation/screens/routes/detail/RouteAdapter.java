package com.fernandosierra.door2door.presentation.screens.routes.detail;

import android.graphics.Color;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.fernandosierra.door2door.domain.model.Provider;
import com.fernandosierra.door2door.domain.model.Route;
import com.fernandosierra.door2door.domain.model.Segment;
import com.fernandosierra.door2door.domain.model.Stop;
import com.fernandosierra.door2door.presentation.screens.routes.detail.delegates.RouteDelegate;
import com.fernandosierra.door2door.presentation.screens.routes.detail.delegates.RouteHeaderDelegate;
import com.fernandosierra.door2door.presentation.screens.routes.detail.delegates.SegmentDelegate;
import com.fernandosierra.door2door.presentation.screens.routes.detail.delegates.StopDelegate;
import com.fernandosierra.door2door.presentation.screens.routes.detail.viewtypes.RouteHeaderViewType;
import com.fernandosierra.door2door.presentation.screens.routes.detail.viewtypes.SegmentViewType;
import com.fernandosierra.door2door.presentation.screens.routes.detail.viewtypes.StopViewType;
import com.fernandosierra.door2door.presentation.screens.routes.detail.viewtypes.ViewType;
import com.fernandosierra.door2door.presentation.util.observer.SingleObserverAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.subjects.PublishSubject;

public class RouteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int NO_STOP = -1;
    private List<ViewType> viewTypes = new ArrayList<>();
    private SparseArray<RouteDelegate> delegatesSparseArray = new SparseArray<>();
    private PublishSubject<Pair<Integer, Integer>> publishSubject = PublishSubject.create();

    public RouteAdapter(@NonNull Observer<Pair<Integer, Integer>> observer) {
        delegatesSparseArray.append(ViewType.TYPE_HEADER, new RouteHeaderDelegate());
        delegatesSparseArray.append(ViewType.TYPE_SEGMENT, new SegmentDelegate(new SingleObserverAdapter<Integer>() {
            @Override
            public void onSuccess(Integer segmentIndex) {
                publishSubject.onNext(new Pair<>(segmentIndex, NO_STOP));
            }
        }));
        delegatesSparseArray.append(ViewType.TYPE_STOP, new StopDelegate(new SingleObserverAdapter<Pair<Integer, Integer>>() {
            @Override
            public void onSuccess(Pair<Integer, Integer> segmentAndStopIndexes) {
                publishSubject.onNext(segmentAndStopIndexes);
            }
        }));
        publishSubject.subscribe(observer);
    }

    public void setRoute(@NonNull Route route) {
        Provider provider = route.getProvider();
        viewTypes.clear();
        String displayName = provider.getDisplayName();
        viewTypes.add(new RouteHeaderViewType(provider.getIcon(), displayName == null ? provider.getId() : displayName, route.getType(),
                route.getDuration(), route.getPrice()));
        Observable.range(0, route.getSegments().size())
                .map(segmentIndex -> {
                    Segment segment = route.getSegments().get(segmentIndex);
                    int color = Color.parseColor(segment.getColor());
                    viewTypes.add(new SegmentViewType(segmentIndex, segment.getName(), segment.getTravelMode(), segment.getDescription(),
                            color, segment.getIcon()));
                    Observable.range(0, segment.getStops().size())
                            .map(stopIndex -> {
                                Stop stop = segment.getStops().get(stopIndex);
                                boolean isLast = segmentIndex == route.getSegments().size() - 1
                                        && stopIndex == segment.getStops().size() - 1;
                                viewTypes.add(new StopViewType(segmentIndex, stopIndex, stop.getName(), stop.getDate(), color, isLast));
                                return isLast;
                            })
                            .subscribe();
                    return color;
                })
                .subscribe();
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
                int type;
                ViewType viewType = viewTypes.get(position);
                if (viewType instanceof SegmentViewType) {
                    type = ViewType.TYPE_SEGMENT;
                } else {
                    type = ViewType.TYPE_STOP;
                }
                return type;
        }
    }
}
