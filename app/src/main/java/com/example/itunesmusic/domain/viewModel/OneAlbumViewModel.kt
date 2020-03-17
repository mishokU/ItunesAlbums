package com.example.itunesmusic.domain.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.itunesmusic.data.local.database.ITunesDatabase
import com.example.itunesmusic.data.repository.AlbumsPlayListRepository
import com.example.itunesmusic.domain.models.AlbumModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OneAlbumViewModel(private val albumModel: AlbumModel, application: Application) : AndroidViewModel(application) {

    private val coroutineJob = Job()
    private val coroutineScope = CoroutineScope(coroutineJob + Dispatchers.Main)

    private val database = ITunesDatabase.getDatabase(application)
    private val repository = AlbumsPlayListRepository(database)


    //After load all play list from repository and network status
    val playList = repository.getAllSongs(albumModel.collectionId)
    val networkStatus = repository.networkStatus

    init{
        refreshSongs()
    }

    fun refreshSongs() = coroutineScope.launch{
        repository.refreshSongs(albumModel.collectionId)
    }

    override fun onCleared() {
        coroutineJob.cancel()
        super.onCleared()
    }

    fun deleteAllSongs(id : Int) {
        coroutineScope.launch {
            repository.deleteAllSongs(id)
        }
    }
}