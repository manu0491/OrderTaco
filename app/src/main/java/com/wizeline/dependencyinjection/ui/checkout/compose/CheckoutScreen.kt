package com.wizeline.dependencyinjection.ui.checkout.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wizeline.dependencyinjection.data.Taco
import com.wizeline.dependencyinjection.util.DateFormatter

@Composable
fun CheckoutScreen(
    tacoList: List<Taco>,
    onRemoveTaco: (Taco) -> Unit,
    dateFormatter: DateFormatter,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
       OrderTacoList(
           list = tacoList,
           removeTaco = onRemoveTaco,
           dateFormatter
        )
    }
}