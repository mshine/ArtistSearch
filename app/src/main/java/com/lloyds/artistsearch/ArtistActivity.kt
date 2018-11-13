package com.lloyds.artistsearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lloyds.artistsearch.api.ArtistResult
import com.lloyds.artistsearch.api.ImageSize
import com.lloyds.artistsearch.injection.module.GlideApp
import kotlinx.android.synthetic.main.activity_artist.*

class ArtistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist)

        val artist = intent?.extras?.getSerializable("artist") as ArtistResult.Artist

        loadArtistImage(artist)
        setArtistText(artist)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun loadArtistImage(artist: ArtistResult.Artist) {
        GlideApp.with(this)
            .load(artist.images.firstOrNull { it.imageSize == ImageSize.ExtraLarge.size }?.imageUrl)
            .error(R.drawable.icn_sad)
            .into(artist_image)
    }

    private fun setArtistText(artist: ArtistResult.Artist) {
        artist_name.text = getString(R.string.artist_name, artist.name)
        artist_listeners.text = artist.listeners
        artist_listeners.text = getString(R.string.listeners, artist.listeners)
        artist_url.text = artist.url
    }
}
