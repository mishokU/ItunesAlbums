package com.example.itunesmusic.data.remote.models

import com.example.itunesmusic.data.remote.albums.AlbumsPlayListRemoteModel
import com.example.itunesmusic.data.remote.constants.ItunesResponseKeys
import com.google.gson.annotations.SerializedName

data class FullAlbumsPlayListModel(

    @SerializedName(ItunesResponseKeys.RESULT_COUNT)
    val resultCount: Int,

    @SerializedName(ItunesResponseKeys.RESULTS)
    val results: List<AlbumsPlayListRemoteModel>
)