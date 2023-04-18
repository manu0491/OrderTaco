package com.wizeline.dependencyinjection.data

interface TacoDataSource {
    suspend fun addTaco(taco: Taco)
    suspend fun getAllTacos(callback: (List<Taco>) -> Unit)
    suspend fun removeTacos()

    suspend fun removeTaco(taco: Taco)
}