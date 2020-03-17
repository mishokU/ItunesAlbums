package com.example.itunesmusic.domain.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.itunesmusic.data.local.database.ITunesDatabase
import com.example.itunesmusic.data.repository.AlbumsRepository
import retrofit2.HttpException

class RefreshAlbumsWorker(val context: Context, params: WorkerParameters):
    CoroutineWorker(context, params) {

    companion object{
        const val WORK_NAME = "RefreshAlbums"
    }

    override suspend fun doWork(): Result {
        val database = ITunesDatabase.getDatabase(applicationContext)
        val repository = AlbumsRepository(database)

        return try{
            repository.refreshAlbums()
            Result.success()
        } catch (exception : HttpException){
            return Result.failure()
        }


    }
}