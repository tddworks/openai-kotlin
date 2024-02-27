plugins {
//    id("root.publication")
    `maven-publish`
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.kover)
    alias(libs.plugins.com.linecorp.build.recipe)
    alias(libs.plugins.build.dokka.plugin)

    alias(libs.plugins.kotlinx.binary.validator) apply false
    id("com.tddworks.sonatype-portal-publisher") version "0.0.1"
}

sonatypePortalPublisher {
    authentication {
        username = "Uh+r/1by"
        password = "vcjKVgS1D3xtqhpLNRu1smPLqr73R4Aw2nRJAqlLISvA"
    }
    settings {
        autoPublish = false
        aggregation = true
    }
}


dependencies {
    kover(projects.openaiClient.openaiClientCore)
    kover(projects.openaiClient.openaiClientCio)
    kover(projects.openaiClient.openaiClientDarwin)
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

//publishing {
//    // Configure all publications
//    publications.withType<MavenPublication> {
//        // Stub javadoc.jar artifact
//        artifact(tasks.register("${name}JavadocJar", Jar::class) {
//            archiveClassifier.set("javadoc")
//            archiveAppendix.set(this@withType.name)
//        })
//
//        // Provide artifacts information required by Maven Central
//        pom {
//            name.set("Kotlin Multiplatform library template")
//            description.set("Dummy library to test deployment to Maven Central")
//            url.set("https://github.com/Kotlin/multiplatform-library-template")
//
//            licenses {
//                license {
//                    name.set("MIT")
//                    url.set("https://opensource.org/licenses/MIT")
//                }
//            }
//            developers {
//                developer {
//                    id.set("JetBrains")
//                    name.set("JetBrains Team")
//                    organization.set("JetBrains")
//                    organizationUrl.set("https://www.jetbrains.com")
//                }
//            }
//            scm {
//                url.set("https://github.com/Kotlin/multiplatform-library-template")
//            }
//        }
//    }
//}

koverReport {
    filters {
        excludes {
            classes(
                "**Platform*",
                "com.tddworks.**.request.*",
                "com.tddworks.**.response.*",
                "com.tddworks.**.data.*",
                "com.tddworks.**.internal.ktor.internal.*",
                "com.tddworks.**.**.ktor.internal.*",
                "com.tddworks.**.*\$*$*", // Lambda functions like - LemonSqueezyLicenseApi$activeLicense$activationResult$1
                "*.BuildConfig",
                "*.BuildKonfig", // BuildKonfig generated
                "*.ComposableSingletons*", // Jetpack Compose generated
                "*.*\$*Preview\$*", // Jetpack Compose Preview functions
                "*.di.*", // Koin
                "*.ui.preview.*", // Jetpack Compose Preview providers
                "*.*Test", // Test files
                "*.*Test*", // Test cases
                "*.*Mock", // mockative @Mock generated
                "*.test.*", // Test util package
                "*.*\$\$serializer", // Kotlinx serializer)
                "**.*\$Lambda$*.*", // Lambda functions
                "**.*\$inlined$*", // Inlined functions
                "**.*2\$1" // transactionWithResult
            )
        }
        includes {
            classes("com.tddworks.*")
        }
    }

    verify {
        rule {
            bound {
                minValue = 100
            }
        }
    }
}