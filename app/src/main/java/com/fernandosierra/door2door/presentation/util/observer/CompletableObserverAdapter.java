package com.fernandosierra.door2door.presentation.util.observer;

import io.reactivex.observers.DisposableCompletableObserver;
import timber.log.Timber;

public abstract class CompletableObserverAdapter extends DisposableCompletableObserver {
    @Override
    public void onComplete() {
        // Optional
    }

    @Override
    public void onError(Throwable e) {
        Timber.e(e);
    }
}
