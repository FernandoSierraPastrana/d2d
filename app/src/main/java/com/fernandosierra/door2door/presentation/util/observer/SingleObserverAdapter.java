package com.fernandosierra.door2door.presentation.util.observer;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public abstract class SingleObserverAdapter<T> implements SingleObserver<T> {

    @Override
    public void onSubscribe(Disposable d) {
        // Nothing
    }

    @Override
    public void onSuccess(T t) {
        // Optional
    }

    @Override
    public void onError(Throwable e) {
        Timber.e(e);
    }
}
