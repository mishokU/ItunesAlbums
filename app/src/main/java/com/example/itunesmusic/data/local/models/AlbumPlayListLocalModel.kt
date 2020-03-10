package com.example.itunesmusic.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks_table")
data class AlbumPlayListLocalModel(

    @PrimaryKey
    val id : Int = 0,
    val title : String = "",
    val time : Int = 0,
    val collectionId : Int = 0
)