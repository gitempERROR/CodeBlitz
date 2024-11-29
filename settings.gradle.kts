pluginManagement {
    repositories {
        mavenCentral()
        google {
            content {
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("androidx.*")
            }
        }

        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()

        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "CodeBlitz"
include(":app")
 