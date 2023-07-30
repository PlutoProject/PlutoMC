dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.1.1")
    implementation(project(":framework-velocity"))
    implementation(project(":framework-shared"))
    implementation(project(":module-whitelist"))
    annotationProcessor("com.velocitypowered:velocity-api:3.1.1")
}