package com.example.itunesmusic.domain.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.itunesmusic.data.remote.constants.MAX_NUMBER_OF_ALBUMS_FOR_SEARCH_RESULT
import com.example.itunesmusic.data.remote.constants.RequestValues
import com.example.itunesmusic.data.remote.album.AlbumRemoteModel
import com.example.itunesmusic.data.remote.service.ItunesService
import com.example.itunesmusic.di.utils.CoroutineScopeIO
import com.example.itunesmusic.di.utils.CoroutineScopeMain
import com.example.itunesmusic.domain.converters.NetworkStatus
import com.example.itunesmusic.domain.models.AlbumModel
import kotlinx.coroutines.*
import javax.inject.Inject

class SearchAlbumsViewModel @Inject constructor(
    private val itunesService: ItunesService,
    @CoroutineScopeMain
    private val coroutineScope: CoroutineScope
) : ViewModel() {

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

    //Search album via retrofit iTunes api with deferred type of object
    fun searchAlbum(it: String) {
        coroutineScope.launch {
            try {
                val albumsDeferred = itunesService.getAlbumsAsync(
                    name = it,
                    entity = RequestValues.ALBUM_ENTITY,
                    media = RequestValues.MUSIC_MEDIA,
                    attribute = RequestValues.ALBUM_TERM_ATTRIBUTE,
                    limit = MAX_NUMBER_OF_ALBUMS_FOR_SEARCH_RESULT
                )

                showStatus(NetworkStatus.LOADING)

                val albums = albumsDeferred.await()

                if(!albums.results.isNullOrEmpty()){
                    showStatus(NetworkStatus.DONE)
                    _searchedAlbums.value = albums.results.asUIModelFromNetwork()
                    //_searchedAlbums.value = asUIModelFromNetwork(albums.results)
                }
            } catch (t: Throwable) {
                showStatus(NetworkStatus.ERROR)
            }
        }
    }

    private suspend fun showStatus(status : NetworkStatus) {
        withContext(Dispatchers.Main) {
            _networkStatus.value = status
        }
    }

    //To convert from network model to UI model
    private fun List<AlbumRemoteModel>.asUIModelFromNetwork() : List<AlbumModel> {
        return map{
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
        coroutineScope.cancel()
        super.onCleared()
    }

    fun showFullAlbum(it: AlbumModel?) {
        _fullAlbumDescription.value = it
    }

    fun showFullAlbumComplete(){
        _fullAlbumDescription.value = null
    }

}