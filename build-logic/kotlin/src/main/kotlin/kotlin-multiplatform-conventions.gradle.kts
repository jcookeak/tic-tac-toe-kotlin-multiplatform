import org.gradle.api.tasks.testing.logging.TestLogEvent

val libs = extensions
    .getByType<VersionCatalogsExtension>().find("libs").get()

plugins {
    id("kotlin-conventions")
    id("com.diffplug.spotless")
}

kotlin {
//    wasm {
//        binaries.executable()
//        binaries.library()
//        browser {}
//    }
    js(IR) {
        nodejs()
//        binaries.library()
        binaries.executable() // ts generation appears broken in this beta...
//        browser {}
//        useCommonJs()
    }

    sourceSets {
        all {
            languageSettings {
                optIn("kotlin.RequiresOptIn")
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

    }
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