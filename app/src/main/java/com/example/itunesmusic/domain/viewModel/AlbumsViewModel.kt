package com.example.itunesmusic.domain.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.itunesmusic.data.local.database.AlbumsDatabase
import com.example.itunesmusic.data.repository.AlbumsRepository
import com.example.itunesmusic.domain.models.AlbumModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AlbumsViewModel(application: Application) : AndroidViewModel(application) {

    private val _fullAlbumDescription = MutableLiveData<AlbumModel>()
    val fullAlbumDescription : LiveData<AlbumModel>
        get() = _fullAlbumDescription


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = AlbumsDatabase.getDatabase(application)
    private val repository = AlbumsRepository(database)

    init {
        refreshAlbums()
    }

    fun refreshAlbums() = coroutineScope.launch{
        repository.refreshAlbums()
    }

    //Load all albums and status from repository
    val allAlbumsProperty = repository.albums
    val networkStatus = repository.networkStatus

    fun showFullAlbum(it: AlbumModel?) {
        _fullAlbumDescription.value = it
    }

    fun showFullAlbumComplete(){
        _fullAlbumDescription.value = null
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }

    fun deleteAllAlbums() {
        coroutineScope.launch {
            repository.deleteAllAlbums()
        }
    }
}