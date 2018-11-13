package com.lloyds.artistsearch

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.view.RxView
import com.lloyds.artistsearch.api.ArtistResult
import com.lloyds.artistsearch.base.BaseDaggerActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseDaggerActivity<MainPresenter.View, MainPresenter>(), MainPresenter.View {

    override val view: MainPresenter.View = this

    private var artistList: List<ArtistResult.Artist> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.hasFixedSize()
        recycler_view.adapter = ListAdapter(this, artistList)
    }

    override val onSearchArtist: Observable<String> by lazy {
        RxView.clicks(search_button).map { search_edit_text.text.toString() }.share()
    }

    override fun setData(artistsList: ArtistResult) {
        artistList = artistsList.results.artistMatches.artists
        recycler_view.adapter = ListAdapter(this, artistList)
    }
}
