package com.example.itunesmusic.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums_table")
data class AlbumLocalModel(

    @PrimaryKey(autoGenerate = true)
    val id : Int,

    val img : String,
    val group_name : String,
    val album_name : String,
    val publish_year : Int
)