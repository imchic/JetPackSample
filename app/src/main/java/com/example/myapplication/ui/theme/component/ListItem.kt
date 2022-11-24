package com.example.myapplication.ui.theme.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun setListItem(){
    LazyColumn() {
        items(30) {
            ListItem(
                headlineText = { Text("Three line list item") },
                overlineText = { Text("OVERLINE") },
                supportingText = { Text("Secondary text") },
                leadingContent = {
                    Icon(
                        Icons.Filled.Favorite,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = "Localized description",
                    )
                },
                trailingContent = { Text("meta") }
            )
            Divider()
        }
    }
}