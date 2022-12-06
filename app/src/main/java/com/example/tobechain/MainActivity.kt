package com.example.tobechain

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.lifecycle.LifecycleOwner
import com.example.tobechain.map.BaseMap
import com.example.tobechain.map.MapViewModel
import com.example.tobechain.ui.component.TobeChainNavigationRail
import com.example.tobechain.ui.theme.tobechainTheme
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            tobechainTheme(darkTheme = isSystemInDarkTheme()) {
                initTobeChainApp()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    /**
     * TobeChain App
     */
    private fun initTobeChainApp() {

        val navRailItemsIcon = listOf(
            Icons.Outlined.Apartment
//            Icons.Outlined.EditCalendar,
//            Icons.Outlined.Paid,
//            Icons.Outlined.Construction,
//            Icons.Outlined.RealEstateAgent,
//            Icons.Outlined.MapsHomeWork,
//            Icons.Outlined.EditLocationAlt
        )

        val navRailItemsText = listOf(
            "공동주택전개도"
//            "후보지",
//            "보상",
//            "공사",
//            "판매",
//            "주택정보",
//            "맵관리"
        )

        val selectedItem = remember { mutableStateOf(navRailItemsIcon[0]) }
        val mapViewModel: MapViewModel by viewModels()

        Scaffold { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            ) {
                TobeChainNavigationRail(mapViewModel, Modifier.padding(innerPadding), navRailItemsIcon, navRailItemsText, selectedItem)
            }

        }

    }


    @Preview
    @Composable
    fun DefalutPreview() {
        initTobeChainApp()
    }
}

