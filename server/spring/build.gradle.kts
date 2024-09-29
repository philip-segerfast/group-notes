import org.springframework.boot.gradle.tasks.bundling.BootJar

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
    testImplementation(libs.kotlin.test.junit)

//    implementation(project(":server:gn-common"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.automerge:automerge:0.0.7")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.google.http-client:google-http-client-jackson2:1.45.0")
    implementation("org.springframework.security:spring-security-oauth2-client")
    implementation("com.google.api-client:google-api-client:2.7.0")
    implementation("com.google.auth:google-auth-library-oauth2-http:1.25.0")
    implementation("org.springframework.security:spring-security-config")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc:3.3.0")
    implementation("io.netty:netty-all:4.1.110.Final")
    implementation("org.mariadb:r2dbc-mariadb:1.2.0")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.0.4")
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    compileOnly("javax.servlet:javax.servlet-api:4.0.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}