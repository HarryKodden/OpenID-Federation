import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.npmPublish)
    id("maven-publish")
    alias(libs.plugins.kover)
}

repositories {
    mavenCentral()
    mavenLocal()
    google()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

kotlin {
    jvm()

    js(IR) {
        browser {
            commonWebpackConfig {
                devServer = KotlinWebpackConfig.DevServer().apply {
                    port = 8083
                }
            }
        }
        nodejs {
            testTask {
                useMocha {
                    timeout = "5000"
                }
            }
        }
        binaries.library()
        generateTypeScriptDefinitions()
        compilations["main"].packageJson {
            name = "@sphereon/openid-federation-client"
            version = rootProject.extra["npmVersion"] as String
            description = "OpenID Federation Client Library"
            customField("description", "OpenID Federation Client Library")
            customField("license", "Apache-2.0")
            customField("author", "Sphereon International")
            customField(
                "repository", mapOf(
                    "type" to "git",
                    "url" to "https://github.com/Sphereon-Opensource/openid-federation"
                )
            )

            customField(
                "publishConfig", mapOf(
                    "access" to "public"
                )
            )

            types = "./index.d.ts"
        }
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.js.ExperimentalJsExport")
            languageSettings.optIn("kotlinx.serialization.ExperimentalSerializationApi")
            languageSettings.optIn("kotlin.ExperimentalUnsignedTypes")
        }

        val commonMain by getting {
            dependencies {
                implementation(libs.kache)
                implementation(libs.kache.file)
                api(projects.modules.openidFederationCache)
                api(projects.modules.openidFederationHttpResolver)
                api(projects.modules.openidFederationOpenapi)
                api(projects.modules.openidFederationLogger)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.client.auth)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.datetime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(libs.ktor.client.mock)
                implementation(libs.kotlinx.coroutines.test)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.ktor.client.java)
                implementation(libs.nimbus.jose.jwt)
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(libs.kotlin.test.junit)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(libs.ktor.client.js)
                implementation(libs.kotlinx.coroutines.core.js)
                implementation(npm("jose", "5.9.4"))
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
                implementation(libs.kotlinx.coroutines.test.js)
                implementation(libs.ktor.client.mock.js)
            }
        }
    }
}

npmPublish {
    registries {
        register("npmjs") {
            uri.set("https://registry.npmjs.org")
            authToken.set(System.getenv("NPM_TOKEN") ?: "")
        }
    }
    packages {
        named("js") {
            packageJson {
                "name" by "@sphereon/openid-federation-client"
                "version" by rootProject.extra["npmVersion"] as String
            }
            scope.set("@sphereon")
            packageName.set("openid-federation-client")
        }
    }
}
