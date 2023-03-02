import org.gradle.api.tasks.testing.logging.TestLogEvent

val libs = extensions
    .getByType<VersionCatalogsExtension>().find("libs").get()

plugins {
    id("kotlin-conventions")
    id("com.diffplug.spotless")
    id("com.android.library")
}

kotlin {
    android()
    js(IR) {
        nodejs()
        binaries.library()
        useCommonJs()
//        binaries.executable() // ts generation appears broken in this beta...
//        browser {
//            webpackTask {
//                outputFileName = "kmp_game.js"
//                output.library = "kmpGame"
//            }
//        }
//        useCommonJs()

        compilations.all {

        }
    }

//    android()

    sourceSets {
        all {
            languageSettings {
                optIn("kotlin.RequiresOptIn")
                optIn("kotlin.js.ExperimentalJsExport")
            }
        }

        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
//                implementation(libs.findLibrary("kotest-engine").get())
                implementation(libs.findLibrary("kotest-assertions").get())
            }
        }
//        val commonTest by getting {
//            implementation(kotlin("test"))
//        }
//        val wasmMain by getting
//        val wasmTest by getting
        val jsMain by getting
        val jsTest by getting

        val androidMain by getting

    }
}

npmPublish {
    packages {
        named("js") {
            packageJsonTemplateFile.set(projectDir.resolve("../template.package.json"))
        }
    }
}

android {
    namespace = "dev.jcookeak"
    compileSdk = 33
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildToolsVersion = "33.0.2"
}

tasks.withType<Test>().configureEach {
    testLogging {
        events = setOf(
            TestLogEvent.FAILED,
            TestLogEvent.PASSED,
            TestLogEvent.SKIPPED,
        )
    }
}