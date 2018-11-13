package com.lloyds.artistsearch.injection.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lloyds.artistsearch.BuildConfig
import com.lloyds.artistsearch.api.ArtistServiceApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object ApiModule {

    @Provides
    @Singleton
    @JvmStatic
    fun artistSearchApi(client: OkHttpClient, gson: Gson): ArtistServiceApi =
        Retrofit.Builder()
            .baseUrl(BuildConfig.ARTIST_API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(ArtistServiceApi::class.java)

    @Provides
    @Singleton
    @JvmStatic
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    @JvmStatic
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

}