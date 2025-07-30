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
    neoForge()
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
    get("developmentNeoForge").extendsFrom(commonBundle)
}

val mcVersion = stonecutter.current.project.substringBeforeLast('-')

repositories {
    maven("https://maven.neoforged.net/releases/")
    maven ( "https://maven.shedaniel.me/" )
    maven ( "https://maven.terraformersmc.com/releases/" )
    maven ( "https://api.modrinth.com/maven")
    maven ( "https://cursemaven.com" )
    maven ( "https://maven.wispforest.io/releases" )
    maven ( "https://maven.su5ed.dev/releases" )
    maven ( "https://maven.theillusivec4.top/")

}

dependencies {
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
    "neoForge"("net.neoforged:neoforge:${common.mod.dep("neoforge_loader")}")
    "io.github.llamalad7:mixinextras-neoforge:${mod.dep("mixin_extras")}".let {
        implementation(it)
        include(it)
    }

    // Cloth Config
    modApi("me.shedaniel.cloth:cloth-config-neoforge:${common.mod.dep("cloth_version")}")

    // Map Atlases
    if (stonecutter.eval(mcVersion, "<1.21.2")) {
        modImplementation("curse.maven:map-atlases-forge-519759:${common.mod.dep("map_atlases_forge")}")
        modImplementation("maven.modrinth:moonlight:neoforge_${common.mod.dep("moonlight")}")
    }
    else {
        modCompileOnly("curse.maven:map-atlases-forge-519759:${common.mod.dep("map_atlases_forge")}")
        modCompileOnly("maven.modrinth:moonlight:neoforge_${common.mod.dep("moonlight")}")
    }

    // Xaero's Minimap
    modImplementation("maven.modrinth:xaeros-minimap:${common.mod.dep("xaeros")}_NeoForge_${common.mod.dep("xaeros_mc")}")

    if (stonecutter.eval(mcVersion, "<1.21.5")) {
        // Accessories
        modImplementation("io.wispforest:accessories-neoforge:${common.mod.dep("accessories")}+$minecraft")
        // Accessorify
        modImplementation("maven.modrinth:accessorify:${common.mod.dep("accessorify")}+$minecraft")
    } else {
        // Accessories
        modCompileOnly("io.wispforest:accessories-neoforge:${common.mod.dep("accessories")}+1.21.4")
        // Accessorify
        modCompileOnly("maven.modrinth:accessorify:${common.mod.dep("accessorify")}+1.21.4")

    }
    compileOnly("top.theillusivec4.curios:curios-neoforge:${common.mod.dep("curios")}:api")
    compileOnly("maven.modrinth:travelersbackpack:${common.mod.dep("travelers_backpack_neoforge")}-neoforge")

    commonBundle(project(common.path, "namedElements")) { isTransitive = false }
    shadowBundle(project(common.path, "transformProductionNeoForge")) { isTransitive = false }
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

tasks.jar {
    archiveClassifier = "dev"
}

tasks.remapJar {
    injectAccessWidener = true
    input = tasks.shadowJar.get().archiveFile
    archiveClassifier = null
    dependsOn(tasks.shadowJar)
}

tasks.shadowJar {
    configurations = listOf(shadowBundle)
    archiveClassifier = "dev-shadow"
    exclude("fabric.mod.json", "architectury.common.json")
}

tasks.processResources {
    properties(listOf("META-INF/neoforge.mods.toml", "pack.mcmeta"),
        "id" to mod.id,
        "name" to mod.name,
        "version" to mod.version + "+" + minecraft,
        "minecraft" to common.mod.prop("mc_dep_forgelike")
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
