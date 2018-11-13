package com.lloyds.artistsearch.base

import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseDaggerActivity<V, P : BasePresenter<V>> : DaggerAppCompatActivity() {

    @Inject
    lateinit var presenter: P

    abstract val view: V

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        presenter.onViewAttached(view)
//    }

    override fun onStart() {
        super.onStart()
        presenter.onViewAttached(view)
    }

    override fun onDestroy() {
        presenter.onViewDetached()
        super.onDestroy()
    }
}