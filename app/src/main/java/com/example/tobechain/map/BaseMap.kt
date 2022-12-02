package com.example.tobechain.map

import android.content.Context
import android.util.Log
import com.carto.core.MapPos
import com.carto.core.MapPosVector
import com.carto.core.MapRange
import com.carto.core.Variant
import com.carto.datasources.GeoJSONVectorTileDataSource
import com.carto.datasources.LocalVectorDataSource
import com.carto.geometry.FeatureCollection
import com.carto.geometry.GeoJSONGeometryReader
import com.carto.geometry.MultiPolygonGeometry
import com.carto.layers.EditableVectorLayer
import com.carto.layers.Layer
import com.carto.layers.VectorLayer
import com.carto.layers.VectorTileLayer
import com.carto.projections.EPSG3857
import com.carto.projections.EPSG4326
import com.carto.projections.Projection
import com.carto.styles.*
import com.carto.ui.MapView
import com.carto.utils.AssetUtils
import com.carto.utils.ZippedAssetPackage
import com.carto.vectorelements.*
import com.carto.vectortiles.MBVectorTileDecoder


object BaseMap {

    enum class MapLayerName(val value: String) {
        EXPLODED_VIEW("explodedView"),
        GROUP("group"),
        ADD_FLOOR("addFloor"),
        ADD_LINE("addLine"),
        ADD_HO("addHo"),
    }

    // map
    private lateinit var _mapView: MapView
    private lateinit var _projection: Projection

    // source
    lateinit var explodedViewSource: LocalVectorDataSource
    lateinit var containsDataSource: LocalVectorDataSource
    lateinit var addFloorDataSource: LocalVectorDataSource
    lateinit var addLineDataSource: LocalVectorDataSource
    lateinit var addHoDataSource: LocalVectorDataSource

    // layer
    val explodedViewLayer: EditableVectorLayer by lazy { EditableVectorLayer(explodedViewSource) }
    val groupLayer: EditableVectorLayer by lazy { EditableVectorLayer(containsDataSource) }
    val floorUpLayer: EditableVectorLayer by lazy { EditableVectorLayer(addFloorDataSource) }
    val addLineLayer: EditableVectorLayer by lazy { EditableVectorLayer(addLineDataSource) }
    val addHoLayer: EditableVectorLayer by lazy { EditableVectorLayer(addHoDataSource) }

    // element arr
    var createPolygonArr = mutableListOf<Polygon>()
    var containsPolygonArr = mutableListOf<Polygon>()
    var selectPolygonArr = mutableListOf<Polygon>()

    var clickPosArr = mutableListOf<MapPos>()

    // listener
    //var selectListener: VectorElementSelectEventListener? = null

    // feature
    var featureCollection: FeatureCollection? = null


    /**
     * 맵뷰 기본세팅
     * @param context Context
     * @param view MapView
     */
    fun initMap(context: Context, view: MapView, viewModel: MapViewModel) {

        _mapView = view
        //_projection = _mapView.options.baseProjection
        _projection = EPSG4326()

        val mapOpt = _mapView.options

        _mapView.run {
            setZoom(18.0f, 0.0f)
            setFocusPos(_projection.fromWgs84(MapPos(24.643076, 59.420502)), 0.5F)
        }

        mapOpt.apply {
            watermarkScale = 0.0f
            tiltRange = MapRange(90f, 90f)
            isRotatable = false
        }

        explodedViewSource = LocalVectorDataSource(_projection)
        containsDataSource = LocalVectorDataSource(_projection)
        addFloorDataSource = LocalVectorDataSource(_projection)
        addLineDataSource = LocalVectorDataSource(_projection)
        addHoDataSource = LocalVectorDataSource(_projection)


        val layerArr = mutableListOf(explodedViewLayer, groupLayer, floorUpLayer, addLineLayer, addHoLayer)
        setLayer(_mapView, layerArr)

//        runCatching {
//            explodedView(context, explodedViewSource, createPolygonArr)
//        }.fold(
//            onSuccess = {
//                Log.d("BaseMap", it.toString())
//                when (it) {
//                    true -> {
//                        Log.d("BaseMap", "explodedView success")
//                    }
//
//                    false -> {
//                        Log.d("BaseMap", "explodedView fail")
//                    }
//                }
//            },
//            onFailure = { it: Throwable ->
//                Log.e("BaseMap", it.toString())
//            }
//        )

        this._mapView.mapEventListener =
            MapCustomEventListener(viewModel, _mapView)


    }

    /**
     * 레이어 세팅
     */
    private fun setLayer(mapview: MapView, layers: MutableList<EditableVectorLayer>) {

        var source: LocalVectorDataSource?
        val nameArr = mutableListOf<String>()

        val enums = enumValues<MapLayerName>()

        enums.mapIndexed { index, name ->
            when (name) {
                MapLayerName.EXPLODED_VIEW -> {
                    source = explodedViewSource
                    layers[index] = EditableVectorLayer(source)
                }

                MapLayerName.GROUP -> {
                    source = containsDataSource
                    layers[index] = EditableVectorLayer(source)
                }

                MapLayerName.ADD_FLOOR -> {
                    source = addFloorDataSource
                    layers[index] = EditableVectorLayer(source)
                }

                MapLayerName.ADD_LINE -> {
                    source = addLineDataSource
                    layers[index] = EditableVectorLayer(source)
                }

                MapLayerName.ADD_HO -> {
                    source = addHoDataSource
                    layers[index] = EditableVectorLayer(source)
                }

                else -> throw Exception("잘못된 레이어 명입니다.")
            }

            setLayerName(layers[index], "name", Variant(name.value))
            mapview.layers.add(layers[index])

        }
            .also {
                for (i in 0 until getLayerCount()) {
                    nameArr.add(mapview.layers.get(i).metaData.get("name").string)
                }
                Log.i("info", "생성된 레이어 이름 : ${nameArr}, 현재 생성된 레이어의 갯수 : ${getLayerCount()}")
            }
    }


    /**
     * 레이어 명 세팅
     * @param layer VectorLayer
     * @param key String
     * @param name Variant
     */
    private fun setLayerName(layer: Layer?, key: String, name: Variant) = layer?.setMetaDataElement(key, name)

    /**
     * 레이어 명 가져오기
     * @param index Int
     * @param key String
     * @return String?
     */
    fun getLayerName(index: Int, key: String): String = _mapView.layers.get(index).getMetaDataElement(key).string

    /**
     * 레이어의 갯수
     * @return Int
     */
    fun getLayerCount(): Int = _mapView.layers.count()

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
        pointStyleBuilder.color = MapColor.LIME
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
        markerStyleBuilder.color = MapColor.BROWN
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
        lineStyleBuilder.color = MapColor.RED
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
        polyStyleBuilder.color = MapColor.YELLOW
        polyStyleBuilder.lineStyle = LineStyleBuilder().apply {
            color = MapColor.BLACK
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
        textStyleBuilder.color = MapColor.BLUE
        textStyleBuilder.fontSize = 20f
        textStyleBuilder.fontName = "Roboto-Regular.ttf"

        val text = Text(MapPos(24.643076, 59.420502), textStyleBuilder.buildStyle(), "Hello World")
        textVectorSource.add(text)
    }

    /**
     * dataSource 초기화
     * @param source LocalVectorDataSource?
     */
    private fun clear(source: LocalVectorDataSource?) {
        source?.clear()
    }

    /**
     * GeoJson 값 가져오기
     * @param fileName  Asset Package 내 불러올 파일명 (json type)
     * @return FeatureCollection?
     */
    private fun getGeoJsonFeature(context: Context, fileName: String): FeatureCollection? {
        val assetManager = context.resources.assets
        val stream = assetManager.open(fileName)
        val size = stream.available()
        val buffer = ByteArray(size)
        stream.read(buffer)
        stream.close()
        val json = String(buffer, charset("UTF-8"))
        Log.i("json=>", json)
        val reader = GeoJSONGeometryReader()

        //projection 변경
        //reader.targetProjection = EPSG3857()
        return reader.readFeatureCollection(json)
    }


    /**
     * 공동주택 전개도 레이어
     * @param context Context
     * @param source LocalVectorDataSource?
     * @param polygonArr MutableList<Polygon>
     */
    fun explodedView(context: Context, source: LocalVectorDataSource?, polygonArr: MutableList<Polygon>): Boolean {

        var isExploded: Boolean

        try {
            clear(source)
            polygonArr.clear()

            val elements = VectorElementVector()

            val features = getGeoJsonFeature(context, "dusan.geojson")
            val total = features?.featureCount!!

            for (i in 0 until total) {

                features.getFeature(i).apply {
                    val geometry = geometry as MultiPolygonGeometry
                    val properties = properties

                    val hoNm: String? = properties.getObjectElement("ho_nm").string
                    val huNum: String? = properties.getObjectElement("hu_num").string
                    val cPoedTxt: String? = properties.getObjectElement("c_poed_txt").string

                    for (j in 0 until geometry.geometryCount) {

                        val south = MapPos(geometry.getGeometry(j).bounds.min.x, geometry.getGeometry(j).bounds.min.y)
                        val west = MapPos(geometry.getGeometry(j).bounds.max.x, geometry.getGeometry(j).bounds.min.y)

                        val north = MapPos(geometry.getGeometry(j).bounds.max.x, geometry.getGeometry(j).bounds.max.y)
                        val east = MapPos(geometry.getGeometry(j).bounds.min.x, geometry.getGeometry(j).bounds.max.y)

                        val explodedVector = MapPosVector()
                        explodedVector.apply { add(south); add(west); add(north); add(east) }

                        val createPolygon: Polygon?
                        createPolygon = Polygon(
                            explodedVector,
                            MapStyle.setPolygonStyle(
                                MapColor.GREEN,
                                MapColor.GREEN,
                                2F
                            )
                        )

                        createPolygon.setMetaDataElement("ho", Variant(hoNm))
                        createPolygon.setMetaDataElement("hu", Variant(huNum))
                        createPolygon.setMetaDataElement("cPoedTxt", Variant(cPoedTxt))
                        createPolygon.setMetaDataElement("select", Variant("n"))

                        polygonArr.add(createPolygon)
                        elements.add(createPolygon)

//                        LogUtil.d("min => ${createPolygon.geometry.bounds.min}")
//                        LogUtil.d("max => ${createPolygon.geometry.bounds.max}")

//                        LogUtil.d("min => ${createPolygon.geometry.bounds.min}")
//                        LogUtil.d("max => ${createPolygon.geometry.bounds.max}")

                        val centerPos =
                            MapPos(createPolygon.geometry.centerPos.x, createPolygon.geometry.centerPos.y + 230000)
                        val middlePos =
                            MapPos(createPolygon.geometry.centerPos.x, createPolygon.geometry.centerPos.y - 50000)
                        val botPos = MapPos(createPolygon.geometry.centerPos.x, middlePos.y - 300000)

                        elements.add(
                            Text(
                                centerPos,
                                MapStyle.setTextStyle(MapColor.BLACK, 30F),
                                hoNm
                            )
                        )
                        elements.add(
                            Text(
                                middlePos,
                                MapStyle.setTextStyle(MapColor.RED, 32F),
                                huNum
                            )
                        )
                        elements.add(
                            Text(
                                botPos,
                                MapStyle.setTextStyle(MapColor.BLACK, 30F),
                                cPoedTxt
                            )
                        )
                    }

                }


            }

            source?.addAll(elements)
            isExploded = true

        } catch (e: Exception) {
            Log.e("error", e.message.toString())
            isExploded = false
        }

        return isExploded
    }

    fun createGeoJSONLayer(context: Context, mapView: MapView) {

        //val dataSource = GeoJSONVectorTileDataSource(0, mapView.zoom.toInt())

        try {
//            val `is`: InputStream = context.assets.open("dusan.geojson")
//            val sb = StringBuilder()
//            var ch: Int
//            while (`is`.read().also { ch = it } != -1) {
//                sb.append(ch.toChar())
//            }
//            val data: Variant = Variant.fromString(sb.toString())
//            val layerIdx = dataSource.createLayer("mbtiles")
//            dataSource.setLayerGeoJSON(layerIdx, data)

            val dataSource = GeoJSONVectorTileDataSource(0, 20)
            val geoJsonFeatuerCollection = getGeoJsonFeature(context, "dusan.geojson")
            dataSource.setLayerFeatureCollection(0, EPSG3857(), geoJsonFeatuerCollection)

            val styleAsset = ZippedAssetPackage(AssetUtils.loadAsset("cartostyles-v2.zip"))
            val styleSet = CompiledStyleSet(styleAsset, "darkmatter")
            val decoder = MBVectorTileDecoder(styleSet)

            val layer = VectorTileLayer(dataSource, decoder)
            mapView.layers.add(layer)

        } catch (e: Exception) {
            e.message?.let { Log.e("Error", it) }
        }

    }

//    fun getShape(context: Context, mapView: MapView){
//
//        // 앱 내 저장소에 있는 mbtiles 파일을 가져온다.
//        val shapePath = (context as MainActivity).getExternalFilesDir("/tobeChain")?.absolutePath
//
//        // folder가 없으면 생성
//        if (shapePath != null) {
//            val file = File(shapePath)
//            if (!file.exists()) {
//                file.mkdirs()
//            }
//        }
//
//        //val cartoCSSStyleSet = CartoCSSStyleSet("cartostyles-custom.zip")
//        //val decoder = MBVectorTileDecoder(cartoCSSStyleSet)
//
//
//        val styleAsset: BinaryData = AssetUtils.loadAsset("cartostyles-custom.zip")
//        val styleAssetPackage: AssetPackage = ZippedAssetPackage(styleAsset)
//        val styleSet = CompiledStyleSet(styleAssetPackage)
//        val tileDecoder = MBVectorTileDecoder(styleSet)
//        //val dataSource: TileDataSource = CartoOnlineTileDataSource("voyager")
//
//        val dataSource = MBTilesTileDataSource(10, 18,  shapePath)
//        val currentLayer = VectorTileLayer(dataSource, tileDecoder)
//
//        mapView.layers.add(currentLayer)
//
//
//
//        //val vectorTileDecoder = MBVectorTileDecoder(cartoCSSStyleSet)
//        //val scheme = MBTilesScheme.MBTILES_SCHEME_TMS
//
//        //val vectorTileLayer = MBTilesTileDataSource(12, 12, shapePath, scheme)
//        //mapView.layers.add(VectorTileLayer(vectorTileLayer, vectorTileDecoder))
//
//    }

}