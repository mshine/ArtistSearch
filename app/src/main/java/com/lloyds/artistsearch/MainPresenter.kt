package com.lloyds.artistsearch

import com.lloyds.artistsearch.api.ArtistRepository
import com.lloyds.artistsearch.api.ArtistResult
import com.lloyds.artistsearch.base.BasePresenter
import com.lloyds.artistsearch.injection.module.RxModule
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class MainPresenter @Inject constructor(
    private val artistRepository: ArtistRepository,
    @Named(RxModule.UI) private val uiScheduler: Scheduler
) : BasePresenter<MainPresenter.View>() {

    interface View {
        val onSearchArtist: Observable<String>

        fun setData(artistResult: ArtistResult)
    }

    override fun onViewAttached(view: View) {
        super.onViewAttached(view)

        disposeOnViewDetach(
            view.onSearchArtist
                .switchMap { artistRepository.getArtists(it) }
                .observeOn(uiScheduler)) {
            view.setData(it)
        }
    }
}