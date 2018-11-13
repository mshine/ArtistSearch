package com.lloyds.artistsearch.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ArtistResult(@SerializedName("results") val results: Results) {

    data class Results(
        @SerializedName("artistmatches") val artistMatches: ArtistMatches
    )

    data class ArtistMatches(
        @SerializedName("artist") val artists: List<Artist>
    )

    data class Artist(
        @SerializedName("name") val name: String,
        @SerializedName("listeners") val listeners: String,
        @SerializedName("url") val url: String,
        @SerializedName("image") val images: List<Artwork>
    ) : Serializable

    data class Artwork(
        @SerializedName("#text") val imageUrl: String,
        @SerializedName("size") val imageSize: String
    ) : Serializable
}

sealed class ImageSize(val size: String) {
    object Small : ImageSize("small")
    object Medium : ImageSize("medium")
    object Large : ImageSize("large")
    object ExtraLarge : ImageSize("extralarge")
    object Mega : ImageSize("mega")
}