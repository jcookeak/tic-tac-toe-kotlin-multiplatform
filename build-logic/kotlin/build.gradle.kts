import org.gradle.accessors.dm.LibrariesForLibs

//val Project.libs: LibrariesForLibs get() = (this as ExtensionAware).extensions.getByName("libs") as LibrariesForLibs
//val libs: LibrariesForLibs = the<LibrariesForLibs>()

plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
    implementation("io.kotest:kotest-framework-multiplatform-plugin-gradle:${libs.versions.kotest.get()}")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:${libs.versions.spotless.get()}")
    implementation("com.github.ben-manes:gradle-versions-plugin:${libs.versions.versions.get()}")
    implementation("dev.petuska:npm-publish-gradle-plugin:3.2.1")
    implementation("com.android.tools.build:gradle:7.4.2")
}
