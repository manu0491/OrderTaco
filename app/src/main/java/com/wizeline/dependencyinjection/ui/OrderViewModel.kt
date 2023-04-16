package com.wizeline.dependencyinjection.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wizeline.dependencyinjection.data.Taco
import com.wizeline.dependencyinjection.repository.TacoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val tacoRepository: TacoRepository
): ViewModel() {

    val emptyTaco = Taco(type = "", tortilla = "", timestamp = 0L)
    private val _taco = MutableLiveData<Taco>(emptyTaco)
    val taco: LiveData<Taco> = _taco

    private val _tacoState = MutableLiveData<TACO_STATE>(TACO_STATE.ORDERING)
    val tacoState: LiveData<TACO_STATE> = _tacoState
    fun setType(type: String) {
        val taco = taco.value
        _taco.value = taco?.copy(type = type)
        _tacoState.postValue(TACO_STATE.ORDERING)
    }

    fun setTortilla(tortilla: String) {
        val taco = taco.value
        _taco.value = taco?.copy(tortilla = tortilla)
        _tacoState.postValue(TACO_STATE.ORDERING)
    }

    fun setNote(note: String) {
        val taco = taco.value
        _taco.value = taco?.copy(note = note)
        _tacoState.postValue(TACO_STATE.ORDERING)
    }

    private fun addLocalTaco(taco: Taco){
        tacoRepository.addLocalTaco(taco)
    }

    fun addTacoToOrder() {
        taco.value?.let {
            val taco = it.copy(timestamp = System.currentTimeMillis())
            addLocalTaco(taco)
            _taco.value = emptyTaco
            _tacoState.postValue(TACO_STATE.DONE)
        }
    }
}

enum class TACO_STATE {
    DONE,
    ORDERING
}