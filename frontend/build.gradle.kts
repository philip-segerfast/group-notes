// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import java.util.Properties

private val packageName = "org.robphi.groupnotes"

plugins {
    // KMP
    alias(libs.plugins.kotlinMultiplatform)
    id("com.codingfeline.buildkonfig") version "0.15.2"
    // KMP - Compose
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    // KMP - Kotlin Serialization
    alias(libs.plugins.kotlinx.serialization)
    // Android
    alias(libs.plugins.androidApplication)
}

repositories {
    google()
    mavenCentral()
    maven("https://jitpack.io")
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
            baseName = "Group Notes"
            isStatic = true
        }
    }

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(projects.shared)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.viewmodel)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // Navigator
            implementation(libs.voyager.navigator)
            // Screen Model
            implementation(libs.voyager.screenmodel)
            // Transitions
            implementation(libs.voyager.transitions)

            implementation("androidx.datastore:datastore:1.1.1")
            implementation("androidx.datastore:datastore-preferences:1.1.1")

            implementation(libs.rsocket.ktor.client)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(libs.jetbrains.lifecycle.compose)
        }
        androidMain.dependencies {
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.converter.kotlinx.serialization)

            val androidxCredentialsVersion = "1.3.0-rc01"
            val googleIdVersion = "1.1.1"
            implementation("androidx.credentials:credentials:$androidxCredentialsVersion")
            implementation("androidx.credentials:credentials-play-services-auth:$androidxCredentialsVersion")
            implementation("com.google.android.libraries.identity.googleid:googleid:$googleIdVersion")
        }
        iosMain.dependencies {

        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

android {
    signingConfigs {
        getByName("debug") {
            storeFile = file("C:\\dev\\Android\\Keystore\\debugKeystore.jks")
            storePassword = "password"
            keyAlias = "debugkeystore"
            keyPassword = "password"
        }
        create("release") {
            storeFile = file("C:\\dev\\Android\\Keystore\\release.jks")
            storePassword = "password"
            keyAlias = "release"
            keyPassword = "password"
        }
    }
    namespace = packageName
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = packageName
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
            signingConfig = signingConfigs.getByName("release")
        }
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)

        testImplementation("org.testng:testng:6.9.6")
        coreLibraryDesugaring(libs.desugar.jdk.libs)

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.activity.compose)

        implementation(libs.kotlinx.coroutines.android)
        implementation(libs.kotlinx.coroutines.reactor)

        implementation(libs.gson)
        implementation(libs.converter.gson)
        implementation(libs.retrofit2.reactor.adapter)
        implementation(libs.retrofit)

        implementation(libs.fingerprint.android)

        implementation("com.github.skydoves:retrofit-adapters-result:1.0.9")
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = this@Build_gradle.packageName
            packageVersion = "1.0.0"
        }
    }
}

buildkonfig {
    packageName = "org.robphi.groupnotes.shared"

    val props = try {
        readLocalProperties()
    } catch (e: Exception) {
        error("Failed to load local.properties: $e")
    }

    defaultConfigs {
        buildConfigField(
            FieldSpec.Type.STRING,
            "WEB_CLIENT_ID",
            props["web_client_id"]?.toString() ?: error("Couldn't read web_client_id from local.properties. Did you forget to add it?")
        )
    }
}

private fun readLocalProperties(): Properties {
    val props = Properties()

    try {
        props.load(file("local.properties").inputStream())
    } catch (e: Exception) {
        // keys are private and can not be committed to git
        throw e
    }

    return props
}