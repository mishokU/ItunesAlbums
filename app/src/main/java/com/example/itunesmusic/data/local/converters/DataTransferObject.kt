package com.example.itunesmusic.data.local.converters

import com.example.itunesmusic.data.local.models.AlbumLocalModel
import com.example.itunesmusic.domain.models.AlbumModel


fun asUIModel(it : List<AlbumLocalModel>) : List<AlbumModel> {
    return it.map {
        AlbumModel(
            id = it.id,
            album_name = it.album_name,
            group_name = it.group_name,
            publish_year = it.publish_year,
            img = it.img
        )
    }
}

