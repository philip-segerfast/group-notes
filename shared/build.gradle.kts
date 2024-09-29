import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    targets.configureEach {
        compilations.configureEach {
            compileTaskProvider.get().compilerOptions {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            api("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")

            // kotlinx-serialization
            api(libs.kotlinx.serialization.json)
            api(libs.ktor.serialization.protobuf)
            api(libs.kotlinx.collections.immutable)

            api(libs.kotlinx.coroutines.core)
        }

        androidMain.dependencies {
            // put Android dependencies here
            api(libs.automerge.java)
        }

        jvmMain.dependencies {
            api(libs.automerge.java)
        }
    }
}

android {
    namespace = "org.robphi.groupnotes.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    dependencies {
        // put your Android dependencies here
    }
}