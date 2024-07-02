plugins {
    alias(libs.plugins.springboot)
    alias(libs.plugins.springDependencyManagement)
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinPluginSpring)
    application
}

group = "com.sphereon.oid.fed"
version = "1.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    implementation(libs.springboot.actuator)
    implementation(libs.springboot.web)
    implementation(libs.kotlin.reflect)
    testImplementation(libs.springboot.test)
    runtimeOnly(libs.springboot.devtools)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        setExceptionFormat("full")
        events("started", "skipped", "passed", "failed")
        showStandardStreams = true
    }
}