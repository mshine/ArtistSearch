package com.lloyds.artistsearch.injection.component

import android.content.Context
import com.lloyds.artistsearch.app.ArtistSearchApplication
import com.lloyds.artistsearch.injection.module.ActivityBindingModule
import com.lloyds.artistsearch.injection.module.ApiModule
import com.lloyds.artistsearch.injection.module.RxModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        ApiModule::class,
        RxModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<ArtistSearchApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): ApplicationComponent
    }

}