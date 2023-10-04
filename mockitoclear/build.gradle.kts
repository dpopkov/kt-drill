plugins {
    kotlin("jvm") version "1.9.23"
}

group = "learn.mockito"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.bundles.mockito)
    testImplementation(libs.assertj)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}