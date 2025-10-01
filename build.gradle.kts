plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
}

val minecraft = stonecutter.current.version
val mcVersion = stonecutter.current.project.substringBeforeLast('-')

version = "${mod.version}+$minecraft"
base {
    archivesName.set("${mod.id}-common")
}

architectury.common(stonecutter.tree.branches.mapNotNull {
    if (stonecutter.current.project !in it) null
    else it.project.prop("loom.platform")
})

repositories {
    maven ( "https://api.modrinth.com/maven") // Modrinth
    maven ("https://maven.parchmentmc.org")
    maven("https://maven.wispforest.io/releases")
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft")
    mappings(loom.layered {
        officialMojangMappings()
        if (stonecutter.eval(mcVersion, "<1.21.6")) {
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
    modImplementation("net.fabricmc:fabric-loader:${mod.dep("fabric_loader")}")
    "io.github.llamalad7:mixinextras-common:${mod.dep("mixin_extras")}".let {
        annotationProcessor(it)
        implementation(it)
    }
    modApi("me.shedaniel.cloth:cloth-config-fabric:${mod.dep("cloth_version")}")
    modCompileOnly("maven.modrinth:map-atlases:${mod.dep("map_atlases_fabric")}")
    if (stonecutter.eval(mcVersion, ">1.19.2")) {
        modCompileOnly("io.wispforest:accessories-common:${mod.dep("accessories")}")
    }
    if (stonecutter.eval(mcVersion, ">1.20") && stonecutter.eval(mcVersion, "<1.21.5")) {
        modCompileOnly("maven.modrinth:accessorify:${mod.dep("accessorify")}+$minecraft")
    }
    else if (stonecutter.eval(mcVersion, ">=1.21.5")) {
        modCompileOnly("maven.modrinth:accessorify:${mod.dep("accessorify")}+1.21.4")
    }
    if (stonecutter.eval(mcVersion, ">1.19.2")) {
        modCompileOnly("maven.modrinth:tough-as-nails:${mod.dep("tough_as_nails")}")
    }
    modCompileOnly("maven.modrinth:travelersbackpack:${mod.dep("travelers_backpack")}")
    if (stonecutter.eval(mcVersion, ">1.20")) {
        modCompileOnly("maven.modrinth:serene-seasons:${mod.dep("serene_seasons")}-fabric")
    }
    modCompileOnly("maven.modrinth:fabric-seasons:${mod.dep("fabric_seasons")}")
    modCompileOnly("maven.modrinth:simple-seasons:${mod.dep("simple_seasons")}")

    // Jade
    modImplementation("maven.modrinth:jade:${mod.dep("jade_version")}")

    //Xaero's
    modCompileOnly("maven.modrinth:xaeros-minimap:${mod.dep("xaeros")}_Fabric_${mod.dep("xaeros_mc")}")
    modCompileOnly("maven.modrinth:xaeros-world-map:${mod.dep("xaeros_world_map")}_Fabric_${mod.dep("xaeros_mc")}")

}

loom {
    decompilers {
        get("vineflower").apply { // Adds names to lambdas - useful for mixins
            options.put("mark-corresponding-synthetics", "1")
        }
    }
}

java {
    withSourcesJar()
    val java = if (stonecutter.eval(minecraft, ">=1.20.5"))
        JavaVersion.VERSION_21 else JavaVersion.VERSION_17
    targetCompatibility = java
    sourceCompatibility = java
}

tasks.build {
    group = "versioned"
    description = "Must run through 'chiseledBuild'"
}