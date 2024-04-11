plugins {
    alias(libs.plugins.kover)
    `maven-publish`
}

kotlin {
    jvm()
    sourceSets {
        commonMain {
            dependencies {
                api(projects.anthropicClient.anthropicClientCore)
            }
        }
    }
}