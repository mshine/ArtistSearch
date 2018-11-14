package com.lloyds.artistsearch.api

import io.reactivex.Observable

fun <T> Observable<T>.toNetworkResult(): Observable<Result<T>> {
    return map<Result<T>> { it -> Result.Success(it) }.onErrorReturn { Result.Failure(it) }
}

@Suppress("unused")
sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failure<T>(val throwable: Throwable) : Result<T>()
}