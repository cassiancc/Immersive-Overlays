@file:Suppress("UnstableApiUsage")

plugins {
    id("net.fabricmc.fabric-loom-remap")
    id("dev.kikugie.postprocess.jsonlang")
    id("me.modmuss50.mod-publish-plugin")
    id("maven-publish")
}

val minecraft = stonecutter.current.version
val mcVersion = stonecutter.current.project.substringBeforeLast('-')

tasks.named<ProcessResources>("processResources") {
    fun prop(name: String) = project.property(name) as String

    val props = HashMap<String, String>().apply {
        this["version"] = prop("mod.version") + "+" + prop("deps.minecraft")
        this["minecraft"] = prop("mod.mc_dep_fabric")
    }

    filesMatching(listOf("fabric.mod.json", "META-INF/neoforge.mods.toml", "META-INF/mods.toml")) {
        expand(props)
    }

}

tasks.named("processResources") {
    dependsOn(":${stonecutter.current.project}:stonecutterGenerate")
}

version = "${property("mod.version")}+${property("deps.minecraft")}-fabric"
base.archivesName = property("mod.id") as String

//loom {
//    accessWidenerPath = rootProject.file("src/main/resources/${property("mod.id")}.accesswidener")
//}

jsonlang {
    languageDirectories = listOf("assets/${property("mod.id")}/lang")
    prettyPrint = true
}

repositories {
    mavenLocal()
    maven ( "https://maven.minecraftforge.net" ) {
        name = "Minecraft Forge"
    }
    maven {
        name = "shedaniel (Cloth Config)"
        url = uri("https://maven.shedaniel.me/")
        content {
            includeGroupAndSubgroups("me.shedaniel")
        }
    }
    maven {
        name = "Terraformers (Mod Menu)"
        url = uri("https://maven.terraformersmc.com/releases/")
        content {
            includeGroupAndSubgroups("com.terraformersmc")
            includeGroup("dev.emi")
        }
    }
    maven {
        name = "Wisp Forest Maven"
        url = uri("https://maven.wispforest.io/releases/")
        content {
            includeGroupAndSubgroups("io.wispforest")
        }
    }
    maven {
        name = "Modrinth"
        url = uri("https://api.modrinth.com/maven")
        content {
            includeGroupAndSubgroups("maven.modrinth")
        }
    }
    maven {
        name = "WTHIT"
        url = uri("https://maven2.bai.lol")
        content {
            includeGroupAndSubgroups("mcp.mobius.waila")
            includeGroupAndSubgroups("lol.bai")
        }
    }
    maven {
        name = "Sisby Maven"
        url = uri("https://repo.sleeping.town/")
        content {
            includeGroupAndSubgroups("folk.sisby")
        }
    }
    maven {
        name = "Parchment Mappings"
        url = uri("https://maven.parchmentmc.org")
        content {
            includeGroupAndSubgroups("org.parchmentmc")
        }
    }
    maven {
        name = "Xander Maven"
        url = uri("https://maven.isxander.dev/releases")
        content {
            includeGroupAndSubgroups("dev.isxander")
            includeGroupAndSubgroups("org.quiltmc.parsers")
        }
    }
    maven {
        name = "Nucleoid Maven (Polymer)"
        url = uri("https://maven.nucleoid.xyz")
        content {
            includeGroupAndSubgroups("eu.pb4")
            includeGroupAndSubgroups("xyz.nucleoid")
        }
    }
    maven {
        name = "Fuzs Mod Resources"
        url = uri("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/")
        content {
            includeGroupAndSubgroups("fuzs")
        }
    }
    maven {
        name = "FzzyMaven"
        url = uri("https://maven.fzzyhmstrs.me/")
        content {
            includeGroup("me.fzzyhmstrs")
        }
    }
    maven {
        name = "Cardinal Components"
        url = uri("https://maven.ladysnake.org/releases")
        content {
            includeGroupAndSubgroups("dev.onyxstudios")
            includeGroupAndSubgroups("org.ladysnake")
        }
    }
    maven {
        name = "Fabricators of Create (Snapshots)"
        url = uri("https://mvn.devos.one/snapshots")
        content {
            includeGroupAndSubgroups("net.createmod")
            includeGroupAndSubgroups("dev.engine-room")
            includeGroupAndSubgroups("io.github.fabricators_of_create")
            includeGroupAndSubgroups("com.simibubi")
        }
    }
    maven {
        name = "Fabricators of Create (Releases)"
        url = uri("https://mvn.devos.one/releases")
        content {
            includeGroupAndSubgroups("net.createmod")
            includeGroupAndSubgroups("dev.engine-room")
            includeGroupAndSubgroups("io.github.fabricators_of_create")
            includeGroupAndSubgroups("com.simibubi")
        }
    }

}

dependencies {
    minecraft("com.mojang:minecraft:${property("deps.minecraft")}")
    mappings(loom.layered {
        officialMojangMappings()
        if (hasProperty("deps.parchment"))
            parchment("org.parchmentmc.data:parchment-${property("deps.parchment")}@zip")
    })
    modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")

    // Cloth Config
    modApi("me.shedaniel.cloth:cloth-config-fabric:${property("deps.cloth_version")}")
    // Mod Menu
    modApi("com.terraformersmc:modmenu:${property("deps.modmenu_version")}")
    // Map Atlases
    modCompileOnly("maven.modrinth:map-atlases:${mod.dep("map_atlases")}")
    // Accesories
    if (hasProperty("deps.accessories")) {
        modCompileOnly("io.wispforest:accessories-fabric:${mod.dep("accessories")}")
    }
    // Tough as Nails
    modCompileOnly("maven.modrinth:tough-as-nails:${mod.dep("tough_as_nails")}")
    modCompileOnly("maven.modrinth:travelersbackpack:${mod.dep("travelers_backpack")}")
    modCompileOnly("maven.modrinth:serene-seasons:${mod.dep("serene_seasons")}-fabric")
    // Fabric Seasons
    if (hasProperty("deps.fabric_seasons")) {
        modCompileOnly("maven.modrinth:fabric-seasons:${mod.dep("fabric_seasons")}")
        modLocalRuntime("maven.modrinth:fabric-seasons:${mod.dep("fabric_seasons")}")
    } else {
        modCompileOnly("maven.modrinth:fabric-seasons:2.4.2-BETA+1.21")
    }
    // Simple Seasons
    modCompileOnly("maven.modrinth:simple-seasons:${mod.dep("simple_seasons")}")

    // Sophisticated Backpacks
    modCompileOnly("maven.modrinth:9jxwkYQL:${mod.dep("sophisticated_core")}")
    modCompileOnly("maven.modrinth:ouNrBQtq:${mod.dep("sophisticated_backpacks")}")
    modCompileOnly("io.github.fabricators_of_create.Porting-Lib:transfer:2.3.9+1.20.1")

    // Jade
    modCompileOnly("maven.modrinth:jade:${property("deps.jade")}")
    modLocalRuntime("maven.modrinth:jade:${property("deps.jade")}")

    // Xaero's
    modCompileOnly("maven.modrinth:xaeros-minimap:${mod.dep("xaeros")}_Fabric_${mod.dep("xaeros_mc")}")
    modCompileOnly("maven.modrinth:xaeros-world-map:${mod.dep("xaeros_world_map")}_Fabric_${mod.dep("xaeros_mc")}")

    // Thermoo
    modCompileOnly("maven.modrinth:thermoo:${property("deps.thermoo")}")
    modLocalRuntime("maven.modrinth:thermoo:${property("deps.thermoo")}")

    // Cardinal Components
    if (hasProperty("deps.cardinal_components")) {
        modImplementation("org.ladysnake.cardinal-components-api:cardinal-components-entity:${mod.dep("cardinal_components")}")
        modImplementation("org.ladysnake.cardinal-components-api:cardinal-components-base:${mod.dep("cardinal_components")}")
    }
    else if (stonecutter.eval(mcVersion, ">1.21")) {
        modCompileOnly("org.ladysnake.cardinal-components-api:cardinal-components-entity:6.1.2")
        modCompileOnly("org.ladysnake.cardinal-components-api:cardinal-components-base:6.1.2")
    } else {
        modCompileOnly("dev.onyxstudios.cardinal-components-api:cardinal-components-entity:5.2.3")
        modCompileOnly("dev.onyxstudios.cardinal-components-api:cardinal-components-base:5.2.3")
    }

    // Trinkets
    if (stonecutter.eval(mcVersion, "<1.21.4")) {
        modCompileOnly("dev.emi:trinkets:${mod.dep("trinkets")}")
    } else {
        modCompileOnly("eu.pb4.fork:trinkets:${mod.dep("trinkets")}")
    }

    // Antique Atlases
    if (hasProperty("deps.antique_atlas")) {
        modCompileOnly("maven.modrinth:antique-atlas-4:${mod.dep("antique_atlas")}")
    } else {
        modCompileOnly("maven.modrinth:antique-atlas-4:utFwd9ms")
    }

    // Tiered Backpacks
    if (hasProperty("deps.tiered_backpacks")) {
        modCompileOnly("maven.modrinth:tiered-backpacks:${mod.dep("tiered_backpacks")}")
        modLocalRuntime("maven.modrinth:tiered-backpacks:${mod.dep("tiered_backpacks")}")
    }
    if (hasProperty("deps.fzzy_config")) {
        modCompileOnly("me.fzzyhmstrs:fzzy_config:${mod.dep("fzzy_config")}")
        modLocalRuntime("me.fzzyhmstrs:fzzy_config:${mod.dep("fzzy_config")}")
    }

    // Player Locator Bar Backports
    modCompileOnly("maven.modrinth:bplb:v1.1.1")
    modCompileOnly("maven.modrinth:player-locator-plus:${mod.dep("player_locator_plus")}")

    // Mixin Constraints - embedded
    implementation("com.moulberry:mixinconstraints:1.0.9")
    include("com.moulberry:mixinconstraints:1.0.9")
}

configurations.all {
    resolutionStrategy {
        force("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
    }
}

stonecutter {
    replacements.string {
        direction = eval(current.version, ">1.21.10")
        replace("ResourceLocation", "Identifier")
    }
}

fabricApi {
    configureDataGeneration() {
        outputDirectory = file("$rootDir/src/main/generated")
        client = true
    }
}

tasks {
    processResources {
        exclude("**/neoforge.mods.toml", "**/mods.toml")
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        from(remapJar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
        dependsOn("build")
    }
}

java {
    withSourcesJar()
    val javaCompat = if (stonecutter.eval(stonecutter.current.version, ">=1.21")) {
        JavaVersion.VERSION_21
    } else {
        JavaVersion.VERSION_17
    }
    sourceCompatibility = javaCompat
    targetCompatibility = javaCompat
}

val additionalVersionsStr = findProperty("publish.additionalVersions") as String?
val additionalVersions: List<String> = additionalVersionsStr
    ?.split(",")
    ?.map { it.trim() }
    ?.filter { it.isNotEmpty() }
    ?: emptyList()

publishMods {
    file = tasks.remapJar.map { it.archiveFile.get() }
    additionalFiles.from(tasks.remapSourcesJar.map { it.archiveFile.get() })

    // one of BETA, ALPHA, STABLE
    type = STABLE
    displayName = "${property("mod.name")} ${property("mod.version")} for ${stonecutter.current.version} Fabric"
    version = "${property("mod.version")}+${property("deps.minecraft")}-fabric"
    changelog = provider { rootProject.file("CHANGELOG-LATEST.md").readText() }
    modLoaders.add("fabric")

    modrinth {
        projectId = property("publish.modrinth") as String
        accessToken = env.MODRINTH_API_KEY.orNull()
        minecraftVersions.add(stonecutter.current.version)
        minecraftVersions.addAll(additionalVersions)
        requires("fabric-api")
        optional("cloth-config")
    }

    curseforge {
        projectId = property("publish.curseforge") as String
        accessToken = env.CURSEFORGE_API_KEY.orNull()
        minecraftVersions.add(stonecutter.current.version)
        minecraftVersions.addAll(additionalVersions)
        requires("fabric-api")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "cc.cassian.immersiveoverlays"
            artifactId = "immersiveoverlays-fabric"
            version = "${property("mod.version")}+${property("deps.minecraft")}"

            from(components["java"])
        }
    }
}