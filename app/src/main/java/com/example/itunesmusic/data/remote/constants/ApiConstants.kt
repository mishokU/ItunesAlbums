package com.example.itunesmusic.data.remote.constants

const val MAX_NUMBER_OF_ALBUMS_FOR_SEARCH_RESULT = 25
const val MAX_NUMBER_OF_ALBUMS_FOR_DEFAULT = 10

object ApiConstants {
    const val BASE_URL = "https://itunes.apple.com"
    const val SEARCH = "/search"
    const val LOOKUP = "/lookup"
}

object RequestKeys {
    const val TERM = "term"
    const val ENTITY = "entity"
    const val MEDIA = "media"
    const val ATTRIBUTE = "attribute"
    const val LIMIT = "limit"
    const val COLLECTION_ID = "id"
}

object RequestValues {
    const val START_SEARCH = "4"
    const val ALBUM_ENTITY = "album"
    const val SONG_ENTITY = "song"
    const val MUSIC_MEDIA = "music"
    const val ALBUM_TERM_ATTRIBUTE = "albumTerm"
    const val MIX_TERM_ATTRIBUTE = "mixTerm"
}

object ItunesResponseKeys {
    const val RESULT_COUNT = "resultCount"
    const val RESULTS = "results"
}