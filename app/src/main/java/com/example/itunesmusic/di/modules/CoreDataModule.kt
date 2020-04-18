package com.example.itunesmusic.di.modules

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
class CoreDataModule {

    @Singleton
    @Provides
    fun provideScalarsFactory(): ScalarsConverterFactory = ScalarsConverterFactory.create()

    @Singleton
    @Provides
    fun provideJsonAdapterFactory() = KotlinJsonAdapterFactory()

    @Singleton
    @Provides
    fun provideCoroutineFactory() = CoroutineCallAdapterFactory()

    @Singleton
    @Provides
    fun provideMoshi(jsonFactory : KotlinJsonAdapterFactory): Moshi
            = Moshi.Builder().add(jsonFactory).build()

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi)
            : MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)
}