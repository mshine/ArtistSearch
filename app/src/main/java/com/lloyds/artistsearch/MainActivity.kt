package com.lloyds.artistsearch

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.view.RxView
import com.lloyds.artistsearch.api.ArtistResult
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainPresenter.View, ListAdapter.Listener {

    @Inject
    lateinit var presenter: MainPresenter

    val view: MainPresenter.View = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.hasFixedSize()
        recycler_view.adapter = ListAdapter(this, emptyList(), this)

        presenter.onViewAttached(view)
    }

    override fun onDestroy() {
        presenter.onViewDetached()
        super.onDestroy()
    }

    override val onSearchArtist: Observable<String> by lazy {
        RxView.clicks(search_button).map { search_edit_text.text.toString() }.share()
    }

    override fun setData(artistResult: ArtistResult) {
        (recycler_view.adapter as ListAdapter).apply {
            this.artistList = artistResult.results.artistMatches.artists
            notifyDataSetChanged()
        }
    }

    override fun showError() {
        Toast.makeText(this, getString(R.string.api_error), Toast.LENGTH_LONG).show()
    }

    override fun onItemClick(artist: ArtistResult.Artist) {
        val intent = Intent(this, ArtistActivity::class.java).apply {
            putExtra("artist", artist)
        }
        startActivity(intent)
    }
}
