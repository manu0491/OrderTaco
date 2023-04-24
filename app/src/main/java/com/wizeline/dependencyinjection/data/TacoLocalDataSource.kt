package com.wizeline.dependencyinjection.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TacoLocalDataSource @Inject constructor (
    private val tacoDao: TacoDao
    ): TacoDataSource {

    override suspend fun addTaco(taco: Taco) {
        tacoDao.inserAll(taco)
    }

    override suspend fun getAllTacos(): List<Taco> {
       return tacoDao.getAll()
    }

    override suspend fun removeTacos() {
        tacoDao.deleteTable()
    }

    override suspend fun removeTaco(taco: Taco) {
        tacoDao.deleteTaco(taco)
    }
}