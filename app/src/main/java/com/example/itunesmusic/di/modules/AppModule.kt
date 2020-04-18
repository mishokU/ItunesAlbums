package com.example.itunesmusic.di.modules

import android.app.Application
import com.example.itunesmusic.data.local.database.ITunesDatabase
import com.example.itunesmusic.data.remote.album.AlbumPlayListRemoteDataSource
import com.example.itunesmusic.data.remote.albums.AllAlbumsDataSource
import com.example.itunesmusic.data.remote.constants.ApiConstants
import com.example.itunesmusic.data.remote.service.ItunesService
import com.example.itunesmusic.di.utils.CoroutineScopeIO
import com.example.itunesmusic.di.utils.CoroutineScopeMain
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {
    
    @Singleton
    @Provides
    fun provideLocalDb(app : Application) = ITunesDatabase.getDatabase(app)

    @Singleton
    @Provides
    fun provideAlbumsDao(db : ITunesDatabase) = db.albumsDao()

    @Singleton
    @Provides
    fun provideSongsDao(db : ITunesDatabase) = db.songsDao()

    @Singleton
    @Provides
    fun providePlayListRemoteDataSource(itunesService: ItunesService)
        = AlbumPlayListRemoteDataSource(itunesService)

    @Singleton
    @Provides
    fun provideAllAlbumsDataSource(itunesService: ItunesService)
        = AllAlbumsDataSource(itunesService)

    @CoroutineScopeIO
    @Provides
    fun provideCoroutineScope() = CoroutineScope(Dispatchers.IO)

    @CoroutineScopeMain
    @Provides
    fun provideCoroutineMainScope() = CoroutineScope(Dispatchers.Main)

    @Singleton
    @Provides
    fun provideItunesService(
        scalarsFactory: ScalarsConverterFactory,
        moshiFactory: MoshiConverterFactory,
        coroutineCallAdapterFactory: CoroutineCallAdapterFactory
    ) = provideService(scalarsFactory, moshiFactory,
        coroutineCallAdapterFactory, ItunesService::class.java)


    private fun createRetrofit(
        scalarsFactory : ScalarsConverterFactory,
        moshiFactory: MoshiConverterFactory,
        coroutineCallAdapterFactory: CoroutineCallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(scalarsFactory)
            .addConverterFactory(moshiFactory)
            .addCallAdapterFactory(coroutineCallAdapterFactory)
            .baseUrl(ApiConstants.BASE_URL)
            .build()
    }

    private fun <T> provideService(
        scalarsFactory : ScalarsConverterFactory,
        moshiFactory: MoshiConverterFactory,
        coroutineCallAdapterFactory: CoroutineCallAdapterFactory,
        clazz: Class<T>
    ) : T {
        return createRetrofit(scalarsFactory, moshiFactory,
            coroutineCallAdapterFactory).create(clazz)
    }
    
}