package com.wizeline.dependencyinjection.ui.checkout.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wizeline.dependencyinjection.data.Taco
import com.wizeline.dependencyinjection.util.DateFormatter

@Composable
fun OrderTacoList(
    list: List<Taco>,
    removeTaco: (Taco) -> Unit,
    dateFormatter: DateFormatter,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier
    ){
        items(
            items = list,
            key = { taco -> taco.id}
        ){taco ->
            TacoItem(
                taco = taco,
                onClose = { removeTaco(taco) },
                dateFormatter = dateFormatter
            )
        }
    }
}