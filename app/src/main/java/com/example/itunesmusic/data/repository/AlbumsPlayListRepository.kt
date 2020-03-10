package com.example.itunesmusic.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.itunesmusic.data.local.converters.asPlayListLocalModel
import com.example.itunesmusic.data.local.converters.asTrackUIModel
import com.example.itunesmusic.data.local.database.AlbumsPlayListDatabase
import com.example.itunesmusic.data.remote.api.ItunesApi
import com.example.itunesmusic.data.remote.constants.RequestValues
import com.example.itunesmusic.domain.converters.NetworkStatus
import com.example.itunesmusic.domain.models.SingleTrackModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumsPlayListRepository(val database : AlbumsPlayListDatabase) {

    private val _networkStatus = MutableLiveData<NetworkStatus>()
    val networkStatus : LiveData<NetworkStatus>
        get() = _networkStatus

    //Get all songs from db and convert that data to UI model
    fun getAllSongs(filter : Int) : LiveData<List<SingleTrackModel>> {
        return Transformations.map(database.albumPlayListDao().getAllSongs(filter)) {
            asTrackUIModel(it)
        }
    }

    //Call this function from coroutine on background thread but
    //get callback about network status on main
    suspend fun refreshSongs(filter : Int){
        withContext(Dispatchers.IO) {
            try {

                val playListDeferred = ItunesApi.retrofitService.getTracksByCollectionIdAsync(
                    collectionId = filter,
                    entityType = RequestValues.SONG_ENTITY,
                    mediaType = RequestValues.MUSIC_MEDIA
                )

                withContext(Dispatchers.Main){
                    _networkStatus.value = NetworkStatus.LOADING
                }

                val playList = playListDeferred.await()

                if(playList.results.isNotEmpty()){

                    withContext(Dispatchers.Main) {
                        _networkStatus.value = NetworkStatus.DONE
                    }

                    database.albumPlayListDao().insertAll(asPlayListLocalModel(playList.results))
                }
            } catch (e : Throwable){
                withContext(Dispatchers.Main) {
                    _networkStatus.value = NetworkStatus.ERROR
                }
            }
        }
    }

    suspend fun deleteAllSongs(id : Int) {
        withContext(Dispatchers.IO){
            database.albumPlayListDao().deleteAllSongs(id)
        }
    }
}