package com.example.itunesmusic.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.itunesmusic.data.local.dao.AlbumsDao
import com.example.itunesmusic.data.local.dao.AlbumsPlayListDao
import com.example.itunesmusic.data.local.models.AlbumLocalModel
import com.example.itunesmusic.data.local.models.AlbumPlayListLocalModel
import kotlinx.coroutines.*

@Database(entities = [AlbumLocalModel::class,
                      AlbumPlayListLocalModel::class],
    version = 1, exportSchema = false)
abstract class ITunesDatabase : RoomDatabase() {

    abstract fun albumsDao(): AlbumsDao
    abstract fun songsDao() : AlbumsPlayListDao

    companion object {

        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ITunesDatabase? = null

        fun getDatabase(context: Context): ITunesDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ITunesDatabase::class.java,
                        "albums_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}