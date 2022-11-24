package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.component.*
import com.example.myapplication.ui.theme.sampleAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            sampleAppTheme {
                initApp()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun initApp() {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        val items = listOf(
            Icons.Default.Favorite,
            Icons.Default.Face,
            Icons.Default.Email,
            Icons.Default.Refresh,
            Icons.Default.Settings
        )
        val selectedItem = remember { mutableStateOf(items[0]) }

        var selectedNaviItem by remember { mutableStateOf(0) }
        val naviItems = listOf("Songs", "Artists", "Playlists")

        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        val snackbarHostState = remember { SnackbarHostState() }

        Column(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    setAppBar(drawerState, scope)
                },
//                bottomBar = {
//                    setNavigationMaterial3(naviItems, selectedNaviItem)
//                },
                floatingActionButtonPosition = FabPosition.End,
                floatingActionButton = {
                    var clickCount by remember { mutableStateOf(0) }
                    ExtendedFloatingActionButton(
                        onClick = { /* do something */ },
                        icon = { Icon(Icons.Filled.Add, "Localized description") },
                        text = { Text(text = "Extended FAB") },
                    )
                },
                content = {innerPaddingValue ->
                    setDrawerLayoutMaterial3(drawerState, scope, items, selectedItem, innerPaddingValue)
                }
            )
        }
    }

    @Composable
    @Preview
    fun preview() {
        initApp()
    }
}

