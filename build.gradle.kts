import java.text.SimpleDateFormat
import java.util.*

plugins {
    id("eclipse")
    id("idea")
    id("maven-publish")
    id("net.minecraftforge.gradle").version("[6.0,6.2)")
    id("org.parchmentmc.librarian.forgegradle").version("1.+")
    id("org.spongepowered.mixin")
}

val minecraftVersion: String by extra
val minecraftVersionRange: String by extra
val loaderVersionRange: String by extra
val forgeVersionRange: String by extra
val modVersion: String by extra
val modGroupId: String by extra
val modId: String by extra
val modAuthors: String by extra
val modDescription: String by extra
val modLicense: String by extra
val modName: String by extra
val parchmentChannel: String by extra
val parchmentVersion: String by extra
val forgeVersion: String by extra
val jeiVersion: String by extra
val curiosVersion: String by extra
val mixinVersion: String by extra
val modJavaVersion: String by extra
val lodestoneVersion: String by extra
val fusionVersion: String by extra
var caelusVersion: String by extra
val geckoLibVersion: String by extra
val playerAnimatorVersion: String by extra
val ironsSpellsVersion: String by extra

jarJar.enable()

version = "$minecraftVersion-$modVersion"
if (System.getenv("BUILD_NUMBER") != null) {
    version = "$minecraftVersion-$modVersion.${System.getenv("BUILD_NUMBER")}"
}
group = modGroupId

val baseArchivesName = modId
base {
    archivesName.set(baseArchivesName)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(modJavaVersion))
    }
}

mixin {
    add(sourceSets.main.get(), "${modId}.refmap.json")
    config("malum.mixins.json")
}

minecraft {
    mappings(parchmentChannel, parchmentVersion)

    accessTransformer(file("src/main/resources/META-INF/accesstransformer.cfg"))

    copyIdeResources.set(true)
    runs {
        configureEach {
            workingDirectory(project.file("run"))

            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")

            mods {
                create("${modId}") {
                    source(sourceSets.main.get())
                }
            }
        }

        create("client") {
            property("forge.enabledGameTestNamespaces", modId)
            arg("-mixin.config=" + modId + ".mixins.json")
        }

        create("server") {
            property("forge.enabledGameTestNamespaces", modId)
            args("--nogui")
            arg("-mixin.config=" + modId + ".mixins.json")
        }

        create("data") {
            workingDirectory(project.file("run-data"))

            args(
                    "--mod",
                    modId,
                    "--all",
                    "--output",
                    file("src/generated/resources/"),
                    "--existing",
                    file("src/main/resources/")
            )
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
        name = "jitpack"
        url = uri("https://jitpack.io")
        content {
            includeGroup("io.github")
        }
    }

    //Iron's Spellbooks requirements
    maven {
        name = "Iron's Maven - Release"
        url = uri("https://code.redspace.io/releases")
    }
    maven {
        url = uri("https://maven.kosmx.dev/")
    }
    maven {
        url = uri("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
    }

    exclusiveContent {
        forRepository {
            maven {
                name = "Modrinth"
                url = uri("https://api.modrinth.com/maven")
            }
        }
        forRepositories(fg.repository) // Only add this if you're using ForgeGradle, otherwise remove this line
        filter {
            includeGroup("maven.modrinth")
        }
    }

}

dependencies {
    minecraft("net.minecraftforge:forge:${minecraftVersion}-${forgeVersion}")

    if (System.getProperty("idea.sync.active") != "true") {
        annotationProcessor("org.spongepowered:mixin:${mixinVersion}:processor")
    }

    // MixinExtras
    compileOnly(annotationProcessor("io.github.llamalad7:mixinextras-common:0.3.2")!!)
    implementation(jarJar("io.github.llamalad7:mixinextras-forge:0.3.2")!!) {
        jarJar.ranged(this, "[0.3.2,)")
    }

    // JEI Dependency
    compileOnly(fg.deobf("mezz.jei:jei-${minecraftVersion}-forge-api:${jeiVersion}"))
    compileOnly(fg.deobf("mezz.jei:jei-${minecraftVersion}-common-api:${jeiVersion}"))
    runtimeOnly(fg.deobf("mezz.jei:jei-${minecraftVersion}-forge:${jeiVersion}"))

    // Curios dependency
    compileOnly(fg.deobf("top.theillusivec4.curios:curios-forge:${curiosVersion}:api"))
    runtimeOnly(fg.deobf("top.theillusivec4.curios:curios-forge:${curiosVersion}"))

    implementation(fg.deobf("team.lodestar.lodestone:lodestone:${minecraftVersion}-${lodestoneVersion}"))

    //FD Optional Dependency
    compileOnly(fg.deobf("curse.maven:farmers_delight-398521:4638874"))

    //Tetra Optional Dependency
    compileOnly(fg.deobf("curse.maven:mutil-351914:4824501"))
    compileOnly(fg.deobf("curse.maven:tetra-289712:5544287"))

    //Iron's Spellbooks Optional Dependency
    compileOnly(fg.deobf("top.theillusivec4.caelus:caelus-forge:${caelusVersion}:api"))
//    runtimeOnly(fg.deobf("top.theillusivec4.caelus:caelus-forge:${caelusVersion}"))
    compileOnly(fg.deobf("software.bernie.geckolib:geckolib-forge-${geckoLibVersion}"))
    compileOnly(fg.deobf("dev.kosmx.player-anim:player-animation-lib-forge:${playerAnimatorVersion}"))
//    compileOnly(fg.deobf("io.redspace.ironsspellbooks:irons_spellbooks:${ironsSpellsVersion}:api"))
    compileOnly(fg.deobf("io.redspace.ironsspellbooks:irons_spellbooks:${ironsSpellsVersion}"))

    //Apothic Attributes
    compileOnly(fg.deobf("curse.maven:placebo-283644:5414631"))
    compileOnly(fg.deobf("curse.maven:apothic-attributes-898963:5634071"))

    //Misc
    runtimeOnly(fg.deobf("curse.maven:spark-361579:4587309"))
    runtimeOnly(fg.deobf("curse.maven:attributefix-280510:4911084"))
    runtimeOnly(fg.deobf("curse.maven:overloaded-armor-bar-314002:4631133"))
    runtimeOnly(fg.deobf("maven.modrinth:fusion-connected-textures:${fusionVersion}-forge-mc${minecraftVersion}"))
}

tasks.withType<ProcessResources> {
    inputs.property("version", version)

    filesMatching(listOf("META-INF/mods.toml", "pack.mcmeta")) {
        expand(
                mapOf(
                        "forgeVersionRange" to forgeVersionRange,
                        "loaderVersionRange" to loaderVersionRange,
                        "minecraftVersion" to minecraftVersion,
                        "minecraftVersionRange" to minecraftVersionRange,
                        "modAuthors" to modAuthors,
                        "modDescription" to modDescription,
                        "modId" to modId,
                        "modJavaVersion" to modJavaVersion,
                        "modName" to modName,
                        "modVersion" to version,
                        "modLicense" to modLicense,
                )
        )
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

tasks.withType<Jar> {
    val now = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date())
    manifest {
        attributes(
                mapOf(
                        "Specification-Title" to modName,
                        "Specification-Vendor" to modAuthors,
                        "Specification-Version" to '1',
                        "Implementation-Title" to modName,
                        "Implementation-Version" to version,
                        "Implementation-Vendor" to modAuthors,
                        "Implementation-Timestamp" to now,
                )
        )
    }
    finalizedBy("reobfJar")
    finalizedBy("reobfJarJar")
}

tasks.jar.configure {
    archiveClassifier.set("pure")
}

tasks.jarJar.configure {
    archiveClassifier.set("")
}

//jar {
////    exclude 'com/sammy/malum/core/data/**'
//	exclude 'com/sammy/malum/client/model/bbmodels/**'
//	exclude 'assets/malum/models/block/bbmodels/**'
//}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifactId = baseArchivesName
            from(components["java"])
            fg.component(this)
            jarJar.component(this)
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
