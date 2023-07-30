dependencies {
    implementation(project(":dutil"))

    implementation(project(":common-whitelistmanager"))
    implementation("org.mongodb:mongodb-driver-sync:4.8.2")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    compileOnly(project(":framework-velocity"))
    compileOnly(project(":framework-shared"))
    compileOnly("org.mongodb:bson:4.8.2")
    compileOnly("com.velocitypowered:velocity-api:3.1.1")
}