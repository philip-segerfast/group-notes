// Top-level build file where you can add configuration options common to all sub-projects/modules.
import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(projects.shared)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(projects.shared)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

android {
    namespace = "org.robphi.groupnotes"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.robphi.groupnotes"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)

        debugImplementation(libs.compose.ui.tooling)

        implementation(libs.androidx.lifecycle.viewmodel.compose)
        testImplementation("org.testng:testng:6.9.6")
        coreLibraryDesugaring(libs.desugar.jdk.libs)

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.viewmodel.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)

        implementation(libs.kotlinx.coroutines.android)
        implementation(libs.kotlinx.coroutines.reactor)

        // kotlinx-serialization
        implementation(libs.kotlinx.serialization.json)
        implementation(libs.converter.kotlinx.serialization)

        implementation(libs.gson)
        implementation(libs.converter.gson)
        implementation(libs.retrofit2.reactor.adapter)
        implementation(libs.retrofit)

        implementation(libs.androidx.material.icons.core)

        // Navigator
        implementation(libs.voyager.navigator)
        // Screen Model
        implementation(libs.voyager.screenmodel)
        // Transitions
        implementation(libs.voyager.transitions)
        // Hilt integration
        implementation(libs.voyager.hilt)

        implementation(libs.fingerprint.android)

        implementation(libs.androidx.lifecycle.runtime.ktx)

        implementation(libs.automerge)

        implementation("com.google.dagger:hilt-android:2.51.1")
        configurations["kapt"].dependencies.add(DefaultExternalModuleDependency("com.google.dagger", "hilt-android-compiler", "2.51.1"))
//        kapt("com.google.dagger:hilt-android-compiler:2.51.1")
        implementation("com.github.skydoves:retrofit-adapters-result:1.0.9")
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.robphi.groupnotes"
            packageVersion = "1.0.0"
        }
    }
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}