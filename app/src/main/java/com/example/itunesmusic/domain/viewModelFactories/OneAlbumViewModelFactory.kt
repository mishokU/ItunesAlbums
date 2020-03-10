package com.example.itunesmusic.domain.viewModelFactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itunesmusic.domain.models.AlbumModel
import com.example.itunesmusic.domain.viewModel.AlbumsViewModel
import com.example.itunesmusic.domain.viewModel.OneAlbumViewModel


class OneAlbumViewModelFactory(
    private val albumModel: AlbumModel,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OneAlbumViewModel::class.java)) {
            return OneAlbumViewModel(albumModel,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}