package com.wizeline.dependencyinjection.ui.checkout

import android.util.Log
import androidx.lifecycle.ViewModel
import com.wizeline.dependencyinjection.repository.TacoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val tacoRepository: TacoRepository
): ViewModel() {


    fun getLocalAllTacos(){
        tacoRepository.getLocalAllTacos { tacos ->
            for (taco in tacos) {
                Log.d("test", "taco ordered: ${taco}")
            }
        }
    }
    fun removeLocalTacos(){
        tacoRepository.removeLocalTacos()
    }
}