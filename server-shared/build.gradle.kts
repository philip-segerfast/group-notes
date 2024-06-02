plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm()

    sourceSets {
        jvmMain.dependencies {
            // Common server dependencies
        }

        commonMain.dependencies {
            implementation(project(":shared"))
        }
    }
}