package com.lloyds.artistsearch

import com.lloyds.artistsearch.api.ArtistRepository
import com.lloyds.artistsearch.api.ArtistResult
import com.lloyds.artistsearch.api.Result
import com.nhaarman.mockitokotlin2.*
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    private val artistRepository = mock<ArtistRepository>()

    private val view = mock<MainPresenter.View>()

    private val onSearchArtist = PublishSubject.create<String>()
    private val getArtists = PublishSubject.create<Result<ArtistResult>>()

    @Before
    fun setUp() {
        whenever(view.onSearchArtist).thenReturn(onSearchArtist)
        whenever(artistRepository.getArtists(any())).thenReturn(getArtists)

        val presenter = MainPresenter(artistRepository, Schedulers.trampoline())
        presenter.onViewAttached(view)
    }

    @Test
    fun `when artist searched and artists fetched from repository then set data on view`() {
        onSearchArtist.onNext("foo")
        val artistResult = mock<ArtistResult>()
        getArtists.onNext(Result.Success(artistResult))

        verify(view).setData(artistResult)
        verify(view, never()).showError()
    }

    @Test
    fun `when artist searched and artists empty from repository then show error`() {
        onSearchArtist.onNext("")
        getArtists.onNext(Result.Failure(Throwable()))

        verify(view, never()).setData(any())
        verify(view).showError()
    }
}