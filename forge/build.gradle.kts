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
    forge()
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
    get("developmentForge").extendsFrom(commonBundle)
}

repositories {
    maven ( "https://maven.minecraftforge.net" )
    maven ( "https://maven.shedaniel.me/" )
    maven ( "https://maven.terraformersmc.com/releases/" )
    maven ( "https://api.modrinth.com/maven")
    maven ( "https://cursemaven.com" )
    maven ( "https://maven.parchmentmc.org" )
    maven ( "https://maven.wispforest.io/releases" )
    maven ( "https://maven.su5ed.dev/releases" )
    maven ( "https://maven.theillusivec4.top/")

}

val mcVersion = stonecutter.current.project.substringBeforeLast('-')

dependencies {
    minecraft("com.mojang:minecraft:$minecraft")
    mappings(loom.layered {
        officialMojangMappings()
        if (stonecutter.eval(mcVersion, "<=1.21.8")) {
            val parchmentVersion = when (mcVersion) {
                "1.18.2" -> "1.18.2:2022.11.06"
                "1.19.2" -> "1.19.2:2022.11.27"
                "1.20.1" -> "1.20.1:2023.09.03"
                "1.21.1" -> "1.21.1:2024.11.17"
                "1.21.4" -> "1.21.4:2025.03.23"
                "1.21.5" -> "1.21.5:2025.04.19"
                "1.21.8" -> "1.21.8:2025.07.20"
                else -> ""
            }
            parchment("org.parchmentmc.data:parchment-$parchmentVersion@zip")
    }})
    "forge"("net.minecraftforge:forge:$minecraft-${common.mod.dep("forge_loader")}")
    "io.github.llamalad7:mixinextras-forge:${mod.dep("mixin_extras")}".let {
        implementation(it)
        include(it)
    }



    // Cloth Config
    modApi("me.shedaniel.cloth:cloth-config-forge:${common.mod.dep("cloth_version")}")

    // Map Atlases
    modImplementation("curse.maven:map-atlases-forge-519759:${common.mod.dep("map_atlases_forge")}")
    if (stonecutter.eval(mcVersion, "=1.19.2")) {
        modImplementation("maven.modrinth:moonlight:${common.mod.dep("moonlight")}-forge")
    } else {
        modImplementation("maven.modrinth:moonlight:forge_${common.mod.dep("moonlight")}")
        modCompileOnly("io.wispforest:accessories-neoforge:${common.mod.dep("accessories")}+$minecraft")
    }

    // Xaero's Minimap
    modImplementation("maven.modrinth:xaeros-minimap:${common.mod.dep("xaeros")}_Forge_${common.mod.dep("xaeros_mc")}")
    modImplementation("maven.modrinth:xaeros-world-map:${common.mod.dep("xaeros_world_map")}_Forge_${common.mod.dep("xaeros_mc")}")

    if (stonecutter.eval(mcVersion, "=1.20.1")) {
        modImplementation("maven.modrinth:oreganized:${common.mod.dep("oreganized")}-forge")
        modImplementation("maven.modrinth:blueprint:${common.mod.dep("blueprint")}")
        modImplementation("curse.maven:legendary-survival-overhaul-840254:6834435")
    }
    modCompileOnly("top.theillusivec4.curios:curios-forge:${common.mod.dep("curios")}:api")
    modCompileOnly("maven.modrinth:travelersbackpack:${common.mod.dep("travelers_backpack")}-forge")

    compileOnly("maven.modrinth:sophisticated-core:${common.mod.dep("sophisticated_core")}")
    compileOnly("maven.modrinth:sophisticated-backpacks:${common.mod.dep("sophisticated_backpacks")}")

    modImplementation("maven.modrinth:cold-sweat:${common.mod.dep("cold_sweat")}")

    // Jade
    modImplementation("maven.modrinth:jade:${common.mod.dep("jade_version")}")


    commonBundle(project(common.path, "namedElements")) { isTransitive = false }
    shadowBundle(project(common.path, "transformProductionForge")) { isTransitive = false }
}

loom {
    decompilers {
        get("vineflower").apply { // Adds names to lambdas - useful for mixins
            options.put("mark-corresponding-synthetics", "1")
        }
    }

    forge.convertAccessWideners = true
    forge.mixinConfigs(
        "immersiveoverlays.mixins.json",
        "immersiveoverlays-forge.mixins.json"

    )


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
    properties(listOf("META-INF/mods.toml", "pack.mcmeta"),
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
