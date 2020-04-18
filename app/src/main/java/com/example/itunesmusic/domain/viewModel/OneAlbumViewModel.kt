package com.example.itunesmusic.domain.viewModel

import androidx.lifecycle.ViewModel
import com.example.itunesmusic.data.remote.album.AlbumsPlayListRepository
import com.example.itunesmusic.di.utils.CoroutineScopeIO
import kotlinx.coroutines.*
import javax.inject.Inject

class OneAlbumViewModel @Inject constructor(
    private val repository: AlbumsPlayListRepository,
    @CoroutineScopeIO
    private val coroutineScope: CoroutineScope
) : ViewModel() {

    var collectionId : Int? = null

    val playList by lazy {
        repository.getAllSongs(collectionId)
    }

    //After load all play list from repository and network status
    val networkStatus = repository.networkStatus

    init {
        refreshSongs()
    }

    fun refreshSongs() = coroutineScope.launch{
        repository.refreshSongs(collectionId)
    }

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }

    fun deleteAllSongs(id : Int?) {
        coroutineScope.launch {
            repository.deleteAllSongs(id)
        }
    }
}