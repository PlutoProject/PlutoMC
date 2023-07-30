plugins {
    id("xyz.jpenilla.run-paper" )version "2.0.1"
}

dependencies {
    implementation(project(":framework-bukkit"))
    compileOnly(project(":framework-shared"))

    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("com.mojang:brigadier:1.0.18")

    implementation(project(":module-ironelevator"))
    implementation(project(":module-cactusrotator"))
    implementation(project(":module-voidtotem"))
    implementation(project(":module-waxednotwaxed"))
    implementation(project(":module-economy"))

    implementation(project(":dutil"))
}

tasks {
    runServer {
        minecraftVersion("1.19.3")
    }
}