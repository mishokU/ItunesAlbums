package com.example.itunesmusic.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.itunesmusic.data.local.converters.asUIModel
import com.example.itunesmusic.data.local.database.AlbumsDatabase
import com.example.itunesmusic.domain.models.AlbumModel

class AlbumsRepository(private val database : AlbumsDatabase) {

    val albums : LiveData<List<AlbumModel>> = Transformations.map(database.albumsDao().getAlbums()) {
        asUIModel(it)
    }

    fun refreshAlbums() {

    }

}