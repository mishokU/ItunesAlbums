package com.example.itunesmusic.di.keys

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class ViewModelKey(
    val values: KClass<out ViewModel>
)