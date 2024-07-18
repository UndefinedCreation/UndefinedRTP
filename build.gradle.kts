plugins {
    java
    kotlin("jvm") version "1.9.22"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.2.2"
}

group = "com.undefined"
version = "1.0"

repositories {
    mavenCentral()
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    maven {
        name = "undefinedapiRepo"
        url = uri("https://repo.undefinedcreation.com/repo")
    }
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}


dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("com.undefined:api:0.5.68:mapped")
    implementation("net.wesjd:anvilgui:1.9.6-SNAPSHOT")
}

tasks {

    assemble {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveFileName.set("UndefinedRTP-shadow.jar")
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "21"
    }
    runServer {
        minecraftVersion("1.20.4")
    }
}

kotlin{
    jvmToolchain(21)
}

