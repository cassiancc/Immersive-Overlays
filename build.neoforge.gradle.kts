plugins {
    id("net.neoforged.moddev")
    id("dev.kikugie.postprocess.jsonlang")
    id("me.modmuss50.mod-publish-plugin")
    id("maven-publish")
    id("org.sinytra.adapter.userdev")
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

version = "${property("mod.version")}+${property("deps.minecraft")}-neoforge"
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

neoForge {
    version = property("deps.neoforge") as String
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

tasks {
    processResources {
        exclude("**/fabric.mod.json", "**/*.accesswidener", "**/mods.toml")
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

dependencies {

    // Cloth Config
    if (hasProperty("deps.cloth_version")) {
        implementation("me.shedaniel.cloth:cloth-config-neoforge:${property("deps.cloth_version")}")
    } else {
        compileOnly("me.shedaniel.cloth:cloth-config-neoforge:19.0.147")
    }
    // Jade
    if (hasProperty("deps.jade")) {
        runtimeOnly("maven.modrinth:jade:${property("deps.jade")}")
    }
    compileOnly("maven.modrinth:jade:19.3.1+neoforge")

    // Map Atlases
    if (stonecutter.eval(mcVersion, "<1.21.2")) {
        implementation("curse.maven:map-atlases-forge-519759:${mod.dep("map_atlases")}")
        implementation("maven.modrinth:moonlight:neoforge_${mod.dep("moonlight")}")
    }
    else {
        compileOnly("curse.maven:map-atlases-forge-519759:${mod.dep("map_atlases")}")
        compileOnly("maven.modrinth:moonlight:neoforge_${mod.dep("moonlight")}")
    }

    // Xaero's Minimap
    implementation("maven.modrinth:xaeros-minimap:${mod.dep("xaeros")}_NeoForge_${mod.dep("xaeros_mc")}")
    implementation("maven.modrinth:xaeros-world-map:${mod.dep("xaeros_world_map")}_NeoForge_${mod.dep("xaeros_mc")}")

    // Cold Sweat
    implementation("maven.modrinth:cold-sweat:${mod.dep("cold_sweat")}")


    if (stonecutter.eval(mcVersion, ">1.19.2")) {
        compileOnly("io.wispforest:accessories-neoforge:${mod.dep("accessories")}")
    }
    if (stonecutter.eval(mcVersion, ">1.21") && stonecutter.eval(mcVersion, "<1.21.5")) {
        compileOnly("maven.modrinth:accessorify:${mod.dep("accessorify")}+$minecraft")
    }
    else if (stonecutter.eval(mcVersion, ">=1.21.5")) {
        compileOnly("maven.modrinth:accessorify:${mod.dep("accessorify")}+1.21.4")
    }
    compileOnly("top.theillusivec4.curios:curios-neoforge:${mod.dep("curios")}:api")
    compileOnly("maven.modrinth:travelersbackpack:${mod.dep("travelers_backpack")}-neoforge")

    compileOnly("maven.modrinth:oreganized:5.0.0")
    if (stonecutter.eval(mcVersion, "=1.21.1")) {
        runtimeOnly("maven.modrinth:oreganized:5.0.0")
        implementation("com.teamabnormals:blueprint:1.21.1-8.0.5")
    }

    compileOnly("maven.modrinth:terrafirmacraft:JCusAJHn")
    if (stonecutter.eval(mcVersion, "=1.21.1")) {
        runtimeOnly("maven.modrinth:terrafirmacraft:JCusAJHn")
        runtimeOnly("maven.modrinth:patchouli:h6hKI2ob")
    }

    compileOnly("curse.maven:ecliptic-seasons-1118306:7041469")
    if (stonecutter.eval(mcVersion, "=1.21.1")) {
        runtimeOnly("curse.maven:ecliptic-seasons-1118306:7041469")
    }

    if (stonecutter.eval(mcVersion, ">1.19.2")) {
        compileOnly("maven.modrinth:tough-as-nails:${mod.dep("tough_as_nails")}")
    }
    if (stonecutter.eval(mcVersion, ">1.20")) {
        compileOnly("maven.modrinth:serene-seasons:${mod.dep("serene_seasons")}-forge")
    }
    compileOnly("org.sinytra.forgified-fabric-api:fabric-client-tags-api-v1:1.1.15+e053909619")
    compileOnly("org.sinytra.forgified-fabric-api:fabric-convention-tags-v2:2.11.0+87e5848019")

    if (stonecutter.eval(mcVersion, "1.21.1")) {
        compileOnly("maven.local:antique-atlas:2.12.0+1.21_mapped_moj_1.21.1")
    }

    implementation("maven.modrinth:sophisticated-core:${mod.dep("sophisticated_core")}")
    implementation("maven.modrinth:sophisticated-backpacks:${mod.dep("sophisticated_backpacks")}")

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
    displayName = "${property("mod.name")} ${property("mod.version")} for ${stonecutter.current.version} Neoforge"
    version = "${property("mod.version")}+${property("deps.minecraft")}-neoforge"
    changelog = provider { rootProject.file("CHANGELOG-LATEST.md").readText() }
    modLoaders.add("neoforge")

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
            artifactId = "immersiveoverlays-neoforge"
            version = "${property("mod.version")}+${property("deps.minecraft")}"

            from(components["java"])
        }
    }
}