package com.example.itunesmusic.data.remote.api

import com.example.itunesmusic.data.remote.impl.ItunesApiService
import com.example.itunesmusic.data.remote.retrofit.retrofit
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object ItunesApi {
    val retrofitService : ItunesApiService by lazy {
        retrofit.create(ItunesApiService::class.java)
    }
}