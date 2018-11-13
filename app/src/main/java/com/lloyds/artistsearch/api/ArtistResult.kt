package com.lloyds.artistsearch.api

import com.google.gson.annotations.SerializedName

data class ArtistResult(@SerializedName("results") val results: Results) {

    data class Results(
        @SerializedName("artistmatches") val artistMatches: ArtistMatches
    )

    data class ArtistMatches(
        @SerializedName("artist") val artists: List<Artist>
    )

    data class Artist(
        @SerializedName("name") val name: String,
        @SerializedName("listeners") val listeners: Int,
        @SerializedName("url") val url: String,
        @SerializedName("image") val images: List<Artwork>
    )

    data class Artwork(
        @SerializedName("#text") val imageUrl: String,
        @SerializedName("size") val imageSize: String
    )
}