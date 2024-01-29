@file:Suppress("UnstableApiUsage")
pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven ("https://jitpack.io")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        google()
    }
}
rootProject.name = "marvel-superheroes"
include(":app")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
