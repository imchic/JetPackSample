pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral {
            content {
                excludeModule("javax.media", "jai_core")
            }
        }
        maven {
            url = uri("https://jitpack.io")
            url = uri("http://download.osgeo.org/webdav/geotools/")
            url = uri ("https://repo.osgeo.org/repository/release/")
            isAllowInsecureProtocol = true
        }
        flatDir { dirs("libs") }
    }
}
rootProject.name = "TobeChain"
include(":app")