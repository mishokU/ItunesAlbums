package com.example.itunesmusic.domain.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.itunesmusic.data.remote.albums.AlbumsRepository
import com.example.itunesmusic.data.remote.service.ItunesService
import com.example.itunesmusic.di.utils.ChildWorkerFactory
import retrofit2.HttpException
import javax.inject.Inject

class RefreshAlbumsWorker(
    private val repository: AlbumsRepository,
    private val service: ItunesService,
    val context: Context,
    params: WorkerParameters):
    CoroutineWorker(context, params) {

    companion object{
        const val WORK_NAME = "RefreshAlbums"
    }

    override suspend fun doWork(): Result {
        return try{
            repository.refreshAlbums()
            Result.success()
        } catch (exception : HttpException){
            return Result.failure()
        }
    }

    class Factory @Inject constructor(
        private val repository: AlbumsRepository,
        private val service: ItunesService
    ): ChildWorkerFactory {

        override fun create(context: Context, params: WorkerParameters): CoroutineWorker {
            return RefreshAlbumsWorker(repository, service, context, params)
        }
    }
}