package com.example.itunesmusic.di.components

import android.app.Application
import android.content.Context
import com.example.itunesmusic.di.modules.ActivitiesModule
import com.example.itunesmusic.di.modules.AppModule
import com.example.itunesmusic.ui.application.AlbumsApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivitiesModule::class,
    AppModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build() : AppComponent
    }

    fun inject(application : AlbumsApplication)
}