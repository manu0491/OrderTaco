package com.wizeline.dependencyinjection.ui.checkout.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatefulCounter(tacoCount: Int, modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableStateOf(0) }
    count = tacoCount
    StatelessCounter(count, modifier)
}

@Composable
fun StatelessCounter(count: Int, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            val tacoText = if (count <= 1) "taco" else "tacos"
            Text("Has ordenado $count $tacoText")
        }
    }
}
