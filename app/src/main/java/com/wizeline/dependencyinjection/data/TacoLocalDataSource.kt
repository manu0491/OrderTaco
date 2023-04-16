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

    private val executorService: ExecutorService = Executors.newFixedThreadPool(4)
    private val mainThreadHandler by lazy {
        Handler(Looper.getMainLooper())
    }

    override fun addTaco(taco: Taco) {
        executorService.execute {
            tacoDao.inserAll(taco)
        }
    }

    override fun getAllTacos(callback: (List<Taco>) -> Unit) {
        executorService.execute {
            val tacos = tacoDao.getAll()
            mainThreadHandler.post { callback(tacos) }
        }
    }

    override fun removeTacos() {
        executorService.execute {
            tacoDao.deleteTable()
        }
    }

    override fun removeTaco(taco: Taco) {
        executorService.execute {
            tacoDao.deleteTaco(taco)
        }
    }
}