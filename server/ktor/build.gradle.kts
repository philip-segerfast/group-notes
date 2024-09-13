plugins {
//    kotlin("multiplatform")
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinx.serialization)
    id("app.cash.sqldelight") version "2.0.2"
    application
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Tar>{
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.withType<Zip>{
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

group = "robphi.server.ktor"
version = "1.0.0"

sqldelight {
    databases {
        create("Database") {
            packageName.set("robphi.server.ktor.database")
            dialect("app.cash.sqldelight:postgresql-dialect:2.0.2")
            generateAsync.set(true)
        }
    }
}

application {
    mainClass.set("robphi.server.ktor.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {
    implementation(projects.shared)
    implementation(projects.server.shared)

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")

    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.koin)

    // https://mvnrepository.com/artifact/io.ktor/ktor-server-sse-jvm
    implementation(libs.ktor.server.sse.jvm)

    implementation("io.r2dbc:r2dbc-spi:1.0.0.RELEASE")
    implementation("org.postgresql:r2dbc-postgresql:1.0.5.RELEASE")

    implementation(libs.kotlinx.coroutines.reactive)

//    implementation(libs.sqldeight.jdbc.driver)
    implementation(libs.sqldelight.r2dbc.driver)
    implementation(libs.sqldelight.async.extensions)
    implementation(libs.sqldelight.coroutines.extensions)

//    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)

    // tmp
    implementation(libs.kermit)
}