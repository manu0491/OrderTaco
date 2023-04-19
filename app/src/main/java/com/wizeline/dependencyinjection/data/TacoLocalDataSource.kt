package com.wizeline.dependencyinjection.data

class TacoLocalDataSource(
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