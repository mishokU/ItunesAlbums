package com.example.itunesmusic.domain.viewModelFactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itunesmusic.domain.viewModel.AlbumsViewModel
import com.example.itunesmusic.domain.viewModel.SearchAlbumsViewModel


class SearchAlbumsViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchAlbumsViewModel::class.java)) {
            return SearchAlbumsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}