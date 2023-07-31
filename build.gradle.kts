import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.21"
    id("java")
    id("idea")
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
}

version "2.0.0"

allprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("java")
        plugin("idea")
        plugin("com.github.johnrengelman.shadow")
    }

    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            url = uri("https://libraries.minecraft.net")
        }
        maven {
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
        maven {
            url = uri("https://repo.purpurmc.org/snapshots")
        }
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
        }
    }

    dependencies {
        implementation("com.google.code.gson:gson:2.10.1")
        implementation("fastutil:fastutil:5.0.9")
        implementation("com.google.guava:guava:32.1.1-jre")
        implementation("com.electronwill.night-config:toml:3.6.6")
        implementation("org.apache.commons:commons-lang3:3.12.0")

        compileOnly("org.jetbrains:annotations:24.0.0")
        compileOnly("org.projectlombok:lombok:1.18.24")
        compileOnly("net.luckperms:api:5.4")
        testCompileOnly("org.projectlombok:lombok:1.18.24")

        annotationProcessor("org.projectlombok:lombok:1.18.24")
        testAnnotationProcessor("org.projectlombok:lombok:1.18.24")

        testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
    }

    tasks.test {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
    /*
    configureEach {
            options.encoding = "UTF-8"
        }

    tasks.withType<org.gradle.jvm.tasks.Jar> {
        configureEach {
            destinationDirectory = file("$rootDir/products")
        }
    }
     */
}