import org.jetbrains.kotlin.gradle.plugin.KotlinExecution

plugins {
    kotlin("jvm") version "1.9.23"
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "learn.javafx"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.8.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
javafx {
    version = "19"
    modules("javafx.controls", "javafx.graphics", "javafx.fxml")
}
