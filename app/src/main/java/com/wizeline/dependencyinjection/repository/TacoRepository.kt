package com.wizeline.dependencyinjection.repository

import com.wizeline.dependencyinjection.data.Taco
import com.wizeline.dependencyinjection.data.TacoDataSource
import javax.inject.Inject

class TacoRepository @Inject constructor(
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

    fun removeTaco(taco: Taco) {
        tacoLocalDataSource.removeTaco(taco)
    }
}