@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.tobechain.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.carto.ui.MapView
import com.example.tobechain.R
import com.example.tobechain.map.BaseMap
import com.example.tobechain.map.MapViewModel


/**
 * 공동주택 전개도
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TobeChainExplodedView(viewModel: MapViewModel) {

    val context = LocalContext.current

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val items = listOf("토지", "건물")
    val iconItems = listOf(R.drawable.ic_biz_lad, R.drawable.ic_biz_residnt)

    Scaffold(
        topBar = {
            TobeMapAppBar(context, snackbarHostState, scope, viewModel)
        },
        bottomBar = {
            TobeChainBottomAppBar(snackbarHostState, scope)
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    modifier = Modifier.padding(12.dp)
                )
            }
        },
//        floatingActionButton = {
//            FloatingActionButton(
//                modifier = Modifier.padding(12.dp),
//                onClick = {
//                    scope.launch {
//                        snackbarHostState.showSnackbar("FloatingActionButton Clicked")
//                    }
//                },
//                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
//                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
//            ) {
//                Icon(
//                    imageVector = Icons.Outlined.Layers,
//                    contentDescription = stringResource(id = R.string.ic_map_layers),
//                )
//            }
//        }
    ) { padding ->

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            AndroidView(
                factory = { context ->
                    val mapView = MapView(context)
                    mapView
                },
                modifier = Modifier.fillMaxSize().padding(0.dp)
            ) { view ->
                view.apply {

                    BaseMap.run {
                        initMap(context, view, viewModel)
                        //explodedView(context, explodedViewSource, createPolygonArr)
                        //createGeoJSONLayer(context, view)
                    }

                    //BaseMap.addMarker(projection, view)
                    //BaseMap.addPoint(projection, view)
                    //BaseMap.addLine(projection, view)
                    //BaseMap.addPolygon(projection, view)
                    //BaseMap.addText(projection, view)

                }
            }
        }

//        ModalNavigationDrawer(
//            drawerState = drawerState,
//            scrimColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.32f),
//            drawerContent = {
//                ModalDrawerSheet(Modifier.padding(padding)) {
//                    //Spacer(Modifier.height(190.dp))
//                    items.forEachIndexed { index, item ->
//                        NavigationDrawerItem(
//                            colors = NavigationDrawerItemDefaults.colors(
//                                selectedContainerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.12f),
//                                selectedIconColor = MaterialTheme.colorScheme.error,
//                            ),
//                            icon = {
//                                Icon(
//                                    painterResource(id = iconItems[index]),
//                                    contentDescription = null
//                                )
//                            },
//                            label = { Text(item) },
//                            selected = item == items[0],
//                            onClick = {
//                                scope.launch {
//                                    drawerState.close()
//                                }
//                            },
//                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
//                        )
//
//                    }
//
//                }
//            }
//
//        )
    }

}
