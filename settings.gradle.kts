pluginManagement {
    repositories {
        maven { url = uri("https://maven.minecraftforge.net/") }
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
    versionCatalogs {
        create("forge") {
            from(files("gradle/forge.versions.toml"))
        }
    }
}

rootProject.name = extra["mod_id"] as String
