plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kover)
    alias(libs.plugins.touchlab.kmmbridge)
    id("module.publication")
}

kotlin {
    macosArm64()
    macosX64()

    listOf(
        macosArm64(),
        macosX64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "openai"
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(projects.openaiClient.openaiClientCore)
                api(libs.ktor.client.darwin)
            }
        }
    }
}