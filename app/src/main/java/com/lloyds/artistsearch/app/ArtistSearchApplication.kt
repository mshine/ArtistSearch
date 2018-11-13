package com.lloyds.artistsearch.app

import com.lloyds.artistsearch.BuildConfig
import com.lloyds.artistsearch.injection.component.ApplicationComponent
import com.lloyds.artistsearch.injection.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class ArtistSearchApplication : DaggerApplication() {

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        initLogger()
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        applicationComponent = DaggerApplicationComponent.builder().context(this).build()
        return applicationComponent
    }
}