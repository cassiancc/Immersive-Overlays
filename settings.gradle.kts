pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev")
        maven("https://maven.minecraftforge.net")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.kikugie.dev/snapshots")
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.5"
}

stonecutter {
    centralScript = "build.gradle.kts"
    kotlinController = true
    create(rootProject) {
        // Root `src/` functions as the 'common' project
        versions("1.19.2", "1.20.1", "1.21.1", "1.21.4", "1.21.5", "1.21.6-pre3")
        branch("fabric") // Copies versions from root
        branch("neoforge") { versions("1.21.1", "1.21.4", "1.21.5") }
    }
}

rootProject.name = "Immersive Overlays"