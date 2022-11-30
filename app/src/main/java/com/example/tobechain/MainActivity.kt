package com.example.tobechain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tobechain.ui.component.TobeChainNavigationRail
import com.example.tobechain.ui.theme.tobechainTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            tobechainTheme {
                InitApp()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun InitApp() {

        val context = this

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

        Scaffold { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            ) {
                //showToolbar(context, drawerState, scope)
                TobeChainNavigationRail(Modifier.padding(innerPadding), iconItems, menuItems, selectedItem)
                //setDrawerLayoutMaterial3(drawerState, scope, iconItems, menuItems, selectedItem, innerPadding)
            }

        }
    }

    @Composable
    @Preview
    fun DefalutPreview() {
        InitApp()
    }
}

