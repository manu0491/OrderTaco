package com.wizeline.dependencyinjection.di

import com.wizeline.dependencyinjection.data.TacoDataSource
import com.wizeline.dependencyinjection.repository.TacoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class TacoRepositoryModule {

    @Provides
    fun providesTacoRepository(tacoLocalDataSource: TacoDataSource): TacoRepository {
        return TacoRepository(tacoLocalDataSource)
    }
}
