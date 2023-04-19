package com.wizeline.dependencyinjection.repository

import com.wizeline.dependencyinjection.data.Taco
import com.wizeline.dependencyinjection.data.TacoDataSource

class TacoRepository(
    private val tacoLocalDataSource: TacoDataSource
) {
    suspend fun addLocalTaco(taco: Taco){
        tacoLocalDataSource.addTaco(taco)
    }
    suspend fun getLocalAllTacos(callback: (List<Taco>) -> Unit){
        tacoLocalDataSource.getAllTacos(callback)
    }
    suspend fun removeLocalTacos(){
        tacoLocalDataSource.removeTacos()
    }

    suspend fun removeTaco(taco: Taco) {
        tacoLocalDataSource.removeTaco(taco)
    }
}