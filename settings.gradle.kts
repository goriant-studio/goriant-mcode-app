pluginManagement {
    plugins {
        id("com.android.application") version "8.4.0" apply false // Or your version
        id("org.jetbrains.kotlin.android") version "2.0.0" apply false // Or your version
        id("androidx.compose.compiler") version "1.5.10" apply false//Or your version
        id("org.jetbrains.kotlin.plugin.compose") version "1.9.22"
    }
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Mcode"
include(":app")
