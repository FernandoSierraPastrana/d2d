package com.fernandosierra.door2door.data.repository;

import android.support.annotation.NonNull;

import com.fernandosierra.door2door.data.LocalDataParser;
import com.fernandosierra.door2door.data.mapper.RRouteMapper;
import com.fernandosierra.door2door.data.model.RRoute;
import com.fernandosierra.door2door.data.source.ProviderDataSource;
import com.fernandosierra.door2door.data.source.RouteDataSource;
import com.fernandosierra.door2door.domain.model.Route;
import com.fernandosierra.door2door.presentation.util.SchedulersHelper;
import com.fernandosierra.door2door.presentation.util.observer.SingleDisposableObserverAdapter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;

@Singleton
public class RouteRepository {
    private final RouteDataSource routeDataSource;
    private final ProviderDataSource providerDataSource;
    private final LocalDataParser localDataParser;
    private final RRouteMapper rRouteMapper;

    @Inject
    RouteRepository(@NonNull RouteDataSource routeDataSource,
                    @NonNull ProviderDataSource providerDataSource,
                    @NonNull LocalDataParser localDataParser,
                    @NonNull RRouteMapper rRouteMapper) {
        this.routeDataSource = routeDataSource;
        this.providerDataSource = providerDataSource;
        this.localDataParser = localDataParser;
        this.rRouteMapper = rRouteMapper;
    }

    public void loadLocalDataIfItIsNecessary(@NonNull String localJson, @NonNull DisposableCompletableObserver observer) {
        if (routeDataSource.isEmpty() || providerDataSource.isEmpty()) {
            Single.just(localDataParser.readAndParseJson(localJson))
                    .flatMapCompletable(pair -> Completable.create(emitter -> {
                        providerDataSource.createOrUpdate(pair.getFirst());
                        Observable.fromIterable(pair.getSecond())
                                .map(route -> {
                                    route.setProviderObject(providerDataSource.getByPrimaryKey(route.getProviderId(), null));
                                    return route;
                                })
                                .toList()
                                .subscribe(new SingleDisposableObserverAdapter<List<RRoute>>() {
                                    @Override
                                    public void onSuccess(List<RRoute> rRoutes) {
                                        routeDataSource.deleteAll();
                                        routeDataSource.create(rRoutes);
                                        emitter.onComplete();
                                    }

                                    @Override
                                    public void onError(Throwable exception) {
                                        super.onError(exception);
                                        emitter.onError(exception);
                                    }
                                });
                    }))
                    .subscribeOn(SchedulersHelper.INSTANCE.getScheduler(SchedulersHelper.REALM))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } else {
            observer.onComplete();
        }
    }

    public void getAllRoutes(@NonNull DisposableSingleObserver<List<Route>> observer) {
        Single.fromCallable(routeDataSource::getAll)
                .flatMapObservable(Observable::fromIterable)
                .map(rRouteMapper::transform)
                .toList()
                .subscribeOn(SchedulersHelper.INSTANCE.getScheduler(SchedulersHelper.REALM))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
