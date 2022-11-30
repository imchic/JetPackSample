@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.tobechain.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.carto.core.MapPos
import com.carto.core.MapRange
import com.carto.projections.Projection
import com.carto.ui.MapView
import com.example.tobechain.R
import com.example.tobechain.map.BaseMap
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showMapView() {

    lateinit var projection: Projection

    val context = LocalContext.current

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val items = listOf("토지", "건물")
    val iconItems = listOf(R.drawable.ic_biz_lad, R.drawable.ic_biz_residnt)

    Scaffold(
        topBar = {
            showMapAppBar(context, snackbarHostState, scope)
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    modifier = Modifier
                        .padding(12.dp)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        if (drawerState.isClosed) {
                            drawerState.open()
                        } else {
                            drawerState.close()
                        }
                    }
                },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(
                    imageVector = Icons.Outlined.Layers,
                    contentDescription = stringResource(id = R.string.ic_map_layers),
                )
            }
        },
        content = { padding ->

            ModalNavigationDrawer(
                drawerState = drawerState,
                scrimColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.32f),
                drawerContent = {
                    ModalDrawerSheet(Modifier.width(150.dp)) {
                        Spacer(Modifier.height(190.dp))
                        items.forEachIndexed { index, item ->
                            NavigationDrawerItem(
                                colors = NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.12f),
                                    selectedIconColor = MaterialTheme.colorScheme.error,
                                ),
                                icon = {
                                    Icon(
                                        painterResource(id = iconItems[index]),
                                        contentDescription = null
                                    )
                                },
                                label = { Text(item) },
                                selected = item == items[0],
                                onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                            )

                        }

                    }
                },

                // drawer layout in view content
                content = {
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
                            modifier = Modifier.fillMaxSize().padding(0.dp),
                            update = { view ->
                                view.apply {

                                    val mapOpt = options
                                    projection = mapOpt.baseProjection

                                    mapOpt.apply {
                                        watermarkScale = 0.0f
                                        tiltRange = MapRange(90f, 90f)
                                        isRotatable = false
                                        setZoom(14f, 0.0f)
                                        setFocusPos(projection.fromWgs84(MapPos(24.643076, 59.420502)), 0.5F)
                                    }

                                    //BaseMap.addMarker(projection, view)
                                    //BaseMap.addPoint(projection, view)
                                    //BaseMap.addLine(projection, view)
                                    BaseMap.addPolygon(projection, view)
                                    //BaseMap.addText(projection, view)

                                }
                            }
                        )
                    }
                }

            )
        }
    )

}
