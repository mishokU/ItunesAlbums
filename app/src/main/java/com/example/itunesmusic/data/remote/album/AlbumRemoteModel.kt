package com.example.itunesmusic.data.remote.album

data class AlbumRemoteModel(
    val amgArtistId: Int = 0,
    val artistId: Int = 0,
    val artistName: String,
    val artistViewUrl: String = "",
    val artworkUrl100: String = "",
    val artworkUrl60: String = "",
    val collectionCensoredName: String = "",
    val collectionExplicitness: String = "",
    val collectionId: Int = 0,
    val collectionName: String = "",
    val collectionPrice: Double = 0.0,
    val collectionType: String = "",
    val collectionViewUrl: String = "",
    val copyright: String = "",
    val country: String = "",
    val currency: String = "",
    val primaryGenreName: String = "",
    val releaseDate: String = "",
    val trackCount: Int = 0,
    val wrapperType: String = ""
)