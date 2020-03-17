package com.example.itunesmusic.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.itunesmusic.data.local.models.AlbumPlayListLocalModel

@Dao
interface AlbumsPlayListDao  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(it : List<AlbumPlayListLocalModel>)

    @Query("Select * from tracks_table where collectionId = :collection_id and time != 0")
    fun getAllSongs(collection_id : Int) : LiveData<List<AlbumPlayListLocalModel>>

    @Query("Delete from tracks_table")
    fun deleteAll()

    @Query("Delete from tracks_table where collectionId = :collection_id")
    fun deleteAllSongs(collection_id : Int)

}