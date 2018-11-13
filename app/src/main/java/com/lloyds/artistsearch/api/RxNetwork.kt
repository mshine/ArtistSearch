package com.lloyds.artistsearch.api

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RxNetwork @Inject constructor() {

    fun <U> retryOnFailure(): ObservableTransformer<U, Result<U>> = ObservableTransformer { observable ->
        val failures = PublishSubject.create<Result.Failure<U>>()
        Observable.merge(
            observable.retryWhen { attempts ->
                attempts.doOnNext { failures.onNext(Result.Failure(it)) }
                    .map { Attempt(1, it) }
                    .switchMap { attempt ->
                        when (attempt.throwable) {
                            is IOException -> Observable.timer((Math.pow(5.0, attempt.count.toDouble())).toLong(), TimeUnit.SECONDS)
                            is UnknownHostException -> Observable.never()
                            is HttpException -> Observable.never()
                            else -> Observable.error(attempt.throwable)
                        }
                    }
            }.map<Result<U>> { Result.Success(it) },
            failures
        )
    }

    private data class Attempt(val count: Int, val throwable: Throwable)

    sealed class Result<T> {
        class Success<T>(val data: T) : Result<T>()
        class Failure<T>(val throwable: Throwable) : Result<T>()
    }

}