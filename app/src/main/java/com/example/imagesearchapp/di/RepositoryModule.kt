package com.example.imagesearchapp.di

import com.example.imagesearchapp.network.Service
import com.example.imagesearchapp.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesRepository(service: Service): ImageRepository {
        return ImageRepository(retroService = service)
    }

}