import org.gradle.api.internal.tasks.userinput.UserInputReader
import org.jetbrains.kotlin.config.JvmAnalysisFlags.useIR
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AppConfig.COMPILE_SDK

    // local.properties 가져오기
    val localProperties = Properties().apply {
        load(rootProject.file("local.properties").inputStream())
    }

    defaultConfig {
        applicationId = AppConfig.APPLICATION_ID
        minSdk = AppConfig.MIN_SDK
        targetSdk = AppConfig.TARGET_SDK
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

//        ndk {
//            abiFilters.add("armeabi-v7a")
//            abiFilters.add("arm64-v8a")
//        }
    }

//    signingConfigs {
//        create("release") {
//            keyAlias = "key0"
//            keyPassword = ""
//            storePassword = ""
//            storeFile = file("../keystore/imchic.jks")
//        }
//    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            isTestCoverageEnabled = true
            applicationIdSuffix = ".debug"
//            buildConfigField("String", "api_url", localProperties.getProperty("api_url"))
            buildConfigField("String", "BASE_URL", "\"${AppConfig.DEBUG_URL}\"")
            buildConfigField("String", "APP_NAME", "\"${AppConfig.DEBUG_APP_NAME}\"")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("release") {
//            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isDebuggable = false
            applicationIdSuffix = ".release"
            buildConfigField("String", "BASE_URL", "\"${AppConfig.RELEASE_URL}\"")
            buildConfigField("String", "APP_NAME", "\"${AppConfig.RELEASE_APP_NAME}\"")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = AppConfig.JAVA_VERSION
        freeCompilerArgs += listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=true"
        )

    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    buildFeatures {
        dataBinding = true
        compose = true
    }
    packagingOptions {
        resources {
//            excludes += "META-INF/AL2.0"
//            excludes += "META-INF/LGPL2.1"
//            excludes += "META-INF/registryFile.jai"
//            excludes += "META-INF/registryFile.jaiext"
//            excludes += "AndroidManifest.xml"
//            excludes += "plugin.xml"
//            excludes += "about.ini"
//            excludes += "about.mappings"
//            excludes += "modeling32.png"
//            excludes += "about.properties"
//            excludes += "plugin.properties"
//            excludes += "it/geosolutions/jaiext/resources/image/it.geosolutions.jaiext.scale.properties"
//            excludes += "tec/uom/se/format/messages.properties"
        }
    }
    lint {
        abortOnError = false
    }
}

dependencies {

    // aar library add
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))

    // AndroidX
    implementation (Dependency.AndroidX.CORE_KTX)
    implementation (Dependency.AndroidX.LIFE_CYCLE)
    implementation (Dependency.AndroidX.ACTIVITY_COMPOSE)
    implementation (Dependency.AndroidX.NAVIGAION)
    implementation (Dependency.AndroidX.PREFERENCE)

    // Jetpack Compose
    implementation(Dependency.Compose.ANIMATION)
    implementation(Dependency.Compose.FOUNDATION)
    implementation(Dependency.Compose.MATERIAL)
    implementation(Dependency.Compose.MATERIAL_WINDOW_SIZE_CLASS)
    implementation(Dependency.Compose.MATERIAL_ICONS_EXTENDED)
    implementation(Dependency.Compose.RUNTIME)
    implementation(Dependency.Compose.RUNTIME_LIVEDATA)
    implementation(Dependency.Compose.RUNTIME_RXJAVA2)
    implementation(Dependency.Compose.UI_TOOLING_PREVIEW)
    implementation(Dependency.Compose.UI_TOOLING)

    // UI
    implementation(Dependency.UI.LOTTIE_COMPOSE)

    // Google
    implementation(Dependency.Google.HILT)
    kapt(Dependency.Google.HILT_COMPILER)

    // Map
    implementation(Dependency.GIS_ENGINE.CARTO_MAP_SDK)

    implementation(Dependency.Test.JUNIT)
    implementation(Dependency.Test.EXT_JUNIT)
    implementation(Dependency.Test.ESPRESSO_CORE)
}

kapt {
    correctErrorTypes = true
}
