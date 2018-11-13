package com.lloyds.artistsearch.api

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.Relay
import com.lloyds.artistsearch.BuildConfig
import com.lloyds.artistsearch.SearchRepository
import com.lloyds.artistsearch.injection.module.RxModule
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class ArtistRepository @Inject constructor(
    private val api: ArtistServiceApi,
    searchRepository: SearchRepository,
    @Named(RxModule.IO) private val ioScheduler: Scheduler,
    @Named(RxModule.UI) private val uiScheduler: Scheduler) {

    private val artistsRelay: Relay<ArtistResult> = BehaviorRelay.create()
    val artists: Observable<ArtistResult> = artistsRelay

    init {
        searchRepository.searchTerm
            .subscribeOn(uiScheduler)
            .subscribe { searchMovies(it) }
    }

    private fun searchMovies(searchTerm: String) {
        api.artists(searchTerm, BuildConfig.ARTIST_API_TOKEN)
            .subscribeOn(ioScheduler)
            .toObservable()
            .subscribe { artistsRelay.accept(it) }
    }
}