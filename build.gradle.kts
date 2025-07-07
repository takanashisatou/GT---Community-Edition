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

    create("extra") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

dependencies {
    compileOnly("org.jetbrains:annotations:24.1.0")

    // kotlin
    runtimeOnly(forge.kotlinforforge)
    compileOnly(forge.kotlinforforge)
    api(forge.kotlinforforge)
    jarJar(forge.kotlinforforge)

    // Runtime Recipe Viewers - uncomment whichever one you want to use //
    modRuntimeOnly(forge.emi)
    // modRuntimeOnly(forge.bundles.jei.impl)
    // modRuntimeOnly(forge.bundles.rei.runtime)

    // GTM
    modImplementation(forge.gtm)
    modImplementation(forge.ldlib)

    // Shimmer
    modCompileOnly(forge.shimmer)

    // Registrate
    modImplementation(forge.registrate)

    // Create and its dependencies
    modImplementation(variantOf(forge.create) { classifier("all") })// { transitive = false }
    modImplementation(forge.flywheel)

    // Recipe Viewers
    modCompileOnly(forge.bundles.jei)
    modCompileOnly(forge.bundles.rei)
    modCompileOnly(forge.emi)

    // Jade
    modImplementation(forge.jade)

    // Mixin (& Extras)
    annotationProcessor(variantOf(libs.mixin) { classifier("processor") })
    annotationProcessor(forge.mixinextras.common)
    compileOnly(forge.mixinextras.common)
    compileOnly(forge.mixinextras)

    // Configuration
    modImplementation(forge.configuration)

    // ae2
    modImplementation(forge.ae2)

    // AdAstra
    modImplementation(forge.ad.astra)
    modImplementation(forge.resourcefullib)
    modImplementation(forge.resourcefulconfig)
    modImplementation(forge.botarium)

    //others
    modImplementation(forge.supplementaries)
    modImplementation(forge.cc.tweaked)
    modImplementation(forge.selene)
    modImplementation(forge.botania)
    modImplementation(forge.curios)
    modImplementation(forge.patchouli)
}

apply {
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