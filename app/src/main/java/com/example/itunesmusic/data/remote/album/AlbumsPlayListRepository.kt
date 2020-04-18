package com.example.itunesmusic.data.remote.album

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.itunesmusic.data.local.converters.asPlayListLocalModel
import com.example.itunesmusic.data.local.converters.asTrackUIModel
import com.example.itunesmusic.data.local.dao.AlbumsPlayListDao
import com.example.itunesmusic.domain.converters.NetworkStatus
import com.example.itunesmusic.domain.models.SingleTrackModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlbumsPlayListRepository @Inject constructor(
    private val songsDao : AlbumsPlayListDao,
    private val playListDataSource: AlbumPlayListRemoteDataSource
) {

    private val _networkStatus = MutableLiveData<NetworkStatus>()
    val networkStatus : LiveData<NetworkStatus>
        get() = _networkStatus

    //Get all songs from db and convert that data to UI model
    fun getAllSongs(collection_id : Int?) : LiveData<List<SingleTrackModel>> {
        return Transformations.map(songsDao.getAllSongs(collection_id)) {
         it.asTrackUIModel()
        }
    }

    //Call this function from coroutine on background thread but
    //get callback about network status on main
    suspend fun refreshSongs(collectionId : Int?){
        withContext(Dispatchers.IO) {
            try {
                val playListDeferred = playListDataSource.fetchSongsAsync(collectionId)

                showStatus(NetworkStatus.LOADING)
                val playList = playListDeferred.await()

                if(playList.results.isNotEmpty()){
                    showStatus(NetworkStatus.DONE)
                    songsDao.insertAll(playList.results.asPlayListLocalModel())
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

    suspend fun deleteAllSongs(id : Int?) {
        withContext(Dispatchers.IO){
            songsDao.deleteAllSongs(id)
        }
    }
}