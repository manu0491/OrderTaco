package com.wizeline.dependencyinjection.repository

import androidx.annotation.VisibleForTesting
import com.wizeline.dependencyinjection.data.Taco
import com.wizeline.dependencyinjection.data.TacoDataSource
import javax.inject.Inject

class TacoRepository @Inject constructor(
    @get:VisibleForTesting val tacoLocalDataSource: TacoDataSource
) {
    suspend fun addLocalTaco(taco: Taco){
        tacoLocalDataSource.addTaco(taco)
    }
    suspend fun getLocalAllTacos(): List<Taco>{
       return tacoLocalDataSource.getAllTacos()
    }
    suspend fun removeLocalTacos(){
        tacoLocalDataSource.removeTacos()
    }

    suspend fun removeTaco(taco: Taco) {
        tacoLocalDataSource.removeTaco(taco)
    }
}