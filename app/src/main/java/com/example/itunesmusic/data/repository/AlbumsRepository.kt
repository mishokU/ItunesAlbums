package com.example.itunesmusic.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.itunesmusic.data.local.converters.asLocalModel
import com.example.itunesmusic.data.local.converters.asUIModel
import com.example.itunesmusic.data.local.database.AlbumsDatabase
import com.example.itunesmusic.data.local.database.AlbumsPlayListDatabase
import com.example.itunesmusic.data.remote.api.ItunesApi
import com.example.itunesmusic.data.remote.constants.MAX_NUMBER_OF_ALBUMS_FOR_SEARCH_RESULT
import com.example.itunesmusic.data.remote.constants.RequestValues
import com.example.itunesmusic.domain.converters.NetworkStatus
import com.example.itunesmusic.domain.models.AlbumModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumsRepository(
    private val database : AlbumsDatabase,
    private val songsDatabase: AlbumsPlayListDatabase) {

    private val _networkStatus = MutableLiveData<NetworkStatus>()
    val networkStatus : LiveData<NetworkStatus>
        get() = _networkStatus

    //Get albums model from db and convert it to UI model
    val albums : LiveData<List<AlbumModel>> = Transformations.map(database.albumsDao().getAlbums()) {
        asUIModel(it)
    }

    //Call this function from coroutine on background thread but
    //get callback about network status on main
    suspend fun refreshAlbums() {
        withContext(Dispatchers.IO) {
            try {
                val albumsDeferred = ItunesApi.retrofitService.getAlbumsAsync(
                    name = "4",
                    entity = RequestValues.ALBUM_ENTITY,
                    media = RequestValues.MUSIC_MEDIA,
                    attribute = RequestValues.MIX_TERM_ATTRIBUTE,
                    limit = MAX_NUMBER_OF_ALBUMS_FOR_SEARCH_RESULT
                )

                withContext(Dispatchers.Main) {
                    _networkStatus.value = NetworkStatus.LOADING
                }

                val albums = albumsDeferred.await()

                if(albums.results.isNotEmpty()){
                    withContext(Dispatchers.Main) {
                        _networkStatus.value = NetworkStatus.DONE
                    }
                    database.albumsDao().insertAll(asLocalModel(albums.results))
                }
            } catch (e : Throwable){
                Log.d("status", e.message!!)
                withContext(Dispatchers.Main) {
                    _networkStatus.value = NetworkStatus.ERROR
                }
            }
        }
    }

    suspend fun deleteAllSongs(){
        withContext(Dispatchers.IO){
            songsDatabase.albumPlayListDao().deleteAll()
        }
    }

    suspend fun deleteAllAlbums() {
        withContext(Dispatchers.IO){
            database.albumsDao().deleteAll()
        }
    }

}