package com.example.tobechain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.example.tobechain.component.setDrawerLayoutMaterial3
import com.example.tobechain.component.showToolbar
import com.example.tobechain.ui.theme.sampleAppTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            sampleAppTheme {
                InitApp()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun InitApp() {

        val context = this
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        val iconItems = listOf(
            Icons.Outlined.EditCalendar,
            Icons.Outlined.Paid,
            Icons.Outlined.Construction,
            Icons.Outlined.RealEstateAgent,
            Icons.Outlined.MapsHomeWork,
            Icons.Outlined.EditLocationAlt
        )

        val menuItems = listOf(
            "후보지",
            "보상",
            "공사",
            "판매",
            "주택정보",
            "맵관리"
        )

        val selectedItem = remember { mutableStateOf(iconItems[0]) }

        var selectedMenu = remember { mutableStateOf(menuItems[0]) }
        val naviItems = listOf("Songs", "Artists", "Playlists")
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        val snackbarHostState = remember { SnackbarHostState() }

        Column(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    showToolbar(context, drawerState, scope)
                },
//                bottomBar = {
//                    setNavigationMaterial3(naviItems, selectedNaviItem)
//                },
                content = {innerPaddingValue ->
                    setDrawerLayoutMaterial3(drawerState, scope, iconItems, menuItems, selectedItem, innerPaddingValue)
                }
            )
        }
    }

    @Composable
    @Preview
    fun DefalutPreview() {
        InitApp()
    }
}

