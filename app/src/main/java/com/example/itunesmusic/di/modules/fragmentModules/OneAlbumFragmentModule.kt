package com.example.itunesmusic.di.modules.fragmentModules

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itunesmusic.di.keys.ViewModelKey
import com.example.itunesmusic.domain.viewModel.OneAlbumViewModel
import com.example.itunesmusic.ui.album.OneAlbumFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    OneAlbumFragmentModule.ProvideViewModel::class
])
abstract class OneAlbumFragmentModule {

    /* Install module into sub component to have access to bound fragment instance */
    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    abstract fun bind(): OneAlbumFragment

    @Module
    class InjectViewModel {

        @Provides
        fun provideOneAlbumViewModel(
            factory: ViewModelProvider.Factory,
            target: Fragment
        ) = ViewModelProvider(target, factory).get(OneAlbumViewModel::class.java)

    }

    @Module
    abstract class ProvideViewModel {

        @Binds
        @IntoMap
        @ViewModelKey(OneAlbumViewModel::class)
        abstract fun provideAlbumsViewModel(viewModel: OneAlbumViewModel): ViewModel

    }
}