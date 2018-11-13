package com.lloyds.artistsearch.base;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

public abstract class BasePresenter<T> {
    private final CompositeDisposable attachedDisposables = new CompositeDisposable();
    protected T view;

    @CallSuper
    public void onViewAttached(@NonNull final T view) {
        if (isViewAttached()) {
            throw new IllegalStateException(
                    "View " + this.view + " is already attached. Cannot attach " + view);
        }
        this.view = view;
    }

    @CallSuper
    public void onViewDetached() {
        if (!isViewAttached()) {
            throw new IllegalStateException("View is already detached");
        }
        view = null;

        attachedDisposables.clear();
    }

    @CallSuper
    protected <P> void disposeOnViewDetach(@NonNull final Observable<P> observable, @NonNull final Consumer<P> consumer) {
        attachedDisposables.add(observable.subscribe(consumer, getErrorConsumer()));
    }

    private Consumer<? super Throwable> getErrorConsumer() {
        return (Consumer<Throwable>) throwable -> {
            Timber.e(throwable.getMessage());
            throwable.printStackTrace();
        };
    }

    private boolean isViewAttached() {
        return view != null;
    }
}
