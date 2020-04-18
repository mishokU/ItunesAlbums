package com.example.itunesmusic.di.keys

import androidx.work.CoroutineWorker
import dagger.MapKey
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class WorkerKey(val value: KClass<out CoroutineWorker>)
