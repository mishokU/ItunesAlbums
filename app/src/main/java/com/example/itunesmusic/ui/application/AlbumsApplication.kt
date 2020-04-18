package com.example.itunesmusic.ui.application

import android.app.Activity
import android.app.Application
import androidx.work.*
import com.example.itunesmusic.di.utils.ApplicationInjector
import com.example.itunesmusic.domain.work.RefreshAlbumsWorker
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

//Why i need has activity injection ?
class AlbumsApplication : Application(), HasActivityInjector {

    //What is this ?
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        delayedInit()

        ApplicationInjector.init(this)
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

    //What does this shit do ?
    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }
}