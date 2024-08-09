import kotlinx.kover.gradle.plugin.dsl.CoverageUnit
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    id("com.gradle.plugin-publish") version "1.2.1"
    kotlin("jvm") version "2.0.10"
    kotlin("plugin.serialization") version "2.0.10"
    id("org.jetbrains.kotlinx.kover") version "0.8.1"
}

val groupStr = "io.github.klahap.coam"
val gitRepo = "https://github.com/klahap/coam"
val pluginClass = "$groupStr.Plugin"

version = System.getenv("GIT_TAG_VERSION") ?: "1.0.0-SNAPSHOT"
group = groupStr

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.swagger.parser.v3:swagger-parser:2.1.22")
    testImplementation("io.kotest:kotest-assertions-core:5.9.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

gradlePlugin {
    website = gitRepo
    vcsUrl = "$gitRepo.git"

    val generateFrappeDsl by plugins.creating {
        id = groupStr
        implementationClass = pluginClass
        displayName = "OpenAPI Spec Merger Checker (coam)"
        description =
            "The OpenAPI Spec Merger Checker (coam) is a Gradle plugin that verifies whether all paths of one OpenAPI specification file are fully included, through \$ref, in another OpenAPI specification file."
        tags = listOf("OpenAPI", "API Validation", "OpenAPI Merge")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
        freeCompilerArgs.add("-Xcontext-receivers")
        jvmTarget.set(JvmTarget.JVM_21)
        languageVersion = KotlinVersion.KOTLIN_2_0
    }
}

kover {
    reports {
        filters {
            excludes {
                classes(
                    pluginClass,
                )
            }
        }
        verify {
            CoverageUnit.values().forEach { covUnit ->
                rule("minimal ${covUnit.name.lowercase()} coverage rate") {
                    minBound(100, coverageUnits = covUnit)
                }
            }
        }
    }
}
