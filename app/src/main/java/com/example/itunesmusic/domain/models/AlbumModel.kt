package com.example.itunesmusic.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumModel(
    val id: Int,
    val img : String,
    val group_name : String,
    val album_name : String,
    val publish_year : String,
    val collectionId : Int,
    val price : Double,
    val genre : String
) : Parcelable