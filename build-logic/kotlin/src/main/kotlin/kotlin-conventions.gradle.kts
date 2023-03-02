plugins {
    id("dev.petuska.npm.publish")
    id("org.jetbrains.kotlin.multiplatform")
    id("io.kotest.multiplatform")
    id("com.diffplug.spotless")
    id("com.github.ben-manes.versions")
}

group = "dev.jcookeak"

kotlin {
    jvmToolchain(17)

    sourceSets.configureEach {
        languageSettings {
            optIn("kotlin.time.ExperimentalTime")
            optIn("kotlin.experimental.ExperimentalTypeInference")
            optIn("kotlin.contracts.ExperimentalContracts")
            optIn("kotlin.js.ExperimentalJsExport")
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
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