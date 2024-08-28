import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    alias(libs.plugins.kotlinJvm)
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    application
}

group = "org.example"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

application {
    mainClass.set("org.example.backend.BackendApplication")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

tasks.getByName<BootJar>("bootJar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.getByName<Tar>("distTar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.getByName<Zip>("distZip") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

dependencies {
    implementation(projects.shared)
    implementation(projects.server.shared)

    testImplementation(libs.kotlin.test.junit)

    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.0.4")

    testImplementation("io.projectreactor:reactor-test")

    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
    runtimeOnly("org.mariadb:r2dbc-mariadb:1.2.0")

    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")
}