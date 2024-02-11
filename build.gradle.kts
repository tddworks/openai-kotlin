plugins {
    id("root.publication")
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidLibrary).apply(false)
//    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.kover)
    alias(libs.plugins.com.linecorp.build.recipe)
}


dependencies {
    kover(projects.openaiClient.openaiClientCore)
    kover(projects.openaiClient.openaiClientCio)
    kover(projects.openaiClient.openaiClientDarwin)
}

subprojects {
    group = "com.tddworks"
    apply(plugin = rootProject.libs.plugins.kotlinMultiplatform.get().pluginId)
}

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