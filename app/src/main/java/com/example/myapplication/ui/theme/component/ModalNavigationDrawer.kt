package com.example.myapplication.ui.theme.component

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun setDrawerLayoutMaterial3(
    drawerState: DrawerState,
    scope: CoroutineScope,
    items: List<ImageVector>,
    selectedItem: MutableState<ImageVector>,
    paddingValues: PaddingValues
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(Modifier.width(240.dp)) {
                Spacer(Modifier.height(100.dp))
                items.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item, contentDescription = null) },
                        label = { Text(item.name) },
                        selected = item == selectedItem.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem.value = item
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {
            Row(
                modifier = Modifier.fillMaxSize().padding(paddingValues)
            ) {
//                Box {
//                    setTab()
//                }
                Box {
                    setNavigationRailMaterial3()
                }
                Box {
                    setListItem()
                }
//                Box(
//                    modifier = Modifier.fillMaxSize().padding(16.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    LazyVerticalGrid(
//                        columns = GridCells.Fixed(2),
//                        contentPadding = PaddingValues(4.dp)
//                    ) {
//                        items(count = 10) {
//                            setOutlineCardMaterial3()
//                        }
//                    }
//                }
            }
        }
    )
}

@Composable
fun setDrawerLegacy(context: Context, closeDrawer: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        item {
            // 닫기 버튼 오른쪽 끝으로
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                // material 3 icon button
                IconButton(onClick = { closeDrawer() }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close drawer",
                        tint = colorScheme.onSurface
                    )
                }
            }
        }
        // drawer item
        item {
            //addDrawerItem(context)
        }
    }
}