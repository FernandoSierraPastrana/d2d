package com.fernandosierra.door2door.presentation.splash;

import android.support.annotation.NonNull;

import com.fernandosierra.door2door.domain.interactor.SetupInteractor;
import com.fernandosierra.door2door.presentation.base.BasePresenter;
import com.fernandosierra.door2door.presentation.util.Door2DoorUtils;
import com.fernandosierra.door2door.presentation.util.observer.CompletableObserverAdapter;

import javax.inject.Inject;

public class SplashPresenter extends BasePresenter<SplashView> {
    private static final String LOCAL_JSON = "local.json";
    private final SetupInteractor setupInteractor;

    @Inject
    public SplashPresenter(@NonNull SplashView splashView, @NonNull SetupInteractor setupInteractor) {
        super(splashView);
        this.setupInteractor = setupInteractor;
    }

    public void init() {
        view(SplashView::init);
    }

    public void loadLocalData() {
        view(SplashView::showLoader);
        final String localDataJson = Door2DoorUtils.instance.readJsonAsset(LOCAL_JSON);
        if (localDataJson != null) {
            setupInteractor.setupFromLocalData(localDataJson, new CompletableObserverAdapter() {
                @Override
                public void onComplete() {
                    view(SplashView::hideLoader);
                }
            });
        } else {
            view(SplashView::hideLoader);
        }
    }
}
