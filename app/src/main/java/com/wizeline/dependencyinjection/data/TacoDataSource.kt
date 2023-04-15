package com.wizeline.dependencyinjection.data

interface TacoDataSource {
    fun addTaco(taco: Taco)
    fun getAllTacos(callback: (List<Taco>) -> Unit)
    fun removeTacos()
}