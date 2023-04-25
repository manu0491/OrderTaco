package com.wizeline.dependencyinjection.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wizeline.dependencyinjection.data.Taco
import com.wizeline.dependencyinjection.repository.TacoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val tacoRepository: TacoRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {


    val emptyTaco = Taco(type = "", tortilla = "", timestamp = 0L)
    private val _taco = MutableLiveData<Taco>(emptyTaco)
    val taco: LiveData<Taco> = _taco

    private val _tacoState = MutableStateFlow(TacoUiState.OrderingState(TACO_STATE.LOADING))
    val tacoState: StateFlow<TacoUiState> = _tacoState
    fun setType(type: String) {
        val taco = taco.value
        _taco.value = taco?.copy(type = type)
        _tacoState.value = TacoUiState.OrderingState(TACO_STATE.ORDERING)
        //_tacoState.postValue(TACO_STATE.ORDERING)
    }

    fun setTortilla(tortilla: String) {
        val taco = taco.value
        _taco.value = taco?.copy(tortilla = tortilla)
        //_tacoState.postValue(TACO_STATE.ORDERING)
        _tacoState.value = TacoUiState.OrderingState(TACO_STATE.ORDERING)
    }

    fun setNote(note: String) {
        val taco = taco.value
        _taco.value = taco?.copy(note = note)
        _tacoState.value = TacoUiState.OrderingState(TACO_STATE.ORDERING)
        //_tacoState.postValue(TACO_STATE.ORDERING)
    }

    private fun addLocalTaco(taco: Taco){
        viewModelScope.launch(dispatcher) {
            tacoRepository.addLocalTaco(taco)
        }
    }

    fun addTacoToOrder() {
        taco.value?.let {
            val taco = it.copy(timestamp = System.currentTimeMillis())
            addLocalTaco(taco)
            _taco.value = emptyTaco
            _tacoState.value = TacoUiState.OrderingState(TACO_STATE.DONE)
            //_tacoState.postValue(TACO_STATE.DONE)
        }
    }
}

enum class TACO_STATE {
    DONE,
    ORDERING,
    LOADING
}

sealed class TacoUiState{
    data class OrderingState(val ordering: TACO_STATE): TacoUiState()
}