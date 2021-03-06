package com.example.itunesmusic.data.remote.models

import com.example.itunesmusic.data.remote.album.AlbumRemoteModel
import com.example.itunesmusic.data.remote.constants.ItunesResponseKeys
import com.google.gson.annotations.SerializedName

data class FullAlbumsModel(

    @SerializedName(ItunesResponseKeys.RESULT_COUNT)
    val resultCount : Int,

    @SerializedName(ItunesResponseKeys.RESULTS)
    val results : List<AlbumRemoteModel>
)