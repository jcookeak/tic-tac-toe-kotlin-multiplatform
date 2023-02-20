plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("io.kotest.multiplatform")
    id("com.diffplug.spotless")
    id("com.github.ben-manes.versions")
}

repositories {
    mavenCentral()
    mavenLocal()
}

group = "dev.jcookeak"

kotlin {
    sourceSets.configureEach {
        languageSettings {
            optIn("kotlin.time.ExperimentalTime")
            optIn("kotlin.experimental.ExperimentalTypeInference")
            optIn("kotlin.contracts.ExperimentalContracts")
            optIn("kotlin.js.ExperimentalJsExport")
        }
    }
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        ktlint()
    }
    kotlinGradle {
        ktlint()
    }
}