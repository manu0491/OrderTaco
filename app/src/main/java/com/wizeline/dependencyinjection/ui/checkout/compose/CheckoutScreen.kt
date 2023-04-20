package com.wizeline.dependencyinjection.ui.checkout.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.wizeline.dependencyinjection.ui.checkout.CheckoutViewModel
import com.wizeline.dependencyinjection.util.DateFormatter

@Composable
fun CheckoutScreen(
    viewModel: CheckoutViewModel = hiltViewModel(),
    dateFormatter: DateFormatter,
    modifier: Modifier = Modifier
) {
    val tacoListState = viewModel.tacoList.observeAsState()
    Column(modifier = modifier) {
        StatefulCounter(
            tacoCount = tacoListState.value?.size ?: 0,
            modifier = modifier
        )
        OrderTacoList(
           list = tacoListState.value ?: emptyList(),
           removeTaco = { viewModel.removeTaco(it) },
           dateFormatter
        )
    }
}