package com.example.tobechain.ui.component

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.carto.core.MapPos
import com.carto.core.MapRange
import com.carto.projections.Projection
import com.carto.ui.MapView
import com.example.tobechain.map.BaseMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun setDrawerLayoutMaterial3(
    drawerState: DrawerState,
    scope: CoroutineScope,
    items: List<ImageVector>,
    menuItems: List<String>,
    selectedItem: MutableState<ImageVector>,
    paddingValues: PaddingValues
) {

    lateinit var projection: Projection

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(Modifier.width(240.dp)) {
                Spacer(Modifier.height(100.dp))
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        icon = { Icon(item, contentDescription = null) },
                        label = { Text(menuItems[index]) },
                        selected = item == selectedItem.value,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                Log.d("carto", menuItems[index])

                            }
                            selectedItem.value = item
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {
//            Box (
//                modifier =  Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues),
//                contentAlignment = Alignment.Center,
//                content = {
//                    showIntroLottieAnimation()
//                }
//            )

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                setListItem()
//                AndroidView(
//                    factory = { context ->
//                        val mapView = MapView(context)
//                        mapView
//                    },
//                    modifier = Modifier.fillMaxSize().padding(paddingValues),
//                    update = { view ->
//                        view.apply {
//
//                            val mapOpt = options
//                            projection = mapOpt.baseProjection
//
//                            mapOpt.apply {
//                                watermarkScale = 0.0f
//                                tiltRange = MapRange(90f, 90f)
//                                isRotatable = false
//                                setZoom(14f, 0.0f)
//                                setFocusPos(projection.fromWgs84(MapPos(24.643076, 59.420502)), 0.5F)
//                            }
//
//                            //BaseMap.addMarker(projection, view)
//                            //BaseMap.addPoint(projection, view)
//                            //BaseMap.addLine(projection, view)
//                            BaseMap.addPolygon(projection, view)
//                            //BaseMap.addText(projection, view)
//
//                        }
//                    }
//                )
            }
        }
    )
}