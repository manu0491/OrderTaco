package com.wizeline.dependencyinjection.ui.checkout

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wizeline.dependencyinjection.data.Taco
import com.wizeline.dependencyinjection.repository.TacoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val tacoRepository: TacoRepository
): ViewModel() {

    private val _tacoList = MutableLiveData<List<Taco>>()
    val tacoList: LiveData<List<Taco>> =_tacoList


    fun getLocalAllTacos(){
        tacoRepository.getLocalAllTacos { tacos ->
            for (taco in tacos) {
                Log.d("test", "taco ordered: ${taco}")
            }
            _tacoList.postValue(tacos)
        }
    }
    fun removeLocalTacos(){
        tacoRepository.removeLocalTacos()
    }

    fun removeTaco(taco: Taco) {
        tacoRepository.removeTaco(taco)
        val list = tacoList.value?.run { toMutableList() }
        list?.remove(taco)
        list?.run {
            _tacoList.postValue(this)
        }
    }
}