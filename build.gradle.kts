plugins {
    java
    eclipse
    idea
    alias(libs.plugins.modDevGradle)
    alias(libs.plugins.spotless)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin)
}

group = extra["maven_group"] as String

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

// Formats the mod version to include the loader, Minecraft version, and build number (if present)
val isSnapshot = System.getenv("SNAPSHOT") != null
version = "${extra["mod_version"]}" + (if (isSnapshot) "-SNAPSHOT" else "")

sourceSets {
    main {
        resources {
            srcDir("src/generated/resources")
        }
    }

    test {
        kotlin {
            srcDir("src/test/java")
        }
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }

    create("extra") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

apply {
    from("$rootDir/gradle/scripts/dependencies.gradle")
    from("$rootDir/gradle/scripts/moddevgradle.gradle")
    from("$rootDir/gradle/scripts/repositories.gradle")
    from("$rootDir/gradle/scripts/resources.gradle")
    from("$rootDir/gradle/scripts/jars.gradle")
    from("$rootDir/gradle/scripts/publishing.gradle")
    from("$rootDir/gradle/scripts/spotless.gradle")
}

// Create run-folders for non-client configs to avoid pollution of dev envs
tasks.named("generateModMetadata") {
    doFirst {
        mkdir("run/server")
        mkdir("run/gametest")
        mkdir("run/data")
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}