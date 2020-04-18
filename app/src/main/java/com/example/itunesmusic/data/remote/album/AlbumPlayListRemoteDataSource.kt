package com.example.itunesmusic.data.remote.album

import com.example.itunesmusic.data.remote.constants.RequestValues
import com.example.itunesmusic.data.remote.service.ItunesService
import javax.inject.Inject

class AlbumPlayListRemoteDataSource @Inject constructor(private val itunesService: ItunesService) {

    fun fetchSongsAsync(collectionId : Int?) = itunesService.getTracksByCollectionIdAsync(
        collectionId = collectionId,
        entityType = RequestValues.SONG_ENTITY,
        mediaType = RequestValues.MUSIC_MEDIA
    )

}