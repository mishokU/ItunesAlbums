package com.example.itunesmusic.data.remote.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.itunesmusic.data.local.converters.asLocalModel
import com.example.itunesmusic.data.local.converters.asUIModel
import com.example.itunesmusic.data.local.database.ITunesDatabase
import com.example.itunesmusic.domain.converters.NetworkStatus
import com.example.itunesmusic.domain.models.AlbumModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlbumsRepository @Inject constructor(
    private val database: ITunesDatabase,
    private val albumsDataSource: AllAlbumsDataSource
) {

    private val _networkStatus = MutableLiveData<NetworkStatus>()
    val networkStatus : LiveData<NetworkStatus>
        get() = _networkStatus

    //Get albums model from db and convert it to UI model
    val albums : LiveData<List<AlbumModel>> = Transformations.map(database.albumsDao().getAlbums()) {
        it.asUIModel()
    }

    //Call this function from coroutine on background thread but
    //get callback about network status on main
    suspend fun refreshAlbums() {
        withContext(Dispatchers.IO) {
            try {
                val albumsDeferred = albumsDataSource.fetchAlbumsAsync()

                showStatus(NetworkStatus.LOADING)

                val albums = albumsDeferred.await()

                if(albums.results.isNotEmpty()){
                    showStatus(NetworkStatus.DONE)
                    database.albumsDao().insertAll(albums.results.asLocalModel())
                }
            } catch (e : Throwable){
                showStatus(NetworkStatus.ERROR)
            }
        }
    }

    private suspend fun showStatus(status : NetworkStatus) {
        withContext(Dispatchers.Main) {
            _networkStatus.value = status
        }
    }

    suspend fun deleteAllSongs(){
        withContext(Dispatchers.IO){
            database.songsDao().deleteAll()
        }
    }

    suspend fun deleteAllAlbums() {
        withContext(Dispatchers.IO){
            database.albumsDao().deleteAll()
        }
    }

}