package com.wizeline.dependencyinjection.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


fun getOrderViewModelFactory(
    orderViewModel: OrderViewModel
): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when (modelClass.name) {
                OrderViewModel::class.java.name -> {
                    orderViewModel as T
                }
                else -> {
                    orderViewModel as T
                }
            }
        }
    }
}