dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    // Include 'plugins build' to define convention plugins.
    includeBuild("build-logic")
}

rootProject.name = "tic-tac-toe"
// This is an empty umbrella build including all the component builds.
// This build is not necessarily needed. The component builds work independently.

//includeBuild("build-logic")
include("game", "android")