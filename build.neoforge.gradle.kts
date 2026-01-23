plugins {
    id("net.neoforged.moddev")
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

version = "${property("mod.version")}+${property("deps.minecraft")}-neoforge"
base.archivesName = property("mod.id") as String

jsonlang {
    languageDirectories = listOf("assets/${property("mod.id")}/lang")
    prettyPrint = true
}


repositories {
    mavenLocal()
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
        name = "Sinytra"
        url = uri("https://maven.su5ed.dev/releases")
        content {
            includeGroupAndSubgroups("org.sinytra")
            includeGroupAndSubgroups("dev.su5ed")
        }
    }
    maven {
        name = "Curios"
        url = uri("https://maven.theillusivec4.top/")
        content {
            includeGroup("top.theillusivec4.curios")
        }
    }
    maven {
        name = "Team Abnormals"
        url = uri("https://maven.teamabnormals.com")
        content {
            includeGroup("com.teamabnormals")
        }
    }
    maven {
        name = "CurseForge"
        url = uri("https://cursemaven.com")
        content {
            includeGroup("curse.maven")
        }
    }
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
        configureEach {
            systemProperty("neoforge.warnings.onlyin.hide", "true")
        }
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
        compileOnly("maven.modrinth:jade:${property("deps.jade")}")
        runtimeOnly("maven.modrinth:jade:${property("deps.jade")}")
    } else {
        compileOnly("maven.modrinth:jade:19.3.1+neoforge")
    }

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
    compileOnly("maven.modrinth:xaeros-minimap:${mod.dep("xaeros")}_NeoForge_${mod.dep("xaeros_mc")}")
    compileOnly("maven.modrinth:xaeros-world-map:${mod.dep("xaeros_world_map")}_NeoForge_${mod.dep("xaeros_mc")}")

    // Cold Sweat
    compileOnly("maven.modrinth:cold-sweat:${mod.dep("cold_sweat")}")

    compileOnly("io.wispforest:accessories-neoforge:${mod.dep("accessories")}")
    compileOnly("top.theillusivec4.curios:curios-neoforge:${mod.dep("curios")}:api")
    compileOnly("maven.modrinth:travelersbackpack:${mod.dep("travelers_backpack")}-neoforge")

    if (stonecutter.eval(mcVersion, "=1.21.1")) {
        compileOnly("maven.modrinth:oreganized:${property("deps.oreganized")}")
        runtimeOnly("maven.modrinth:oreganized:${property("deps.oreganized")}")
        implementation("com.teamabnormals:blueprint:1.21.1-8.0.5")
        implementation("curse.maven:legendary-survival-overhaul-840254:7278267")
        runtimeOnly("maven.modrinth:terrafirmacraft:JCusAJHn")
        runtimeOnly("maven.modrinth:patchouli:h6hKI2ob")
        runtimeOnly("curse.maven:ecliptic-seasons-1118306:7041469")
        compileOnly("maven.local:antique-atlas:2.12.0+1.21_mapped_moj_1.21.1")
        compileOnly("org.sinytra:forgified-fabric-loader:2.5.55+0.17.2+1.21.1")
    }

    compileOnly("maven.modrinth:terrafirmacraft:JCusAJHn")
    compileOnly("curse.maven:ecliptic-seasons-1118306:7041469")
    compileOnly("maven.modrinth:tough-as-nails:${mod.dep("tough_as_nails")}")
    compileOnly("maven.modrinth:serene-seasons:${mod.dep("serene_seasons")}-forge")
    compileOnly("org.sinytra.forgified-fabric-api:fabric-client-tags-api-v1:1.1.15+e053909619")
    compileOnly("org.sinytra.forgified-fabric-api:fabric-convention-tags-v2:2.11.0+87e5848019")
    compileOnly("maven.modrinth:sophisticated-core:${mod.dep("sophisticated_core")}")
    compileOnly("maven.modrinth:sophisticated-backpacks:${mod.dep("sophisticated_backpacks")}")
    compileOnly("maven.modrinth:dead-reckoning:6tHF0yCl")

    if (hasProperty("deps.thermoo")) {
        compileOnly("maven.modrinth:thermoo:${property("deps.thermoo")}")
        runtimeOnly("maven.modrinth:thermoo:${property("deps.thermoo")}")
    }

    // Mixin Constraints - embedded
    implementation("com.moulberry:mixinconstraints:1.0.9")
    jarJar("com.moulberry:mixinconstraints:1.0.9")
}

stonecutter {
    replacements.string {
        direction = eval(current.version, ">1.21.10")
        replace("ResourceLocation", "Identifier")
    }
}

java {
    withSourcesJar()
    val javaCompat = if (stonecutter.eval(stonecutter.current.version, ">26")) {
        JavaVersion.VERSION_25
    } else {
        JavaVersion.VERSION_21
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
    displayName = "${property("mod.name")} ${property("mod.version")} for ${stonecutter.current.version} NeoForge"
    version = "${property("mod.version")}+${property("deps.minecraft")}-neoforge"
    changelog = provider { rootProject.file("CHANGELOG-LATEST.md").readText() }
    modLoaders.add("neoforge")

    modrinth {
        projectId = property("publish.modrinth") as String
        accessToken = env.MODRINTH_API_KEY.orNull()
        minecraftVersions.add(property("deps.minecraft") as String)
        minecraftVersions.addAll(additionalVersions)
        optional("cloth-config")
    }

    curseforge {
        projectId = property("publish.curseforge") as String
        accessToken = env.CURSEFORGE_API_KEY.orNull()
        if (hasProperty("deps.curseforge_minecraft_version")) {
            minecraftVersions.add(property("deps.curseforge_minecraft_version") as String)
        } else {
            minecraftVersions.add(property("deps.minecraft") as String)
        }
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