package com.example.tobechain.map

import com.carto.core.MapPos
import com.carto.core.MapPosVector
import com.carto.core.Variant
import com.carto.datasources.LocalVectorDataSource
import com.carto.layers.VectorLayer
import com.carto.projections.Projection
import com.carto.styles.*
import com.carto.ui.MapView
import com.carto.vectorelements.*
import com.example.tobechain.utils.ObjectColor

object BaseMap {

    /**
     * 포인트
     * @param projection Projection?
     * @param view MapView
     */
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

    /**
     * 마커
     * @param projection Projection
     * @param view MapView
     */
    fun addMarker(projection: Projection, view: MapView) {
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

    /**
     * 선
     * @param projection Projection?
     * @param view MapView
     */
    fun addLine(projection: Projection?, view: MapView) {
        val lineVectorSource = LocalVectorDataSource(projection)
        val vectorLayer = VectorLayer(lineVectorSource)
        view.layers.add(vectorLayer)

        val lineStyleBuilder = LineStyleBuilder()
        lineStyleBuilder.color = ObjectColor.RED
        lineStyleBuilder.lineJoinType = LineJoinType.LINE_JOIN_TYPE_ROUND
        lineStyleBuilder.width = 8F

        val linePoses = MapPosVector()
        val initial = projection?.fromWgs84(MapPos(24.645565, 59.422074))

        linePoses.add(initial)
        linePoses.add(projection?.fromWgs84(MapPos(24.643076, 59.420502)))
        linePoses.add(projection?.fromWgs84(MapPos(24.645351, 59.419149)))
        linePoses.add(projection?.fromWgs84(MapPos(24.648956, 59.420393)))
        linePoses.add(projection?.fromWgs84(MapPos(24.650887, 59.422707)))

        val line = Line(linePoses, lineStyleBuilder.buildStyle())
        line.setMetaDataElement("ClickText", Variant("Line nr 1"))

        lineVectorSource.add(line)
    }

    /**
     * 도형
     * @param projection Projection?
     * @param view MapView
     */
    fun addPolygon(projection: Projection?, view: MapView) {
        val polygonVectorSource = LocalVectorDataSource(projection)
        val vectorLayer = VectorLayer(polygonVectorSource)
        view.layers.add(vectorLayer)

        val polyStyleBuilder = PolygonStyleBuilder()
        polyStyleBuilder.color = ObjectColor.YELLOW
        polyStyleBuilder.lineStyle = LineStyleBuilder().apply {
            color = ObjectColor.BLACK
            width = 2F
        }.buildStyle()

        val polygonPoses = MapPosVector()
        val initial = projection?.fromWgs84(MapPos(24.645565, 59.422074))

        polygonPoses.add(initial)
        polygonPoses.add(projection?.fromWgs84(MapPos(24.643076, 59.420502)))
        polygonPoses.add(projection?.fromWgs84(MapPos(24.645351, 59.419149)))
        polygonPoses.add(projection?.fromWgs84(MapPos(24.648956, 59.420393)))
        polygonPoses.add(projection?.fromWgs84(MapPos(24.650887, 59.422707)))

        val polygon = Polygon(polygonPoses, polyStyleBuilder.buildStyle())
        polygon.setMetaDataElement("ClickText", Variant("Line nr 1"))

        polygonVectorSource.add(polygon)
    }

    /**
     * 라벨
     * @param projection Projection?
     * @param view MapView
     */
    fun addText(projection: Projection?, view: MapView) {
        val textVectorSource = LocalVectorDataSource(projection)
        val vectorLayer = VectorLayer(textVectorSource)
        view.layers.add(vectorLayer)

        val textStyleBuilder = TextStyleBuilder()
        textStyleBuilder.color = ObjectColor.BLUE
        textStyleBuilder.fontSize = 20f
        textStyleBuilder.fontName = "Roboto-Regular.ttf"

        val text = Text(MapPos(24.643076, 59.420502), textStyleBuilder.buildStyle(),"Hello World")
        textVectorSource.add(text)
    }

}