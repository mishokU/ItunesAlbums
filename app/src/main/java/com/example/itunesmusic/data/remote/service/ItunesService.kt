package com.example.itunesmusic.data.remote.service

import com.example.itunesmusic.data.remote.constants.ApiConstants
import com.example.itunesmusic.data.remote.constants.RequestKeys
import com.example.itunesmusic.data.remote.models.FullAlbumsModel
import com.example.itunesmusic.data.remote.models.FullAlbumsPlayListModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesService {

    /**
    * Example: https://itunes.apple.com/search?term=out&entity=album&media=music&attribute=albumTerm
    */
    @GET(ApiConstants.SEARCH)
    fun getAlbumsAsync(
        @Query(RequestKeys.TERM)name: String,
        @Query(RequestKeys.ENTITY)entity: String,
        @Query(RequestKeys.MEDIA)media : String,
        @Query(RequestKeys.ATTRIBUTE)attribute : String,
        @Query(RequestKeys.LIMIT)limit : Int
    ) : Deferred<FullAlbumsModel>

    /**
     * Example: https://itunes.apple.com/lookup?id=982690853&entity=song&media=music
     * The first item in response list is a collection, all other items are tracks.
     */

    @GET(ApiConstants.LOOKUP)
    fun getTracksByCollectionIdAsync(
        @Query(RequestKeys.COLLECTION_ID) collectionId: Int?,
        @Query(RequestKeys.ENTITY) entityType: String,
        @Query(RequestKeys.MEDIA) mediaType: String
    ): Deferred<FullAlbumsPlayListModel>


}