
package com.example.itunesmusic.di.utils

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters

interface ChildWorkerFactory {
  fun create(context: Context, params: WorkerParameters): CoroutineWorker
}