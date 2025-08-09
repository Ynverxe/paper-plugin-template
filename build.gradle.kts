import java.util.*

plugins {
    id("java")
    id("com.gradleup.shadow") version "9.0.1"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.18"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "io.github.ynverxe"
version = "0.1.0"

val pluginName = "Example"
val authors = arrayOf("Ynverxe")
val rootPackage = "io.github.ynverxe.${pluginName.lowercase(Locale.getDefault())}"
val main = "${rootPackage}.${pluginName}Plugin"

repositories {
    gradlePluginPortal()

    mavenCentral()

    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT")
    paperweight.paperDevBundle("1.21.8-R0.1-SNAPSHOT")
}

tasks.processResources {
    val props = mapOf(
        "version" to project.version,
        "main" to main,
        "name" to pluginName,
        "author" to authors.contentToString()
    )

    filteringCharset = "UTF-8"
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}

tasks {
    runServer {
        minecraftVersion("1.21.1")
    }
}

tasks.shadowJar {
    manifest {
        attributes["paperweight-mappings-namespace"] = "mojang"
    }

    relocate("io.github.ynverxe.configuratehelper", "io.github.ynverxe.boxedinpowers.configuratehelper")
    relocate("org.spongepowered.configurate", "io.github.ynverxe.boxedinpowers.configurate")
    relocate("de.tr7zw.changeme.nbtapi", "io.github.ynverxe.boxedinpowers.nbtapi")
    relocate("net.kyori.option", "io.github.ynverxe.boxedinpowers.net.kyori.option")
}