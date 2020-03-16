package com.example.itunesmusic.domain.work

import android.content.Context
import android.service.voice.AlwaysOnHotwordDetector
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.itunesmusic.data.local.database.AlbumsDatabase
import com.example.itunesmusic.data.local.database.AlbumsPlayListDatabase
import com.example.itunesmusic.data.repository.AlbumsRepository
import retrofit2.HttpException

class RefreshAlbumsWorker(context: Context, params: WorkerParameters):
    CoroutineWorker(context, params) {

    companion object{
        const val WORK_NAME = "RefreshAlbums"
    }

    override suspend fun doWork(): Result {
        val database = AlbumsDatabase.getDatabase(applicationContext)
        val songsDatabase = AlbumsPlayListDatabase.getDatabase(applicationContext)
        val repository = AlbumsRepository(database, songsDatabase)

        return try{
            repository.refreshAlbums()
            Result.success()
        } catch (exception : HttpException){
            return Result.failure()
        }


    }
}