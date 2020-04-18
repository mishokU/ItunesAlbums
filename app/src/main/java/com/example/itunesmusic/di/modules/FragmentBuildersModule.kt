package com.example.itunesmusic.di.modules

import com.example.itunesmusic.ui.albums.AllAlbumsFragment
import com.example.itunesmusic.ui.album.OneAlbumFragment
import com.example.itunesmusic.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeAllAlbumsFragment() : AllAlbumsFragment

    @ContributesAndroidInjector
    abstract fun contributeOneAlbumFragment() : OneAlbumFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment() : SearchFragment

}