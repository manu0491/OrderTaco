package com.wizeline.dependencyinjection.repository

import com.wizeline.dependencyinjection.data.Taco
import com.wizeline.dependencyinjection.data.TacoDataSource

class TacoRepository(
    private val tacoLocalDataSource: TacoDataSource
) {
    fun addLocalTaco(taco: Taco){
        tacoLocalDataSource.addTaco(taco)
    }
    fun getLocalAllTacos(callback: (List<Taco>) -> Unit){
        tacoLocalDataSource.getAllTacos(callback)
    }
    fun removeLocalTacos(){
        tacoLocalDataSource.removeTacos()
    }
}