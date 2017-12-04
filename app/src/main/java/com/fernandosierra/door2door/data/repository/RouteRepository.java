package com.fernandosierra.door2door.data.repository;

import android.support.annotation.NonNull;

import com.fernandosierra.door2door.data.LocalDataParser;
import com.fernandosierra.door2door.data.source.ProviderDataSource;
import com.fernandosierra.door2door.data.source.RouteDataSource;
import com.fernandosierra.door2door.domain.model.Route;
import com.fernandosierra.door2door.presentation.util.SchedulersHelper;
import com.fernandosierra.door2door.presentation.util.observer.SingleObserverAdapter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class RouteRepository {
    private final RouteDataSource routeDataSource;
    private final ProviderDataSource providerDataSource;
    private final LocalDataParser localDataParser;

    @Inject
    RouteRepository(@NonNull RouteDataSource routeDataSource, @NonNull ProviderDataSource providerDataSource,
                    @NonNull LocalDataParser localDataParser) {
        this.routeDataSource = routeDataSource;
        this.providerDataSource = providerDataSource;
        this.localDataParser = localDataParser;
    }

    public void loadLocalDataIfItIsNecessary(@NonNull String localJson, @NonNull DisposableCompletableObserver observer) {
        if (routeDataSource.isEmpty() || providerDataSource.isEmpty()) {
            Single.just(localDataParser.readAndParseJson(localJson))
                    .flatMapCompletable(pair -> Completable.create(emitter -> {
                        providerDataSource.createOrUpdate(pair.getFirst());
                        Observable.fromIterable(pair.getSecond())
                                .map(route -> {
                                    route.setProvider(providerDataSource.getByPrimaryKey(route.getProviderId(), null));
                                    return route;
                                })
                                .toList()
                                .subscribe(new SingleObserverAdapter<List<Route>>() {
                                    @Override
                                    public void onSuccess(List<Route> routes) {
                                        routeDataSource.deleteAll();
                                        routeDataSource.create(routes);
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
}
