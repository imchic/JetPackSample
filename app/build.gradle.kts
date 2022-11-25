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
            excludes += "META-INF/AL2.0"
            excludes += "META-INF/LGPL2.1"
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

    implementation(Dependency.UI.LOTTIE_COMPOSE)

    // Hilt
    implementation(Dependency.Google.HILT)
    kapt(Dependency.Google.HILT_COMPILER)

    // Map
    implementation(Dependency.Map.CARTO_MAP_SDK)

    implementation(Dependency.Test.JUNIT)
    implementation(Dependency.Test.EXT_JUNIT)
    implementation(Dependency.Test.ESPRESSO_CORE)
}

kapt {
    correctErrorTypes = true
}
