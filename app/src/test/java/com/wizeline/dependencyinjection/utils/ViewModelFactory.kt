package com.wizeline.dependencyinjection.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wizeline.dependencyinjection.ui.checkout.CheckoutViewModel


fun getOrderViewModelFactory(
    orderViewModel: OrderViewModel,
): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return orderViewModel as T
        }
    }
}

fun getCheckoutViewModelFactory(
    checkoutViewModel: CheckoutViewModel,
): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return checkoutViewModel as T
        }
    }
}