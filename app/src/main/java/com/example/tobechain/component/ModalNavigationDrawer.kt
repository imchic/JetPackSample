package com.example.tobechain.component

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
import com.carto.core.MapPosVector
import com.carto.core.MapRange
import com.carto.core.Variant
import com.carto.datasources.LocalVectorDataSource
import com.carto.layers.VectorLayer
import com.carto.projections.Projection
import com.carto.styles.LineJoinType
import com.carto.styles.LineStyleBuilder
import com.carto.styles.MarkerStyleBuilder
import com.carto.styles.PointStyleBuilder
import com.carto.ui.MapView
import com.carto.vectorelements.Line
import com.carto.vectorelements.Marker
import com.carto.vectorelements.Point
import com.example.tobechain.util.ObjectColor
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
                AndroidView(
                    factory = { context ->
                        val mapView = MapView(context)
                        val mapOpt = mapView.options!!
                        projection = mapOpt.baseProjection
                        mapView.apply {
                            setZoom(23f, 0.0f)
                            setFocusPos(MapPos(55.880251, 272.365759), 0.5F)
                        }
                    },
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    update = { view ->
                        view.apply {
                            val mapOpt = options!!
                            mapOpt.apply {
                                watermarkScale = 0.0f
                                tiltRange = MapRange(90f, 90f)
                                isRotatable = false
                                setZoom(18f, 0.0f)
                                setFocusPos(MapPos(55.880251, 272.365759), 0.5F)
                            }


                            projection = mapOpt.baseProjection

                            addMarker(projection, view)
                            addPoint(projection, view)
                            addLine(projection, view)

                        }
                    }
                )
            }
        }
    )
}

fun addLine(projection: Projection?, view: MapView) {
    val lineVectorSource = LocalVectorDataSource(projection)
    val vectorLayer = VectorLayer(lineVectorSource)
    view.layers.add(vectorLayer)

    // 1. Create line style, and line poses
    val lineStyleBuilder = LineStyleBuilder()
    lineStyleBuilder.color = ObjectColor.RED
    // Define how lines are joined
    lineStyleBuilder.lineJoinType = LineJoinType.LINE_JOIN_TYPE_ROUND
    lineStyleBuilder.width = 8F

    // 2. Special MapPosVector must be used for coordinates
    val linePoses = MapPosVector()
    val initial = projection?.fromWgs84(MapPos(24.645565, 59.422074))

    // 3. Add positions
    linePoses.add(initial)
    linePoses.add(projection?.fromWgs84(MapPos(24.643076, 59.420502)))
    linePoses.add(projection?.fromWgs84(MapPos(24.645351, 59.419149)))
    linePoses.add(projection?.fromWgs84(MapPos(24.648956, 59.420393)))
    linePoses.add(projection?.fromWgs84(MapPos(24.650887, 59.422707)))

    // 4. Create line
    // 4. Add a line
    val line = Line(linePoses, lineStyleBuilder.buildStyle())
    line.setMetaDataElement("ClickText", Variant("Line nr 1"))

    lineVectorSource.add(line)
}

fun addPoint(projection: Projection?, view: MapView) {
    val pointVectorSource = LocalVectorDataSource(projection)
    val vectorLayer = VectorLayer(pointVectorSource)
    view.layers.add(vectorLayer)

    val pointStyleBuilder = PointStyleBuilder()
    pointStyleBuilder.color = ObjectColor.LIME
    pointStyleBuilder.size = 10f

    val point = Point(MapPos(55.880251, 272.365759), pointStyleBuilder.buildStyle())
    pointVectorSource.add(point)
}

private fun addMarker(projection: Projection, view: MapView) {
    val source = LocalVectorDataSource(projection)
    val layer = VectorLayer(source)
    view.layers?.add(layer)

    val markerStyleBuilder = MarkerStyleBuilder()
    markerStyleBuilder.size = 30F
    markerStyleBuilder.color = ObjectColor.BROWN
    val markerStyle1 = markerStyleBuilder.buildStyle()

    val pos1 = MapPos(55.880251, 272.365759)
    val marker1 = Marker(pos1, markerStyle1)

    source.add(marker1)
}