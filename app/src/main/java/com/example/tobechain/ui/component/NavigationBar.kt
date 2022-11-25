package com.example.tobechain.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun setNavigationMaterial3(naviItems: List<String>, selectedNaviItem: Int) {
    NavigationBar(
        content = {
            naviItems.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.ThumbUp, contentDescription = item) },
                    label = { Text(item) },
                    selected = selectedNaviItem == index,
                    onClick = {
                        // selectedNaviItem.value = index
                        // Log.d("debug","setNavigationRailMaterial3: selectedRailItem.value = $selectedRailItem.value")
                    }
                )
            }
        })
}