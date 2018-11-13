package com.lloyds.artistsearch.injection.module

import com.lloyds.artistsearch.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity
}