package com.example.itunesmusic.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.itunesmusic.data.local.dao.AlbumsDao
import com.example.itunesmusic.data.local.models.AlbumLocalModel
import kotlinx.coroutines.*

@Database(entities = [AlbumLocalModel::class], version = 1, exportSchema = false)
abstract class AlbumsDatabase : RoomDatabase() {

    abstract fun albumsDao(): AlbumsDao

    //for check there is actually works
    private class CitiesDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    withContext(Dispatchers.IO) {


                        val wordDao = database.albumsDao()

                        // Delete all content here.
                        wordDao.deleteAll()

                        // Add sample words.
                        wordDao.insert(AlbumLocalModel(
                            id = 1,
                            img = "fefe",
                            group_name = "Kis kis",
                            album_name = "Summer",
                            publish_year = 2020
                        ))
                        wordDao.insert(AlbumLocalModel(
                            id = 2,
                            img = "fefe",
                            group_name = "Macklemore",
                            album_name = "Winter",
                            publish_year = 2018
                        ))
                    }
                }
            }
        }
    }

    companion object {

        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AlbumsDatabase? = null

        fun getDatabase(context: Context, coroutineScope: CoroutineScope): AlbumsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AlbumsDatabase::class.java,
                        "albums_database"
                    )
                        .addCallback(CitiesDatabaseCallback(coroutineScope))
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}