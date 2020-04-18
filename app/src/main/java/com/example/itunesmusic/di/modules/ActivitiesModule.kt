package com.example.itunesmusic.di.modules

import com.example.itunesmusic.di.modules.fragmentModules.AllAlbumsFragmentModule
import com.example.itunesmusic.di.modules.fragmentModules.OneAlbumFragmentModule
import com.example.itunesmusic.di.modules.fragmentModules.SearchFragmentModule
import com.example.itunesmusic.ui.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivitiesModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity() : MainActivity
}