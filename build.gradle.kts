import nl.littlerobots.vcu.plugin.resolver.VersionSelectors

plugins {
    `maven-publish`
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.kover)
    alias(libs.plugins.com.linecorp.build.recipe)
    alias(libs.plugins.build.dokka.plugin)

    alias(libs.plugins.kotlinx.binary.validator) apply false
    id("nl.littlerobots.version-catalog-update") version "1.0.0"
    id("com.tddworks.central-portal-publisher") version "0.0.5"
}

sonatypePortalPublisher {
    settings {
        autoPublish = false
        aggregation = true
    }
}


dependencies {
    kover(projects.openaiClient.openaiClientCore)
    kover(projects.anthropicClient.anthropicClientCore)
    kover(projects.openaiGateway.openaiGatewayCore)
    kover(projects.ollamaClient.ollamaClientCore)
    kover(projects.geminiClient.geminiClientCore)
    kover(projects.common)
}

versionCatalogUpdate {
    versionSelector(VersionSelectors.STABLE)
}

val autoVersion = project.property(
    if (project.hasProperty("AUTO_VERSION")) {
        "AUTO_VERSION"
    } else {
        "LIBRARY_VERSION"
    }
) as String

subprojects {
    /**
     * Working with runtime properties
     * https://docs.gradle.org/current/userguide/kotlin_dsl.html
     */
    val GROUP: String by project
    group = GROUP
    version = autoVersion
    apply(plugin = rootProject.libs.plugins.kotlinMultiplatform.get().pluginId)
    apply(plugin = rootProject.libs.plugins.build.dokka.plugin.get().pluginId)
}

kover {
    reports {
        filters {
            excludes {
                classes(
                    "com.tddworks.**.*\$*$*", // Lambda functions like - LemonSqueezyLicenseApi$activeLicense$activationResult$1
                    "com.tddworks.**.*\$Companion", // Lambda functions like - LemonSqueezyLicenseApi$activeLicense$activationResult$1
                    "*.*\$\$serializer", // Kotlinx serializer)
                )
//            inheritedFrom("org.koin.core.component.KoinComponent")
//            annotatedBy("kotlinx.serialization.Serializable")
            }
            includes {
                classes("com.tddworks.*")
            }
        }

        verify {
            rule {
                bound {
                    minValue = 86
                }
            }
        }
    }
}