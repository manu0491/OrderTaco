package com.wizeline.dependencyinjection.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wizeline.dependencyinjection.data.Taco
import com.wizeline.dependencyinjection.repository.TacoRepository

class OrderViewModel(
    private val tacoRepository: TacoRepository
): ViewModel() {

    val emptyTaco = Taco(type = "", tortilla = "", timestamp = 0L)
    private val _taco = MutableLiveData<Taco>(emptyTaco)
    val taco: LiveData<Taco> = _taco

    fun setType(type: String) {
        val taco = taco.value
        _taco.value = taco?.copy(type = type)
    }

    fun setTortilla(tortilla: String) {
        val taco = taco.value
        _taco.value = taco?.copy(tortilla = tortilla)
    }

    fun setNote(note: String) {
        val taco = taco.value
        _taco.value = taco?.copy(note = note)
    }

    private fun addLocalTaco(taco: Taco){
        tacoRepository.addLocalTaco(taco)
    }
    fun getLocalAllTacos(callback: (List<Taco>) -> Unit){
        tacoRepository.getLocalAllTacos(callback)
    }
    fun removeLocalTacos(){
        tacoRepository.removeLocalTacos()
    }

    fun addTacoToOrder() {
        taco.value?.let {
            val taco = it.copy(timestamp = System.currentTimeMillis())
            addLocalTaco(taco)
            _taco.value = emptyTaco
        }
    }
}