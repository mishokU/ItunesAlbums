package com.example.itunesmusic.data.remote.albums

import com.example.itunesmusic.data.remote.constants.MAX_NUMBER_OF_ALBUMS_FOR_SEARCH_RESULT
import com.example.itunesmusic.data.remote.constants.RequestValues
import com.example.itunesmusic.data.remote.service.ItunesService
import javax.inject.Inject

class AllAlbumsDataSource @Inject constructor(private val itunesService: ItunesService) {

    fun fetchAlbumsAsync() = itunesService.getAlbumsAsync(
        name = RequestValues.START_SEARCH,
        entity = RequestValues.ALBUM_ENTITY,
        media = RequestValues.MUSIC_MEDIA,
        attribute = RequestValues.MIX_TERM_ATTRIBUTE,
        limit = MAX_NUMBER_OF_ALBUMS_FOR_SEARCH_RESULT
    )
}