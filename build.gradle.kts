plugins {
    id("java-library")
    id("maven-publish")
    id("net.neoforged.moddev") version "2.0.30-beta"
}

version = "${property("minecraft_version")}-${property("mod_version")}"
if (System.getenv("BUILD_NUMBER") != null) {
    version = "$version.${System.getenv("BUILD_NUMBER")}"
}
val baseArchivesName = project.property("mod_id").toString()
base {
    archivesName.set(project.property("mod_id").toString())
}
group = "${property("mod_group_id")}"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.named<Wrapper>("wrapper") {
    distributionType = Wrapper.DistributionType.BIN
}

val localRuntime: Configuration by configurations.creating
configurations.runtimeClasspath {
    extendsFrom(localRuntime)
}

neoForge {
    version.set(project.property("neo_version").toString())

    parchment {
        mappingsVersion.set(project.property("parchment_mappings_version").toString())
        minecraftVersion.set(project.property("parchment_minecraft_version").toString())
    }

    setAccessTransformers(
        "src/main/resources/META-INF/accesstransformer.cfg",
        "src/main/resources/META-INF/recipebuilders.cfg",
        "src/main/resources/META-INF/blockproperties.cfg",
        "src/main/resources/META-INF/renderstates.cfg"
    )

    runs {
        register("client") {
            client()

            // Comma-separated list of namespaces to load gametests from. Empty = all namespaces.
            systemProperty("neoforge.enabledGameTestNamespaces", project.property("mod_id").toString())
        }

        register("server") {
            server()
            programArgument("--nogui")
            systemProperty("neoforge.enabledGameTestNamespaces", project.property("mod_id").toString())
        }

        register("gameTestServer") {
            type = "gameTestServer"
            systemProperty("neoforge.enabledGameTestNamespaces", project.property("mod_id").toString())
        }

        register("data") {
            data()
            programArguments.addAll(
                "--mod", project.property("mod_id").toString(),
                "--all",
                "--output", file("src/generated/resources/").absolutePath,
                "--existing", file("src/main/resources/").absolutePath
            )
        }

        configureEach {
            jvmArgument("-Dmixin.debug=true")
            jvmArgument("-Xmx4G")
            systemProperty("neoforge.logging.markers", "REGISTRIES")
            logLevel = org.slf4j.event.Level.DEBUG
        }
    }

    mods {
        create("${property("mod_id")}") {
            sourceSet(sourceSets.main.get())
        }
    }
}

sourceSets {
    main {
        resources.srcDir("src/generated/resources")
    }
}

repositories {
    flatDir {
        dirs("lib")
    }
    mavenLocal()
    mavenCentral()
    maven {
        name = "Curios maven"
        url = uri("https://maven.theillusivec4.top/")
    }
    maven {
        name = "JEI maven"
        url = uri("https://dvs1.progwml6.com/files/maven")
    }
    maven {
        name = "tterrag maven"
        url = uri("https://maven.tterrag.com/")
    }
    maven {
        name = "BlameJared maven"
        url = uri("https://maven.blamejared.com/")
    }
    maven {
        name = "KosmX's maven"
        url = uri("https://maven.kosmx.dev/")
    }
    maven {
        name = "Curse Maven"
        url = uri("https://cursemaven.com")
        content {
            includeGroup("curse.maven")
        }
    }
    maven {
        url = uri("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
        content {
            includeGroup("software.bernie.geckolib")
        }
    }
    maven {
        name = "ModMaven"
        url = uri("https://modmaven.dev")
    }
    maven {
        name = "jitpack"
        url = uri("https://jitpack.io")
        content {
            includeGroup("io.github")
        }
    }
    maven {
        name = "OctoStudios"
        url = uri("https://maven.octo-studios.com/releases")
    }

}

dependencies {
    // JEI Dependency
    compileOnlyApi(("mezz.jei:jei-${project.property("minecraft_version")}-neoforge-api:${project.property("jei_version")}"))
    runtimeOnly(("mezz.jei:jei-${project.property("minecraft_version")}-neoforge:${project.property("jei_version")}"))

    // Curios dependency
    compileOnlyApi(("top.theillusivec4.curios:curios-neoforge:${property("curios_version")}"))
    runtimeOnly(("top.theillusivec4.curios:curios-neoforge:${property("curios_version")}"))

    compileOnlyApi(("team.lodestar.lodestone:lodestone:${property("minecraft_version")}-${property("lodestone_version")}"))
    runtimeOnly(("team.lodestar.lodestone:lodestone:${property("minecraft_version")}-${property("lodestone_version")}"))

    // Tetra Optional Dependency

//    compileOnly(("curse.maven:tetra-${property("tetra_version")}"))
//    compileOnly(("se.mickelus.mutil:mutil:${property("mutil_version")}"))

    compileOnly(("curse.maven:farmers-delight-398521:5878217"))
    localRuntime(("curse.maven:farmers-delight-398521:5878217"))

    compileOnly(("software.bernie.geckolib:geckolib-neoforge-${property("minecraft_version")}:${property("gecko_lib_version")}"))
    compileOnly(("dev.kosmx.player-anim:player-animation-lib-forge:${property("player_animator_version")}"))
    compileOnly(("curse.maven:irons-spells-n-spellbooks-855414:5863590"))

    localRuntime(("curse.maven:jeed-532286:5693385"))
    localRuntime(("curse.maven:spark-361579:5759671"))
    localRuntime(("curse.maven:fusion-connected-textures-854949:6073987"))
    localRuntime(("curse.maven:overloaded-armor-bar-314002:5537850"))

    localRuntime(("curse.maven:bookshelf-228525:5824127")) //Required for AttributeFix
    localRuntime(("curse.maven:prickle-1023259:5836410")) //Required for AttributeFix
    localRuntime(("curse.maven:attributefix-280510:5824104"))
}

val generateModMetadata by tasks.registering(ProcessResources::class) {
    val replaceProperties = mapOf(
        "minecraft_version" to project.findProperty("minecraft_version") as String,
        "minecraft_version_range" to project.findProperty("minecraft_version_range") as String,
        "neo_version" to project.findProperty("neo_version") as String,
        "neo_version_range" to project.findProperty("neo_version_range") as String,
        "loader_version_range" to project.findProperty("loader_version_range") as String,
        "mod_id" to project.findProperty("mod_id") as String,
        "mod_name" to project.findProperty("mod_name") as String,
        "mod_license" to project.findProperty("mod_license") as String,
        "mod_version" to project.findProperty("mod_version") as String,
        "mod_authors" to project.findProperty("mod_authors") as String,
        "mod_description" to project.findProperty("mod_description") as String
    )
    inputs.properties(replaceProperties)
    expand(replaceProperties)

    filesMatching("**/*.java") {
        exclude()
    }

    from("src/main/templates")
    into("build/generated/sources/modMetadata")
}
sourceSets["main"].resources.srcDir(generateModMetadata)
neoForge.ideSyncTask(generateModMetadata)

java {
//    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifactId = "${property("mod_id")}"
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("file://${System.getenv("local_maven")}")
        }
    }
}

idea {
    module {
        for (fileName in listOf("run", "out", "logs")) {
            excludeDirs.add(file(fileName))
        }
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
