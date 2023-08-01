dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-mojangapi:1.20.1-R0.1-SNAPSHOT")
    compileOnly("com.mojang:brigadier:1.0.500")
    compileOnly("io.netty:netty-all:4.1.96.Final")

    implementation(project(":framework-shared"))

    implementation(project(":dutil"))
}