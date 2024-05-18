plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("multiplatform")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.androidApp"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    sourceSets["main"].manifest.srcFile("src/main/AndroidManifest.xml")
}

kotlin {
    android()

    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
                implementation("androidx.core:core-ktx:1.9.0")
                implementation("androidx.appcompat:appcompat:1.5.1")
                implementation("com.google.android.material:material:1.7.0")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("junit:junit:4.13.2")
                implementation("androidx.test.ext:junit:1.1.4")
                implementation("androidx.test.espresso:espresso-core:3.5.0")
            }
        }
    }
}

dependencies {
    implementation("com.google.firebase:firebase-analytics-ktx:21.0.0")
}

apply(plugin = "com.google.gms.google-services")