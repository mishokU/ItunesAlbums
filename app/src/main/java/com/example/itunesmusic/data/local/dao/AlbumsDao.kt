package com.example.itunesmusic.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.itunesmusic.data.local.models.AlbumLocalModel

@Dao
interface AlbumsDao {

    @Insert
    fun insert(albumLocalModel: AlbumLocalModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list : List<AlbumLocalModel>)

    @Query("Select * from albums_table")
    fun getAlbums() : LiveData<List<AlbumLocalModel>>

    @Query("Delete from albums_table")
    fun deleteAll()


}