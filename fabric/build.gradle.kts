@file:Suppress("UnstableApiUsage")


plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
    id("com.github.johnrengelman.shadow")
}

val loader = prop("loom.platform")!!
val minecraft: String = stonecutter.current.version
val common: Project = requireNotNull(stonecutter.node.sibling("")?.project) {
    "No common project for $project"
}

version = "${mod.version}+$minecraft"
base {
    archivesName.set("${mod.id}-$loader")
}
architectury {
    platformSetupLoomIde()
    fabric()
}

val commonBundle: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

val shadowBundle: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

configurations {
    compileClasspath.get().extendsFrom(commonBundle)
    runtimeClasspath.get().extendsFrom(commonBundle)
    get("developmentFabric").extendsFrom(commonBundle)
}

val mcVersion = stonecutter.current.project.substringBeforeLast('-')

repositories {
    maven ( "https://maven.shedaniel.me/" )
    maven ( "https://maven.terraformersmc.com/releases/" )
    maven ( "https://api.modrinth.com/maven")
    maven ("https://maven.parchmentmc.org")
    maven ("https://maven.ladysnake.org/releases")
    maven ("https://maven.wispforest.io/releases")
    maven ( "https://repo.spongepowered.org/repository/maven-public/")
}

dependencies {
    // Minecraft
    minecraft("com.mojang:minecraft:$minecraft")
    mappings(loom.layered {
        officialMojangMappings()
        val parchmentVersion = when (mcVersion) {
            "1.18.2" -> "1.18.2:2022.11.06"
            "1.19.2" -> "1.19.2:2022.11.27"
            "1.20.1" -> "1.20.1:2023.09.03"
            "1.21.1" -> "1.21.1:2024.11.17"
            "1.21.4" -> "1.21.4:2025.03.23"
            "1.21.5" -> "1.21.5:2025.04.19"
            "1.21.8" -> "1.21.6:2025.06.29"
            else -> ""
        }
        parchment("org.parchmentmc.data:parchment-$parchmentVersion@zip")
    })

    // Fabric
    modImplementation("net.fabricmc:fabric-loader:${mod.dep("fabric_loader")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${common.mod.dep("fabric_api")}")

    // Cloth Config
    modCompileOnly("me.shedaniel.cloth:cloth-config-fabric:${common.mod.dep("cloth_version")}")

    // Mod Menu
    modCompileOnly("com.terraformersmc:modmenu:${common.mod.dep("modmenu_version")}")

    modCompileOnly("maven.modrinth:map-atlases:${common.mod.dep("map_atlases_fabric")}")
    if (stonecutter.eval(mcVersion, "=1.19.2")) {
        modImplementation("maven.modrinth:moonlight:${common.mod.dep("moonlight")}-fabric")
    }
    else if (stonecutter.eval(mcVersion, "<1.21.2")) {
        modImplementation("maven.modrinth:moonlight:fabric_${common.mod.dep("moonlight")}")
//        modRuntimeOnly("maven.modrinth:map-atlases:${common.mod.dep("map_atlases_fabric")}")
    }

    if (stonecutter.eval(mcVersion, "=1.21.6")) {
    } else {
        modRuntimeOnly("maven.modrinth:player-locator-plus:${common.mod.dep("player_locator_plus")}")
            runtimeOnly("com.akuleshov7:ktoml-core:0.5.2")
            modRuntimeOnly("net.fabricmc:fabric-language-kotlin:1.13.2+kotlin.2.1.20")
        // Cloth Config
        modRuntimeOnly("me.shedaniel.cloth:cloth-config-fabric:${common.mod.dep("cloth_version")}")

        // Mod Menu
        modRuntimeOnly("com.terraformersmc:modmenu:${common.mod.dep("modmenu_version")}")
    }

    modCompileOnly("maven.modrinth:bplb:v1.0.0")
    modCompileOnly("maven.modrinth:player-locator-plus:${common.mod.dep("player_locator_plus")}")
        runtimeOnly("com.akuleshov7:ktoml-core:0.5.2")
        modRuntimeOnly("net.fabricmc:fabric-language-kotlin:1.13.2+kotlin.2.1.20")
    modImplementation("maven.modrinth:xaeros-minimap:${common.mod.dep("xaeros")}_Fabric_${common.mod.dep("xaeros_mc")}")


    if (stonecutter.eval(mcVersion, "=1.20.1")) {
        modImplementation("dev.onyxstudios.cardinal-components-api:cardinal-components-base:${common.mod.dep("cca")}")
        modImplementation("dev.onyxstudios.cardinal-components-api:cardinal-components-item:${common.mod.dep("cca")}")
    }

    // Accessorify
    if (stonecutter.eval(mcVersion, ">1.21") && stonecutter.eval(mcVersion, "<1.21.5")) {
        modImplementation("maven.modrinth:accessorify:${common.mod.dep("accessorify")}+$minecraft")
    } else {
        modCompileOnly("maven.modrinth:accessorify:${common.mod.dep("accessorify")}+1.21.1")
    }

    // todo runtime dep on serene seasons
//    if (stonecutter.eval(mcVersion, ">1.20") && stonecutter.eval(mcVersion, "<1.21.6")) {
//        modRuntimeOnly("maven.modrinth:serene-seasons:${common.mod.dep("serene_seasons")}-fabric")
//        modRuntimeOnly("com.github.glitchfiend.GlitchCore-fabric:$minecraft-${common.mod.dep("glitchcore")}")
//
//    }

    // Accessories
    if (stonecutter.eval(mcVersion, ">1.19.2") && stonecutter.eval(mcVersion, "<1.21.5")) {
        modRuntimeOnly("maven.modrinth:fabric-seasons:${common.mod.dep("fabric_seasons")}")
        modRuntimeOnly("io.wispforest:accessories-fabric:${common.mod.dep("accessories")}+$minecraft")
    }

    modCompileOnly("maven.modrinth:travelersbackpack:${common.mod.dep("travelers_backpack")}-fabric")
    modCompileOnly("org.ladysnake.cardinal-components-api:cardinal-components-entity:6.1.2")

    // Stonecutter/Arch
    commonBundle(project(common.path, "namedElements")) { isTransitive = false }
    shadowBundle(project(common.path, "transformProductionFabric")) { isTransitive = false }
}

configurations.all {
    resolutionStrategy {
        force("net.fabricmc:fabric-loader:${mod.dep("fabric_loader")}")
        force("net.fabricmc.fabric-api:fabric-api:${common.mod.dep("fabric_api")}")
    }
}

loom {
    decompilers {
        get("vineflower").apply { // Adds names to lambdas - useful for mixins
            options.put("mark-corresponding-synthetics", "1")
        }
    }

    runConfigs.all {
        isIdeConfigGenerated = true
        runDir = "../../../run"
        vmArgs("-Dmixin.debug.export=true")
    }
}

java {
    withSourcesJar()
    val java = if (stonecutter.eval(minecraft, ">=1.20.5"))
        JavaVersion.VERSION_21 else JavaVersion.VERSION_17
    targetCompatibility = java
    sourceCompatibility = java
}

tasks.shadowJar {
    configurations = listOf(shadowBundle)
    archiveClassifier = "dev-shadow"
}

tasks.remapJar {
    injectAccessWidener = true
    input = tasks.shadowJar.get().archiveFile
    archiveClassifier = null
    dependsOn(tasks.shadowJar)
}

tasks.jar {
    archiveClassifier = "dev"
}

tasks.processResources {
    properties(listOf("fabric.mod.json"),
        "id" to mod.id,
        "name" to mod.name,
        "version" to mod.version + "+" + minecraft,
        "minecraft" to common.mod.prop("mc_dep_fabric")
    )
}

tasks.build {
    group = "versioned"
    description = "Must run through 'chiseledBuild'"
}

tasks.register<Copy>("buildAndCollect") {
    group = "versioned"
    description = "Must run through 'chiseledBuild'"
    from(tasks.remapJar.get().archiveFile, tasks.remapSourcesJar.get().archiveFile)
    into(rootProject.layout.buildDirectory.file("libs/${mod.version}/$loader"))
    dependsOn("build")
}