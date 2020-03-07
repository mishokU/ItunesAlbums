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

    private val database = AlbumsDatabase.getDatabase(application, coroutineScope)
    private val repository = AlbumsRepository(database)

    init {
        coroutineScope.launch {
            repository.refreshAlbums()
        }
    }

    val allAlbumsProperty = repository.albums

    fun showFullAlbum(it: AlbumModel) {
        _fullAlbumDescription.value = it
    }

    fun showFullAlbumComplete(){
        _fullAlbumDescription.value = null
    }


}