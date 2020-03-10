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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Database(entities = [AlbumPlayListLocalModel::class], version = 3, exportSchema = false)
abstract class AlbumsPlayListDatabase : RoomDatabase() {

    abstract fun albumPlayListDao(): AlbumsPlayListDao

    companion object {

        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AlbumsPlayListDatabase? = null

        fun getDatabase(context: Context): AlbumsPlayListDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AlbumsPlayListDatabase::class.java,
                        "tracks_database"
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