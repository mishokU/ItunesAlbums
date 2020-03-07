package com.example.itunesmusic.domain.models

data class AlbumModel(
    val id: Int,
    val img : String,
    val group_name : String,
    val album_name : String,
    val publish_year : Int
)