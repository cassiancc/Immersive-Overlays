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
    else it.prop("loom.platform")
})

repositories {
    maven ( "https://api.modrinth.com/maven") // Modrinth
    maven ("https://maven.parchmentmc.org")
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
            else -> ""
        }
        parchment("org.parchmentmc.data:parchment-$parchmentVersion@zip")
    })
    modImplementation("net.fabricmc:fabric-loader:${mod.dep("fabric_loader")}")
    "io.github.llamalad7:mixinextras-common:${mod.dep("mixin_extras")}".let {
        annotationProcessor(it)
        implementation(it)
    }
    modApi("me.shedaniel.cloth:cloth-config-fabric:${mod.dep("cloth_version")}")
    modCompileOnly("maven.modrinth:map-atlases:${mod.dep("map_atlases_fabric")}")

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