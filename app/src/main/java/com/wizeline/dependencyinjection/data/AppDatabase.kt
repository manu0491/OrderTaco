package com.wizeline.dependencyinjection.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Taco::class), version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun tacoDao(): TacoDao
}