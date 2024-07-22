plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm()

    sourceSets {
        jvmMain.dependencies {
            // Common shared dependencies
        }

        commonMain.dependencies {
            implementation(project(":shared"))
        }
    }
}