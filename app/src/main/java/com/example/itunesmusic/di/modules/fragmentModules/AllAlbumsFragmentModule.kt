package com.example.itunesmusic.di.modules.fragmentModules

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itunesmusic.di.keys.ViewModelKey
import com.example.itunesmusic.domain.viewModel.AlbumsViewModel
import com.example.itunesmusic.ui.albums.AllAlbumsFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    AllAlbumsFragmentModule.ProvideViewModel::class
])
abstract class AllAlbumsFragmentModule {

    /* Install module into sub component to have access to bound fragment instance */
    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    abstract fun bind(): AllAlbumsFragment

    @Module
    class InjectViewModel {


        @Provides
        fun provideAllAlbumsViewModel(
            factory: ViewModelProvider.Factory,
            target: Fragment
        ) = ViewModelProvider(target, factory).get(AlbumsViewModel::class.java)

    }

    @Module
    abstract class ProvideViewModel {

        @Binds
        @IntoMap
        @ViewModelKey(AlbumsViewModel::class)
        abstract fun provideAlbumsViewModel(viewModel: AlbumsViewModel): ViewModel

    }
}