plugins {
    id("net.neoforged.moddev.legacyforge")
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
        this["minecraft"] = prop("deps.minecraft")
    }

    filesMatching(listOf("neoforge.mod.json", "META-INF/neoforge.mods.toml", "META-INF/mods.toml")) {
        expand(props)
    }
}

version = "${property("mod.version")}+${property("deps.minecraft")}-forge"
base.archivesName = property("mod.id") as String

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
    maven ( "https://maven.su5ed.dev/releases" )
    maven ( "https://maven.theillusivec4.top/")
    maven ( "https://maven.teamabnormals.com")
    maven ( "https://cursemaven.com" )
    flatDir { dirs("libs") }
}

legacyForge {
    version = property("deps.forge") as String
    validateAccessTransformers = true

    if (hasProperty("deps.parchment")) parchment {
        val (mc, ver) = (property("deps.parchment") as String).split(':')
        mappingsVersion = ver
        minecraftVersion = mc
    }

    runs {
        register("client") {
            gameDirectory = file("run/")
            client()
        }
        register("server") {
            gameDirectory = file("run/")
            server()
        }
    }

    mods {
        register(property("mod.id") as String) {
            sourceSet(sourceSets["main"])
        }
    }
    sourceSets["main"].resources.srcDir("src/main/generated")
}


dependencies {

    // Cloth Config
    if (hasProperty("deps.cloth_version")) {
        modImplementation("me.shedaniel.cloth:cloth-config-forge:${property("deps.cloth_version")}")
    } else {
        compileOnly("me.shedaniel.cloth:cloth-config-forge:11.0.136")
    }

    // Map Atlases
    modImplementation("curse.maven:map-atlases-forge-519759:${mod.dep("map_atlases")}")
    if (stonecutter.eval(mcVersion, "=1.19.2")) {
        modImplementation("maven.modrinth:moonlight:${mod.dep("moonlight")}-forge")
    } else {
        modImplementation("maven.modrinth:moonlight:forge_${mod.dep("moonlight")}")
        modCompileOnly("io.wispforest:accessories-neoforge:${mod.dep("accessories")}")
    }

    // Xaero's Minimap
    modImplementation("maven.modrinth:xaeros-minimap:${mod.dep("xaeros")}_Forge_${mod.dep("xaeros_mc")}")
    modImplementation("maven.modrinth:xaeros-world-map:${mod.dep("xaeros_world_map")}_Forge_${mod.dep("xaeros_mc")}")

    compileOnly("maven.modrinth:terrafirmacraft:tzI7ngJN")
    if (stonecutter.eval(mcVersion, "=1.20.1")) {
        modImplementation("maven.modrinth:oreganized:${mod.dep("oreganized")}-forge")
        modImplementation("maven.modrinth:blueprint:${mod.dep("blueprint")}")
        modImplementation("curse.maven:legendary-survival-overhaul-840254:6834435")
        modRuntimeOnly("maven.modrinth:terrafirmacraft:${mod.dep("tfc")}")
        modRuntimeOnly("maven.modrinth:patchouli:8Qyw08ld")
    }
    modCompileOnly("top.theillusivec4.curios:curios-forge:${mod.dep("curios")}:api")
    modCompileOnly("maven.modrinth:travelersbackpack:${mod.dep("travelers_backpack")}-forge")

    modCompileOnly("maven.modrinth:sophisticated-core:${mod.dep("sophisticated_core")}")
    modCompileOnly("maven.modrinth:sophisticated-backpacks:${mod.dep("sophisticated_backpacks")}")

    modImplementation("maven.modrinth:cold-sweat:${mod.dep("cold_sweat")}")

    // Jade
    modImplementation("maven.modrinth:jade:${mod.dep("jade_version")}")

    modCompileOnly("curse.maven:ecliptic-seasons-1118306:${mod.dep("ecliptic_seasons")}")

    if (stonecutter.eval(mcVersion, ">1.19.2")) {
        modCompileOnly("maven.modrinth:tough-as-nails:${mod.dep("tough_as_nails")}")
    }
    if (stonecutter.eval(mcVersion, ">1.20")) {
        modCompileOnly("maven.modrinth:serene-seasons:${mod.dep("serene_seasons")}-forge")
    }
    if (stonecutter.eval(mcVersion, ">1.20")) {
        modCompileOnly("maven.modrinth:accessorify:${mod.dep("accessorify")}+$minecraft")
    }
    modCompileOnly("dev.su5ed.sinytra.fabric-api:fabric-client-tags-api-v1:1.1.3+5d6761b877")
    modCompileOnly("dev.su5ed.sinytra.fabric-api:fabric-convention-tags-v1:1.5.6+fa3d1c0177")

    modCompileOnly("maven.local:antique-atlas:2.12.0+1.20_mapped_srg_1.20.1")

    modCompileOnly("io.github.llamalad7:mixinextras-common:0.5.0")
    implementation("io.github.llamalad7:mixinextras-forge:0.5.0")
    jarJar("io.github.llamalad7:mixinextras-forge:0.5.0")

}

tasks {
    processResources {
        exclude("**/fabric.mod.json", "**/*.accesswidener", "**/neoforge.mods.toml")
    }

    named("createMinecraftArtifacts") {
        dependsOn("stonecutterGenerate")
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        from(jar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
        dependsOn("build")
    }
}

java {
    withSourcesJar()
    val javaCompat = if (stonecutter.eval(stonecutter.current.version, ">=1.20.5")) {
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
    file = tasks.jar.map { it.archiveFile.get() }
    additionalFiles.from(tasks.named<org.gradle.jvm.tasks.Jar>("sourcesJar").map { it.archiveFile.get() })

    type = STABLE
    displayName = "${property("mod.name")} ${property("mod.version")} for ${stonecutter.current.version} Forge"
    version = "${property("mod.version")}+${property("deps.minecraft")}-forge"
    changelog = provider { rootProject.file("CHANGELOG-LATEST.md").readText() }
    modLoaders.add("forge")

    modrinth {
        projectId = property("publish.modrinth") as String
        accessToken = env.MODRINTH_API_KEY.orNull()
        minecraftVersions.add(stonecutter.current.version)
        minecraftVersions.addAll(additionalVersions)
        optional("cloth-config")
    }

    curseforge {
        projectId = property("publish.curseforge") as String
        accessToken = env.CURSEFORGE_API_KEY.orNull()
        minecraftVersions.add(stonecutter.current.version)
        minecraftVersions.addAll(additionalVersions)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "cc.cassian.immersiveoverlays"
            artifactId = "immersiveoverlays-forge"
            version = "${property("mod.version")}+${property("deps.minecraft")}"

            from(components["java"])
        }
    }
}