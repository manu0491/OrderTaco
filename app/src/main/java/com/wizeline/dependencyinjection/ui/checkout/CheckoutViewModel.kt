package com.wizeline.dependencyinjection.ui.checkout

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wizeline.dependencyinjection.data.Taco
import com.wizeline.dependencyinjection.repository.TacoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    @get:VisibleForTesting val tacoRepository: TacoRepository,
    @get:VisibleForTesting val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _tacoList = MutableLiveData<List<Taco>>()
    val tacoList: LiveData<List<Taco>> =_tacoList


    init {
        getLocalAllTacos()
    }
    fun getLocalAllTacos(){
        viewModelScope.launch(dispatcher) {
            tacoRepository.getLocalAllTacos().run {
                _tacoList.postValue(this)
            }
        }

    }
    fun removeLocalTacos(){
        viewModelScope.launch(dispatcher) {
            tacoRepository.removeLocalTacos()
        }
    }

    fun removeTaco(taco: Taco) {
        viewModelScope.launch(dispatcher) {
            tacoRepository.removeTaco(taco)
            val list = tacoList.value?.run { toMutableList() }
            list?.remove(taco)
            list?.run {
                _tacoList.postValue(this)
            }
        }
    }
}