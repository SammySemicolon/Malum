rootProject.name = "Malum"
pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.parchmentmc.org")
    }
    val loom_version: String by settings
    plugins {
        id("fabric-loom") version loom_version
    }
}