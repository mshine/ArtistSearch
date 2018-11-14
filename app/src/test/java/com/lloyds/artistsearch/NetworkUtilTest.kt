package com.lloyds.artistsearch

import com.lloyds.artistsearch.api.Result
import com.lloyds.artistsearch.api.toNetworkResult
import io.reactivex.Observable
import org.junit.Test

class NetworkUtilTest {

    @Test
    fun toNetworkResult_data_returnSuccess() {
        Observable.just("hello").toNetworkResult().test().assertValue(Result.Success("hello"))
    }

    @Test
    fun toNetworkResult_throwable_returnSuccess() {
        val throwable = Throwable()
        Observable.error<Throwable>(throwable).toNetworkResult().test().assertValue(Result.Failure(throwable))
    }
}