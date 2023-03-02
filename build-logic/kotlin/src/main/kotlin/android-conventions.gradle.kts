plugins {
    id("org.jetbrains.kotlin.android")
    id("com.android.application")
    id("com.diffplug.spotless")
    id("com.github.ben-manes.versions")
}

group = "dev.jcookeak"

kotlin {
    jvmToolchain(17)
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