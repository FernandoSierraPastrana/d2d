package com.fernandosierra.door2door.presentation.base;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends BaseView> {
    private final WeakReference<V> viewRef;

    public BasePresenter(@NonNull V view) {
        this.viewRef = new WeakReference<>(view);
    }

    public void view(@NonNull SecureViewCallback<V> secureViewCallback) {
        V view = viewRef.get();
        if (view != null) {
            secureViewCallback.execute(view);
        }
    }

    public interface SecureViewCallback<V extends BaseView> {
        void execute(@NonNull V view);
    }
}
