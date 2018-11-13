package com.lloyds.artistsearch.injection.module

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
object RxModule {

    @Provides
    @Singleton
    @Named(UI)
    @JvmStatic
    fun provideUIScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Singleton
    @Named(IO)
    @JvmStatic
    fun provideIoScheduler() = Schedulers.io()

    @Provides
    @Singleton
    @Named(COMPUTATION)
    @JvmStatic
    fun provideComputationScheduler() = Schedulers.computation()

    const val UI = "ui"
    const val IO = "io"
    const val COMPUTATION = "computation"
}