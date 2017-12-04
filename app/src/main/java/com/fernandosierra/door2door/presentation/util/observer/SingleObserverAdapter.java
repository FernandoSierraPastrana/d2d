package com.fernandosierra.door2door.presentation.util.observer;

import io.reactivex.observers.DisposableSingleObserver;
import timber.log.Timber;

public abstract class SingleObserverAdapter<T> extends DisposableSingleObserver<T> {
    @Override
    public void onSuccess(T t) {
        // Optional
    }

    @Override
    public void onError(Throwable e) {
        Timber.e(e);
    }
}
