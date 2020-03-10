package com.example.itunesmusic.data.local.converters

import android.provider.MediaStore
import com.example.itunesmusic.data.local.models.AlbumLocalModel
import com.example.itunesmusic.data.local.models.AlbumPlayListLocalModel
import com.example.itunesmusic.data.remote.models.AlbumRemoteModel
import com.example.itunesmusic.data.remote.models.AlbumsPlayListRemoteModel
import com.example.itunesmusic.domain.models.AlbumModel
import com.example.itunesmusic.domain.models.SingleTrackModel


fun asUIModel(it : List<AlbumLocalModel>) : List<AlbumModel> {
    return it.map {
        AlbumModel(
            id = it.id,
            album_name = it.album_name,
            group_name = it.group_name,
            publish_year = it.publish_year,
            img = it.img,
            genre = it.genre,
            collectionId = it.collectionId,
            price = it.price
        )
    }
}

fun asLocalModel(it : List<AlbumRemoteModel>) : List<AlbumLocalModel> {
    return it.map{
        AlbumLocalModel(
            id = it.artistId,
            img = it.artworkUrl60,
            group_name = it.artistName,
            album_name = it.collectionName,
            publish_year = it.releaseDate,
            genre = it.primaryGenreName,
            collectionId = it.collectionId,
            price = it.collectionPrice
        )
    }
}

fun asTrackUIModel(it : List<AlbumPlayListLocalModel>) : List<SingleTrackModel> {
    return it.map {
        SingleTrackModel(
            id = it.id,
            title = it.title,
            time = it.time,
            collectionId = it.collectionId
        )
    }
}

fun asPlayListLocalModel(it : List<AlbumsPlayListRemoteModel>) : List<AlbumPlayListLocalModel> {
    return it.map{
        AlbumPlayListLocalModel(
            id = it.trackId,
            title = it.trackName,
            time = it.trackTimeMillis,
            collectionId = it.collectionId
        )
    }
}

