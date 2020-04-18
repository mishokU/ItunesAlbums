package com.example.itunesmusic.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itunesmusic.di.factories.ViewModelFactory
import com.example.itunesmusic.di.keys.ViewModelKey
import com.example.itunesmusic.domain.viewModel.AlbumsViewModel
import com.example.itunesmusic.domain.viewModel.OneAlbumViewModel
import com.example.itunesmusic.domain.viewModel.SearchAlbumsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AlbumsViewModel::class)
    abstract fun bindAlbumsViewModel(viewModel: AlbumsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OneAlbumViewModel::class)
    abstract fun bindAlbumViewModel(viewModel: OneAlbumViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchAlbumsViewModel::class)
    abstract fun bindSearchViewModel(viewModel: SearchAlbumsViewModel) : ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory : ViewModelFactory) : ViewModelProvider.Factory

}