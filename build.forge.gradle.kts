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
    maven {
        name = "GeckoLib"
        url = uri("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
        content {
            includeGroupByRegex("software\\.bernie.*")
            includeGroup("com.eliotlash.mclib")
        }
    }
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
    modImplementation("maven.modrinth:jade:${mod.dep("jade")}")

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

    if (stonecutter.eval(mcVersion, ">1.20")) {
        modCompileOnly("maven.local:antique-atlas:2.12.0+1.20_mapped_srg_1.20.1")
        modCompileOnly("dev.su5ed.sinytra:fabric-loader:2.7.11+0.16.5+1.20.1")
    }

    modImplementation("maven.modrinth:breezy:${mod.dep("breezy")}")
    modImplementation("software.bernie.geckolib:geckolib-forge-${property("deps.minecraft")}:${property("deps.geckolib")}")
    implementation("com.eliotlash.mclib:mclib:20")

    modImplementation("maven.modrinth:player-locator-plus-reforged:1.0.1")
    compileOnly("maven.modrinth:dead-reckoning:sH7O0giM")


    modCompileOnly("io.github.llamalad7:mixinextras-common:0.5.0")
    implementation("io.github.llamalad7:mixinextras-forge:0.5.0")
    jarJar("io.github.llamalad7:mixinextras-forge:0.5.0")
    annotationProcessor("org.spongepowered:mixin:0.8.5:processor")
    // Mixin Constraints - embedded
    implementation("com.moulberry:mixinconstraints:1.0.9")
    jarJar("com.moulberry:mixinconstraints:1.0.9")

}


mixin {
    add(sourceSets["main"], "immersiveoverlays.refmap.json")
    config("immersiveoverlays.mixins.json")
}

dependencies {
}

tasks.named<Jar>("jar") {
    manifest {
        attributes(
            "MixinConfigs" to "immersiveoverlays.mixins.json"
        )
    }
}

stonecutter {
    replacements.string {
        direction = eval(current.version, ">1.21.10")
        replace("ResourceLocation", "Identifier")
    }
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
    val javaCompat = JavaVersion.VERSION_17
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