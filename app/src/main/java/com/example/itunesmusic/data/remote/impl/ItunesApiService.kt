package com.example.itunesmusic.data.remote.impl

import com.example.itunesmusic.data.remote.models.AlbumRemoteModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApiService {

    @GET("/data/2.5/weather")
    fun getAlbumsAsync(@Query("q")name: String) : Deferred<List<AlbumRemoteModel>>
}