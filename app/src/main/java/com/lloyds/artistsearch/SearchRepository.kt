package com.lloyds.artistsearch

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor() {

    private val searchTermRelay = BehaviorRelay.create<String>()
    val searchTerm: Observable<String> = searchTermRelay

    fun setSearchTerm(searchTerm: String) = searchTermRelay.accept(searchTerm)
}