package me.kennyvaldivia.riway.di

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class, ActivityComponent::class)
@AssistedModule
@Module
interface AssistedInjectModule