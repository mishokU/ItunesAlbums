package com.example.itunesmusic.di.modules

import com.example.itunesmusic.di.keys.WorkerKey
import com.example.itunesmusic.di.utils.ChildWorkerFactory
import com.example.itunesmusic.domain.work.RefreshAlbumsWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(RefreshAlbumsWorker::class)
    internal abstract fun bindMyWorkerFactory(worker: RefreshAlbumsWorker.Factory): ChildWorkerFactory
}