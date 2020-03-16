package com.example.itunesmusic.ui.base

import android.app.Application
import androidx.work.*
import com.example.itunesmusic.domain.work.RefreshAlbumsWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AlbumsApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .setRequiresCharging(true)
                .apply{
                    setRequiresDeviceIdle(true)
                }.build()


            val repeatUpdateAlbums = PeriodicWorkRequestBuilder<RefreshAlbumsWorker>(
                1,
                TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(applicationContext)
                .enqueueUniquePeriodicWork(
                    RefreshAlbumsWorker.WORK_NAME,
                    ExistingPeriodicWorkPolicy.KEEP,
                    repeatUpdateAlbums
                )
        }
    }
}