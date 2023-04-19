package com.wizeline.dependencyinjection.ui.checkout

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wizeline.dependencyinjection.data.Taco
import com.wizeline.dependencyinjection.repository.TacoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutViewModel (
    private val tacoRepository: TacoRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _tacoList = MutableLiveData<List<Taco>>()
    val tacoList: LiveData<List<Taco>> =_tacoList


    init {
        getLocalAllTacos()
    }
    fun getLocalAllTacos(){
        viewModelScope.launch(dispatcher) {
            tacoRepository.getLocalAllTacos { tacos ->
                for (taco in tacos) {
                    Log.d("test", "taco ordered: ${taco}")
                }
                _tacoList.postValue(tacos)
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