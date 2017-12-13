package com.fernandosierra.door2door.presentation.util.observer;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public abstract class ObserverAdapter<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
        // Nothing
    }

    @Override
    public void onNext(T t) {
        // Optional
    }

    @Override
    public void onError(Throwable e) {
        Timber.e(e);
    }

    @Override
    public void onComplete() {
        // Optional
    }
}
