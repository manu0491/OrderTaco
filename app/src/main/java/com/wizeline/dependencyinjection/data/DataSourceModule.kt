package com.wizeline.dependencyinjection.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindsTacoLocalDataSource(tacoDataSource: TacoLocalDataSource) : TacoDataSource
}