object Dependency {

    // AndroidX
    const val COMPAT_VERSION = "1.3.1"

    // KTX
    const val KTX_CORE_VERSION = "1.7.0"

    // COMPOSE
    const val COMPOSE_VERSION = "1.4.0-alpha01"

    // LOTTIE
    const val LOTTIE_VERSION = "5.2.0"

    // HILT
    const val HILT_VERSION = "2.44"

    // TEST
    const val JUNIT_VERSION = "1.1.4"

    // Android Test
    const val ESPRESSO_CORE_VERSION = "3.5.0"

    object AndroidX {
        const val APPCOMPAT = "androidx.appcompat:appcompat:$COMPAT_VERSION"
        const val CORE_KTX = "androidx.core:core-ktx:$KTX_CORE_VERSION"
        const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:1.3.1"
        const val LIFE_CYCLE = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"
    }

    object Compose {
        const val ANIMATION = "androidx.compose.animation:animation:$COMPOSE_VERSION"
        const val FOUNDATION = "androidx.compose.foundation:foundation:$COMPOSE_VERSION"
        const val MATERIAL = "androidx.compose.material3:material3:1.1.0-alpha01"
        const val MATERIAL_WINDOW_SIZE_CLASS = "androidx.compose.material3:material3-window-size-class:1.1.0-alpha01"
        const val MATERIAL_ICONS_EXTENDED = "androidx.compose.material:material-icons-extended:$COMPOSE_VERSION"
        const val RUNTIME = "androidx.compose.runtime:runtime:1.4.0-alpha02"
        const val RUNTIME_LIVEDATA = "androidx.compose.runtime:runtime-livedata:1.4.0-alpha02"
        const val RUNTIME_RXJAVA2 = "androidx.compose.runtime:runtime-rxjava2:1.4.0-alpha02"

        const val UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview:$COMPOSE_VERSION"
        const val UI_TOOLING = "androidx.compose.ui:ui-tooling:$COMPOSE_VERSION"
    }

    object Google {
        const val HILT = "com.google.dagger:hilt-android:$HILT_VERSION"
        const val HILT_COMPILER = "com.google.dagger:hilt-android-compiler:$HILT_VERSION"
    }

    object GIS_ENGINE {
        const val CARTO_MAP_SDK = "com.carto:carto-mobile-sdk:4.4.7@aar"

        const val GEOTOOLS_MAIN = "org.geotools:gt-main:27.0"
        const val GEOTOOLS_SWING = "org.geotools:gt-swing:27.0"
        const val GEOTOOLS_GEOJSON = "org.geotools:gt-geojson:27.0"
        const val GEOTOOLS_SHAPEFILE = "org.geotools:gt-shapefile:27.0"
        const val GEOTOOLS_HSQL = "org.geotools:gt-epsg-hsql:27.0"
        const val GEOTOOLS_OPENGIS = "org.geotools:gt-opengis:11-beta"
    }

    object UI {
        const val LOTTIE_COMPOSE = "com.airbnb.android:lottie-compose:$LOTTIE_VERSION"
    }

    object Test {
        const val JUNIT = "junit:junit:4.13.2"
        const val EXT_JUNIT = "androidx.test.ext:junit:$JUNIT_VERSION"
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:$ESPRESSO_CORE_VERSION"
    }

}