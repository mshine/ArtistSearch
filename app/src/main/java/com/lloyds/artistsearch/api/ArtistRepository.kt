package com.lloyds.artistsearch.api

import com.lloyds.artistsearch.BuildConfig
import com.lloyds.artistsearch.injection.module.RxModule
import io.reactivex.Observable
import io.reactivex.Scheduler
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class ArtistRepository @Inject constructor(
    private val api: ArtistServiceApi,
    private val rxNetwork: RxNetwork,
    @Named(RxModule.IO) private val ioScheduler: Scheduler
) {

    fun getArtists(searchTerm: String): Observable<ArtistResult> {
        return api.artists(searchTerm, BuildConfig.ARTIST_API_TOKEN)
            .subscribeOn(ioScheduler)
            .toObservable()
            .compose(rxNetwork.retryOnFailure())
            .flatMap {
                when (it) {
                    is RxNetwork.Result.Success -> {
                        Observable.just(it.data)
                    }
                    is RxNetwork.Result.Failure -> {
                        val emptyResult = ArtistResult(ArtistResult.Results(ArtistResult.ArtistMatches(emptyList())))
                        return@flatMap when (it.throwable) {
                            is Exception -> Observable.just(emptyResult)
                            is HttpException -> {
                                if (it.throwable.code() == 400) {
                                    Observable.just(emptyResult)
                                } else {
                                    Observable.never()
                                }
                            }
                            is IOException -> {
                                Observable.never()
                            }
                            else -> Observable.just(emptyResult)
                        }
                    }
                }
            }.share()
    }
}