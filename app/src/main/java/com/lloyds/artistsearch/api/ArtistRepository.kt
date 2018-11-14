package com.lloyds.artistsearch.api

import com.lloyds.artistsearch.BuildConfig
import com.lloyds.artistsearch.injection.module.RxModule
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class ArtistRepository @Inject constructor(
    private val api: ArtistServiceApi,
    @Named(RxModule.IO) private val ioScheduler: Scheduler) {

    fun getArtists(searchTerm: String): Observable<Result<ArtistResult>> {
        return api.artists(searchTerm, BuildConfig.ARTIST_API_TOKEN)
            .subscribeOn(ioScheduler)
            .toObservable()
            .toNetworkResult()
            .share()
    }
}