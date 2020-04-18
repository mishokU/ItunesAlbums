package com.example.itunesmusic.domain.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.itunesmusic.data.local.database.ITunesDatabase
import com.example.itunesmusic.data.remote.albums.AlbumsRepository
import com.example.itunesmusic.di.utils.CoroutineScopeIO
import com.example.itunesmusic.domain.models.AlbumModel
import kotlinx.coroutines.*
import javax.inject.Inject

class AlbumsViewModel @Inject constructor(
    private val repository: AlbumsRepository,
    @CoroutineScopeIO
    private val coroutineScope: CoroutineScope
    )
    : ViewModel() {

    private val _fullAlbumDescription = MutableLiveData<AlbumModel>()
    val fullAlbumDescription : LiveData<AlbumModel>
        get() = _fullAlbumDescription

    init {
        refreshAlbums()
    }

    //Load all albums and status from repository
    val allAlbumsProperty = repository.albums
    val networkStatus = repository.networkStatus

    fun refreshAlbums() = coroutineScope.launch{
        repository.refreshAlbums()
    }

    fun showFullAlbum(it: AlbumModel?) {
        _fullAlbumDescription.value = it
    }

    fun showFullAlbumComplete(){
        _fullAlbumDescription.value = null
    }

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }

    fun deleteAllSongs(){
        coroutineScope.launch {
            repository.deleteAllSongs()
        }
    }

    fun deleteAllAlbums() {
        coroutineScope.launch {
            repository.deleteAllAlbums()
        }
    }
}