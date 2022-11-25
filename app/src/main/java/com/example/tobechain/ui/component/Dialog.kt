package com.example.tobechain.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun LogoutDialog(
    title: String,
    content: String,
    confirm: String,
    cancel: String,
    openDialog: MutableState<Boolean>
) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(title) },
            text = { Text(content) },
            confirmButton = {
                TextButton(onClick = { openDialog.value = false }) {
                    Text(confirm)
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog.value = false }) {
                    Text(cancel)
                }
            }
        )
    }
}