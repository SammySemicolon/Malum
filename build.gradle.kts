plugins {
    id("fabric-loom") version "1.9-SNAPSHOT"
    `maven-publish`
    java
}

val port_lib_modules: String by extra


version = "${property("minecraft_version")}-${property("mod_version")}-fabric"

base {
    archivesName.set("${property("mod_id")}")
}

sourceSets {
    named("main") {
        resources {
            srcDir("src/generated/resources")
        }
    }
}


loom {
    accessWidenerPath = file("src/main/resources/malum.accesswidener")
    runs {
        create("data") {
            client()
            name("Data Generation")
            vmArg("-Dfabric-api.datagen")
            vmArg("-Dfabric-api.datagen.output-dir=${file("src/generated/resources")}")
            vmArg("-Dfabric-api.datagen.modid=malum")
            //vmArg("-Dfabric-api.datagen.strict-validation")

            property("porting_lib.datagen.existing_resources", file("src/main/resources").absolutePath)
            property("malum.data.server", "false")

            runDir("build/datagen")
        }
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
    maven(url = "https://api.modrinth.com/maven")
    maven {
        name = "jitpack"
        url = uri("https://jitpack.io")
        content {
            includeGroup("io.github")
        }
    }
    maven(url = "https://maven.ladysnake.org/releases")
    maven("https://maven.terraformersmc.com/")

    maven( "https://maven.parchmentmc.org")
    maven("https://mvn.devos.one/snapshots/")
    maven("https://mvn.devos.one/releases/")
    maven("https://maven.jamieswhiteshirt.com/libs-release")
    maven("https://maven.greenhouseteam.dev/releases/")
    maven("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/") // Forge Config API Port
    maven("https://maven.shedaniel.me/")

}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")

    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${property("parchment_minecraft_version")}:${property("parchment_version")}@zip")
    })

    modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_api_version")}")

    include("com.github.Chocohead:Fabric-ASM:v2.3")
    modImplementation("com.github.Chocohead:Fabric-ASM:v2.3")

    include("me.shedaniel.cloth:cloth-config-fabric:15.0.140")
    modImplementation("me.shedaniel.cloth:cloth-config-fabric:15.0.140")

    // JEI Dependency
    modCompileOnlyApi("mezz.jei:jei-${property("minecraft_version")}-common-api:${property("jei_version")}")
    modCompileOnlyApi("mezz.jei:jei-${property("minecraft_version")}-fabric-api:${property("jei_version")}")
    // at runtime, use the full JEI jar for Fabric
    //modRuntimeOnly("mezz.jei:jei-${property("minecraft_version")}-fabric:${property("jei_version")}")
    //EMI
    modCompileOnly("dev.emi:emi-fabric:${property("emi_version")}:api")
    modLocalRuntime("dev.emi:emi-fabric:${property("emi_version")}")
    modCompileOnly("me.shedaniel:RoughlyEnoughItems-api-fabric:${property("rei_version")}")
    modCompileOnly("me.shedaniel:RoughlyEnoughItems-default-plugin-fabric:${property("rei_version")}")

    // Trinkets Dependency
    modImplementation("dev.emi:trinkets:${property("trinkets_version")}")

    //modImplementation("team.lodestar.lodestone:lodestone:${property("minecraft_version")}-${property("lodestone_version")}-fabric")
    modImplementation("maven.modrinth:lodestonelib:${property("minecraft_version")}-${property("lodestone_version")}-fabric")
    modApi("org.ladysnake.cardinal-components-api:cardinal-components-base:6.1.0")
    modApi("org.ladysnake.cardinal-components-api:cardinal-components-entity:6.1.0")
    //modImplementation("dev.onyxstudios.cardinal-components-api:cardinal-components-entity:${property("cca_version")}")
    //modImplementation("dev.onyxstudios.cardinal-components-api:cardinal-components-world:${property("cca_version")}")

    include("com.terraformersmc.terraform-api:terraform-wood-api-v1:${property("terraform_api_version")}")
    modImplementation("com.terraformersmc.terraform-api:terraform-wood-api-v1:${property("terraform_api_version")}")
    //include("com.terraformersmc.terraform-api:terraform-wood-api-v1:${property("terraform_api_version")}")

    port_lib_modules.split(",").forEach { module ->
        include(("io.github.fabricators_of_create.Porting-Lib:$module:${property("port_lib_version")}"))
        modImplementation(("io.github.fabricators_of_create.Porting-Lib:$module:${property("port_lib_version")}"))
    }

    //include("com.jamieswhiteshirt:reach-entity-attributes:${property("reach_entity_attributes_version")}")
    //modImplementation("com.jamieswhiteshirt:reach-entity-attributes:${property("reach_entity_attributes_version")}")

    modImplementation("vectorwing:FarmersDelight:${property("farmers_delight_version")}")
    //modRuntimeOnly("com.simibubi.create:create-fabric-1.20.1:0.5.1-f-build.1417+mc1.20.1")
    //modRuntimeOnly("fuzs.forgeconfigapiport:forgeconfigapiport-fabric:${property("forge_config_api_port_version")}")

    modCompileOnly("maven.modrinth:fusion-connected-textures:${property("fusion_version")}-fabric-mc1.21")
    //modImplementation("curse.maven:jeed-532286:5186338")

    //modRuntimeOnly("curse.maven:world-stripper-250603:4578576")
    //modRuntimeOnly("curse.maven:spark-361579:4738953")
    //modRuntimeOnly("curse.maven:attributefix-280510:4911083")
    //modCompileOnly("maven.modrinth:botania:${property("botania_version")}")
    //modRuntimeOnly("curse.maven:overloaded-armor-bar-314002:5208706")
}

tasks {

    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(getProperties())
            expand(mutableMapOf("version" to project.version))
        }
    }

    jar {
        from("LICENSE")
    }

    compileJava{
        targetCompatibility = "21"
    }
}

java {
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

//jar {
////    exclude 'com/sammy/malum/core/data/**'
//	exclude 'com/sammy/malum/client/model/bbmodels/**'
//	exclude 'assets/malum/models/block/bbmodels/**'
//}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release = 21
}
