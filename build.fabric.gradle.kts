@file:Suppress("UnstableApiUsage")

plugins {
    id("fabric-loom")
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
    maven ( "https://maven.shedaniel.me/" ) {
        name = "shedaniel (Cloth Config)"
    }
    maven ( "https://maven.terraformersmc.com/releases/" ) {
        name = "Terraformers (Mod Menu)"
    }
    maven ( "https://maven.wispforest.io/releases/" ) {
        name = "Wisp Forest Maven"
    }
    maven ( "https://api.modrinth.com/maven") {
        name = "Modrinth"
    }
    maven ( "https://maven2.bai.lol" ) {
        name = "WTHIT"
    }
    maven ( "https://repo.sleeping.town/" ) {
        name = "Sisby Maven"
    }
    maven ( "https://maven.parchmentmc.org" ) {
        name = "Parchment Mappings"
    }
    maven ( "https://maven.isxander.dev/releases") {
        name = "Xander Maven"
    }
    maven ( "https://maven.nucleoid.xyz" ) {
        name = "Nucleoid Maven (Polymer)"
    }
    maven ( "https://raw.githubusercontent.com/Fuzss/modresources/main/maven/") {
        name = "Fuzs Mod Resources"
    }

    maven("https://mvn.devos.one/releases/" )
    maven("https://mvn.devos.one/snapshots/" )
    maven("https://maven.jamieswhiteshirt.com/libs-release")
    maven("https://maven.ladysnake.org/releases")
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
    if (hasProperty("deps.cloth_version")) {
        modApi("me.shedaniel.cloth:cloth-config-fabric:${property("deps.cloth_version")}")
    } else {
        modCompileOnly("me.shedaniel.cloth:cloth-config-fabric:19.0.147")
    }
    // Mod Menu
    if (hasProperty("deps.modmenu_version"))
        modApi("com.terraformersmc:modmenu:${property("deps.modmenu_version")}")
    else {
        modCompileOnly("com.terraformersmc:modmenu:15.0.0-beta.3")
    }
    modCompileOnly("maven.modrinth:map-atlases:${mod.dep("map_atlases")}")
    if (stonecutter.eval(mcVersion, ">1.19.2")) {
        modCompileOnly("io.wispforest:accessories-fabric:${mod.dep("accessories")}")
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

    // Sophisticated Backpacks
    modCompileOnly("maven.modrinth:9jxwkYQL:${mod.dep("sophisticated_core")}")
    modCompileOnly("maven.modrinth:ouNrBQtq:${mod.dep("sophisticated_backpacks")}")
    modCompileOnly("io.github.fabricators_of_create.Porting-Lib:transfer:2.3.9+1.20.1")
    if (stonecutter.eval(mcVersion, "=1.19.2")) {
        modCompileOnly("io.github.fabricators_of_create.Porting-Lib:Porting-Lib:2.1.1453+1.19.2")
    }

    // Jade
    if (hasProperty("deps.jade")) {
        modCompileOnly("maven.modrinth:jade:${property("deps.jade")}")
        modLocalRuntime("maven.modrinth:jade:${property("deps.jade")}")
    } else {
        modCompileOnly("maven.modrinth:jade:19.3.2+fabric")
    }

    //Xaero's
    modCompileOnly("maven.modrinth:xaeros-minimap:${mod.dep("xaeros")}_Fabric_${mod.dep("xaeros_mc")}")
    modCompileOnly("maven.modrinth:xaeros-world-map:${mod.dep("xaeros_world_map")}_Fabric_${mod.dep("xaeros_mc")}")


    if (stonecutter.eval(mcVersion, ">1.21")) {
        modCompileOnly("org.ladysnake.cardinal-components-api:cardinal-components-entity:6.1.2")
        modCompileOnly("org.ladysnake.cardinal-components-api:cardinal-components-base:6.1.2")
    } else {
        modCompileOnly("dev.onyxstudios.cardinal-components-api:cardinal-components-entity:5.2.3")
        modCompileOnly("dev.onyxstudios.cardinal-components-api:cardinal-components-base:5.2.3")
    }

    if (stonecutter.eval(mcVersion, "<1.21.4")) {
        modCompileOnly("dev.emi:trinkets:${mod.dep("trinkets")}")
    } else {
        modCompileOnly("maven.modrinth:trinkets-canary:${mod.dep("trinkets")}")
    }

    modCompileOnly("maven.modrinth:bplb:v1.0.0")
    modCompileOnly("maven.modrinth:player-locator-plus:${mod.dep("player_locator_plus")}")
}

configurations.all {
    resolutionStrategy {
        force("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
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