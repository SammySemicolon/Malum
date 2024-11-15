import java.util.*

plugins {
    id("java-library")
    id("maven-publish")
    id("net.neoforged.moddev") version "1.0.17"
}


version = "${property("minecraft_version")}-${property("mod_version")}"
if (System.getenv("BUILD_NUMBER") != null) {
    version = "${property("minecraft_version")}-${property("mod_version")}.${System.getenv("BUILD_NUMBER")}"
}
group = "${property("mod_group_id")}"

val baseArchivesName = "${property("mod_id")}"
base {
    archivesName.set("${property("mod_id")}")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

neoForge {
    version.set(project.property("neo_version").toString())
    parchment {
        mappingsVersion.set(project.property("parchment_mappings_version").toString())
        minecraftVersion.set(project.property("parchment_minecraft_version").toString())
    }

    accessTransformers.from(
        project.files("src/main/resources/META-INF/recipebuilders.cfg", "src/main/resources/META-INF/blockproperties.cfg")
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
            systemProperty("forge.logging.markers", "REGISTRIES")
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
    compileOnly(("mezz.jei:jei-${property("minecraft_version")}-neoforge-api:${property("jei_version")}"))
    runtimeOnly(("mezz.jei:jei-${property("minecraft_version")}-neoforge:${property("jei_version")}"))

    // Curios dependency
    implementation(("top.theillusivec4.curios:curios-neoforge:${property("curios_version")}"))

    implementation(("team.lodestar.lodestone:lodestone:${property("minecraft_version")}-${property("lodestone_version")}"))

    //runtimeOnly(("maven.modrinth:fusion-connected-textures:${property("fusion_version")}-forge-mc${property("minecraft_version")}"))

    // Tetra Optional Dependency

//    compileOnly(("curse.maven:tetra-${property("tetra_version")}"))
//    compileOnly(("se.mickelus.mutil:mutil:${property("mutil_version")}"))

    // FD Optional Dependency
    compileOnly(("curse.maven:farmers-delight-398521:5566383"))

    // Iron's Spellbooks Optional Dependency
    runtimeOnly(("software.bernie.geckolib:geckolib-neoforge-${property("minecraft_version")}:${property("gecko_lib_version")}"))
    runtimeOnly(("dev.kosmx.player-anim:player-animation-lib-forge:${property("player_animator_version")}"))
    implementation(("curse.maven:irons-spells-n-spellbooks-855414:5863590"))

    runtimeOnly(("curse.maven:jeed-532286:5634312"))
    runtimeOnly(("curse.maven:spark-361579:4587309"))
    runtimeOnly(("curse.maven:attributefix-280510:4911084"))
    runtimeOnly(("curse.maven:overloaded-armor-bar-314002:4631133"))
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

    // Exclude .java files or any other files that shouldn't have template expansion
    filesMatching("**/*.java") {
        exclude()
    }

    from("src/main/templates")
    into("build/generated/sources/modMetadata")
}
// Include the output of "generateModMetadata" as an input directory for the build.
// This works with both building through Gradle and the IDE.
sourceSets["main"].resources.srcDir(generateModMetadata)
neoForge.ideSyncTask(generateModMetadata)

java {
    withJavadocJar()
    withSourcesJar()
}


tasks.jar.configure {
    archiveClassifier.set("pure")
}



//jar {
////    exclude 'com/sammy/malum/core/data/**'
//	exclude 'com/sammy/malum/client/model/bbmodels/**'
//	exclude 'assets/malum/models/block/bbmodels/**'
//}


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
