package com.fernandosierra.door2door.presentation.screens.splash;

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
        secureViewCall(SplashView::init);
    }

    public void loadLocalData() {
        secureViewCall(SplashView::showLoader);
        final String localDataJson = Door2DoorUtils.instance.readJsonAsset(LOCAL_JSON);
        if (localDataJson != null) {
            setupInteractor.setupFromLocalData(localDataJson, new CompletableObserverAdapter() {
                @Override
                public void onComplete() {
                    secureViewCall(view -> view.hideLoader(() -> secureViewCall(SplashView::goRoutes)));
                }

                @Override
                public void onError(Throwable e) {
                    secureViewCall(view -> view.hideLoader(() -> secureViewCall(SplashView::showErrorDialog)));
                }
            });
        } else {
            secureViewCall(view -> view.hideLoader(null));
        }
    }
}
