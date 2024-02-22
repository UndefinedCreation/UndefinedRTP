plugins {
    java
    kotlin("jvm") version "1.9.22"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.papermc.paperweight.userdev") version "1.5.10"
    id("xyz.jpenilla.run-paper") version "2.2.2"
}

group = "com.redmagic"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven {
        name = "undefinedapiRepo"
        url = uri("https://repo.undefinedcreation.com/repo")
    }
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}


dependencies {
    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("com.redmagic:UndefinedAPI:0.3.58")
    implementation("net.wesjd:anvilgui:1.9.2-SNAPSHOT")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
    shadowJar {
        archiveFileName.set("UndefinedRTP-shadow.jar")
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    runServer {
        minecraftVersion("1.20.4")
    }
}

kotlin{
    jvmToolchain(17)
}

