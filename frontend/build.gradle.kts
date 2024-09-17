// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import java.util.Properties

private val packageName = "org.robphi.groupnotes"

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.serialization)

    id("com.codingfeline.buildkonfig") version "0.15.2"
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

    fun KotlinDependencyHandler.kmpAuth() {
        implementation("io.github.mirzemehdi:kmpauth-google:2.0.0") //Google One Tap Sign-In
//            implementation("io.github.mirzemehdi:kmpauth-firebase:<version>") //Integrated Authentications with Firebase
        implementation("io.github.mirzemehdi:kmpauth-uihelper:2.0.0") //UiHelper SignIn buttons (AppleSignIn, GoogleSignInButton)
    }

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(projects.shared)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation("com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:0.15.2")
        }
        androidMain.dependencies {
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

            implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.7")

            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)

            implementation("org.automerge:automerge:0.0.7")

            kmpAuth()
        }
        iosMain.dependencies {
            kmpAuth()
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)

            implementation("org.automerge:automerge:0.0.7")
        }
    }
}

android {
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

        implementation(libs.fingerprint.android)

        implementation(libs.androidx.lifecycle.runtime.ktx)

        implementation(libs.automerge)

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