import nl.littlerobots.vcu.plugin.resolver.VersionSelectors

plugins {

    `maven-publish`
    // trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.kover)
    alias(libs.plugins.com.linecorp.build.recipe)
    alias(libs.plugins.build.dokka.plugin)
    alias(libs.plugins.kotlinx.binary.validator) apply false
    alias(libs.plugins.version.catalog.update)
    alias(libs.plugins.central.publisher)
    alias(libs.plugins.spotless)
    alias(libs.plugins.dependency.analysis)
    alias(libs.plugins.central.publisher)
}

dependencies {
    kover(projects.openaiClient.openaiClientCore)
    kover(projects.anthropicClient.anthropicClientCore)
    kover(projects.openaiGateway.openaiGatewayCore)
    kover(projects.ollamaClient.ollamaClientCore)
    kover(projects.geminiClient.geminiClientCore)
    kover(projects.common)
}

versionCatalogUpdate { versionSelector(VersionSelectors.STABLE) }

val autoVersion =
    project.property(
        if (project.hasProperty("AUTO_VERSION")) {
            "AUTO_VERSION"
        } else {
            "LIBRARY_VERSION"
        }
    ) as String

subprojects {
    /** Working with runtime properties https://docs.gradle.org/current/userguide/kotlin_dsl.html */
    val GROUP: String by project
    group = GROUP
    version = autoVersion
    apply(plugin = rootProject.libs.plugins.kotlinMultiplatform.get().pluginId)
    apply(plugin = rootProject.libs.plugins.build.dokka.plugin.get().pluginId)
}

// Spotless code formatting
spotless {
    kotlin {
        target("**/*.kt")
        ktfmt().kotlinlangStyle()
    }
    kotlinGradle {
        target("**/*.gradle.kts")
        ktfmt().kotlinlangStyle()
    }
}

kover {
    reports {
        filters {
            excludes {
                classes(
                    "com.tddworks.**.*\$*$*", // Lambda functions like -
                    // LemonSqueezyLicenseApi$activeLicense$activationResult$1
                    "com.tddworks.**.*\$Companion", // Lambda functions like -
                    // LemonSqueezyLicenseApi$activeLicense$activationResult$1
                    "*.*\$\$serializer", // Kotlinx serializer)
                )
                //            inheritedFrom("org.koin.core.component.KoinComponent")
                //            annotatedBy("kotlinx.serialization.Serializable")
            }
            includes { classes("com.tddworks.*") }
        }

        verify { rule { bound { minValue = 86 } } }
    }
}


centralPublisher {
    credentials {
        username = project.findProperty("SONATYPE_USERNAME")?.toString() ?: ""
        password = project.findProperty("SONATYPE_PASSWORD")?.toString() ?: ""
    }
    
    projectInfo {
        name = "openai-kotlin"
        description = "OpenAI API KMP Client"
        url = "https://github.com/tddworks/openai-kotlin"
        
        license {
            name = "The Apache Software License, Version 2.0"
            url = "https://github.com/tddworks/openai-kotlin/blob/main/LICENSE"
        }
        
        developer {
            id = "tddworks"
            name = "itshan"
            email = "itshan@tddworks.com"
        }
        
        scm {
            url = "https://github.com/tddworks/openai-kotlin"
            connection = "scm:git:git://github.com/tddworks/openai-kotlin.git"
            developerConnection = "scm:git:ssh://github.com/tddworks/openai-kotlin.git"
        }
    }
}
