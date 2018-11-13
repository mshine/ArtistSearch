package com.lloyds.artistsearch.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistServiceApi {

    @GET("?method=artist.search&format=json")
    fun artists(@Query("artist") searchTerm: String, @Query("api_key") apiKey: String) : Single<ArtistResult>

}