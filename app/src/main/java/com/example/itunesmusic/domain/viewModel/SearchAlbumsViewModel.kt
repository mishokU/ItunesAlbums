package com.example.itunesmusic.domain.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.itunesmusic.data.remote.api.ItunesApi
import com.example.itunesmusic.data.remote.constants.MAX_NUMBER_OF_ALBUMS_FOR_SEARCH_RESULT
import com.example.itunesmusic.data.remote.constants.RequestValues
import com.example.itunesmusic.data.remote.models.AlbumRemoteModel
import com.example.itunesmusic.domain.converters.NetworkStatus
import com.example.itunesmusic.domain.models.AlbumModel
import kotlinx.coroutines.*

class SearchAlbumsViewModel(application: Application) : AndroidViewModel(application) {

    //Object for remember one single album
    private val _fullAlbumDescription = MutableLiveData<AlbumModel>()
    val fullAlbumDescription : LiveData<AlbumModel>
        get() = _fullAlbumDescription

    private val _networkStatus = MutableLiveData<NetworkStatus>()
    val networkStatus : LiveData<NetworkStatus>
        get() = _networkStatus

    //All searched albums by filter
    private val _searchedAlbums = MutableLiveData<List<AlbumModel>>()
    val searchedAlbums : LiveData<List<AlbumModel>>
        get() = _searchedAlbums


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    //Search album via retrofit iTunes api with deferred type of object
    fun searchAlbum(it: String) {
        coroutineScope.launch {
            try {
                val albumsDeferred = ItunesApi.retrofitService.getAlbumsAsync(
                    name = it,
                    entity = RequestValues.ALBUM_ENTITY,
                    media = RequestValues.MUSIC_MEDIA,
                    attribute = RequestValues.MIX_TERM_ATTRIBUTE,
                    limit = MAX_NUMBER_OF_ALBUMS_FOR_SEARCH_RESULT
                )

                _networkStatus.value = NetworkStatus.LOADING

                val albums = albumsDeferred.await()

                if(!albums.results.isNullOrEmpty()){
                    _networkStatus.value = NetworkStatus.DONE
                    _searchedAlbums.value = asUIModelFromNetwork(albums.results)
                }
            } catch (t: Throwable) {
                _networkStatus.value = NetworkStatus.ERROR
            }
        }
    }

    //To convert from network model to UI model
    private fun asUIModelFromNetwork(it: List<AlbumRemoteModel>): List<AlbumModel> {
        return it.map{
            AlbumModel(
                id = it.artistId,
                img = it.artworkUrl60,
                group_name = it.artistName,
                album_name = it.collectionName,
                publish_year = it.releaseDate,
                collectionId = it.collectionId,
                genre = it.primaryGenreName,
                price = it.collectionPrice
            )
        }
    }

    override fun onCleared() {
        //clear all coroutines in this view model
        viewModelJob.cancel()
        super.onCleared()
    }

    fun showFullAlbum(it: AlbumModel?) {
        _fullAlbumDescription.value = it
    }

    fun showFullAlbumComplete(){
        _fullAlbumDescription.value = null
    }

}