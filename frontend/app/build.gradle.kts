plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("plugin.serialization") version "1.9.23"
}

android {
    namespace = "com.example.groupnotes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.groupnotes"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"))
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.12"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(libs.androidx.core.ktx)
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

}
