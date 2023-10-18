plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":examples"))
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.6.3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
