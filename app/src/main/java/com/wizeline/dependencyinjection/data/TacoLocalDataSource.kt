package com.wizeline.dependencyinjection.data

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TacoLocalDataSource @Inject constructor (
    private val tacoDao: TacoDao
    ): TacoDataSource {

    override suspend fun addTaco(taco: Taco) {
        tacoDao.inserAll(taco)
    }

    override suspend fun getAllTacos(callback: (List<Taco>) -> Unit) {
        val tacos = tacoDao.getAll()
        callback(tacos)
    }

    override suspend fun removeTacos() {
        tacoDao.deleteTable()
    }

    override suspend fun removeTaco(taco: Taco) {
        tacoDao.deleteTaco(taco)
    }
}