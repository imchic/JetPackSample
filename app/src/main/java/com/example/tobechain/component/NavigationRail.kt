package com.example.tobechain.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun setNavigationRailMaterial3() {

    var selectedRailItem = remember { mutableStateOf(0) }
    val items = listOf("Home", "Search", "Settings")
    val icons = listOf(Icons.Filled.Home, Icons.Filled.Search, Icons.Filled.Settings)

    NavigationRail (
        header = {
            FloatingActionButton(
                onClick = { /* do something */ },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Info, "Localized description")
            }
        },
        content = {
            items.forEachIndexed { index, item ->
                NavigationRailItem(
                    modifier = Modifier.padding(12.dp),
                    icon = { Icon(icons[index], contentDescription = item) },
                    label = { Text(item) },
                    selected = selectedRailItem.value == index,
                    onClick = {
                        selectedRailItem.value = index
                        Log.d("debug","setNavigationRailMaterial3: selectedRailItem.value = $selectedRailItem.value")
                    }
                )
            }
        },
    )
}