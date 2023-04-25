package com.wizeline.dependencyinjection.ui.checkout.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wizeline.dependencyinjection.data.Taco
import com.wizeline.dependencyinjection.util.DateFormatter

@Composable
fun TacoItem(
    taco: Taco,
    onClose: () -> Unit,
    dateFormatter: DateFormatter,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
            .weight(1f)
            .padding(start = 16.dp)) {
            Text(
                modifier = modifier.padding(top = 16.dp),
                text = taco.type
            )
            Text(
                modifier = modifier.padding(top = 4.dp),
                text = taco.tortilla
            )
            Text(
                modifier = modifier.padding(top = 4.dp),
                text = "Nota: ${taco.note.orEmpty()}"
            )
            Text(
                modifier = modifier.padding(top = 4.dp),
                text = "Fecha: ${dateFormatter.formatDate(taco.timestamp)}"
            )
        }

        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close${taco.id}")
        }
    }
}