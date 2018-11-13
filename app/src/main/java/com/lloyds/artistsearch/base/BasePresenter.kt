package com.lloyds.artistsearch.base

import androidx.annotation.CallSuper
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

abstract class BasePresenter<T> {
    private val attachedDisposables = CompositeDisposable()
    protected var view: T? = null

    private val errorConsumer: (Throwable) -> Unit
        get() = { throwable ->
                Timber.e(throwable.message)
                throwable.printStackTrace()
        }

    private val isViewAttached: Boolean
        get() = view != null

    @CallSuper
    open fun onViewAttached(view: T) {
        if (isViewAttached) {
            throw IllegalStateException(
                "View " + this.view + " is already attached. Cannot attach " + view
            )
        }
        this.view = view
    }

    @CallSuper
    fun onViewDetached() {
        if (!isViewAttached) {
            throw IllegalStateException("View is already detached")
        }
        view = null

        attachedDisposables.clear()
    }

    @CallSuper
    protected fun <P> disposeOnViewDetach(observable: Observable<P>, consumer: (P) -> Unit) {
        attachedDisposables.add(observable.subscribe(consumer, errorConsumer))
    }
}
