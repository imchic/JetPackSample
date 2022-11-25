@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.tobechain.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun setOutlineCardMaterial3() {
    ElevatedCard(
        onClick = { /* Do something */ },
        modifier =
        Modifier.padding(16.dp)
            .fillMaxWidth()
            .height(150.dp),

    ) {
        Box(Modifier.fillMaxSize()) {
            Text("Clickable", Modifier.align(Alignment.Center))
        }
    }
}
