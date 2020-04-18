package com.example.itunesmusic.di.modules.fragmentModules

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itunesmusic.di.keys.ViewModelKey
import com.example.itunesmusic.domain.viewModel.SearchAlbumsViewModel
import com.example.itunesmusic.ui.search.SearchFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    SearchFragmentModule.ProvideViewModel::class
])
abstract class SearchFragmentModule {

    /* Install module into sub component to have access to bound fragment instance */
    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    abstract fun bind(): SearchFragment

    @Module
    class InjectViewModel {

        @Provides
        fun provideFeatureViewModel(
            factory: ViewModelProvider.Factory,
            target: Fragment
        ) = ViewModelProvider(target, factory).get(SearchAlbumsViewModel::class.java)

    }

    @Module
    abstract class ProvideViewModel {

        @Binds
        @IntoMap
        @ViewModelKey(SearchAlbumsViewModel::class)
        abstract fun provideAlbumsViewModel(viewModel: SearchAlbumsViewModel): ViewModel

    }
}