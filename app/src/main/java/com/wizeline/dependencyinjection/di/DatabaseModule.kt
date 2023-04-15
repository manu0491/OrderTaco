package com.wizeline.dependencyinjection.di

import android.content.Context
import androidx.room.Room
import com.wizeline.dependencyinjection.data.AppDatabase
import com.wizeline.dependencyinjection.data.TacoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "taco.db"
        ).build()

    @Provides
    fun providesTacoDao(appDatabase: AppDatabase): TacoDao =
        appDatabase.tacoDao()

}