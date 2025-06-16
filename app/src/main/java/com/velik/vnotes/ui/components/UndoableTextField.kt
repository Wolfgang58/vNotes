package com.velik.vnotes.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun UndoableTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val history = remember { mutableStateListOf<String>() }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                if (value != it) {
                    history.add(value)
                    if (history.size > 50) history.removeAt(0)
                    onValueChange(it)
                }
            },
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth()
        )

        TextButton(
            onClick = {
                if (history.isNotEmpty()) {
                    val last = history.removeLast()
                    onValueChange(last)
                }
            },
            enabled = history.isNotEmpty()
        ) {
            Text("Geri Al")
        }
    }
}
