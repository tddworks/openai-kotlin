plugins {
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.touchlab.kmmbridge)
    alias(libs.plugins.touchlab.skie)
    `maven-publish`
}

kotlin {
    listOf(macosArm64(), iosArm64(), iosSimulatorArm64()).forEach { macosTarget ->
        macosTarget.binaries.framework {
            baseName = "OllamaClient"
            export(projects.ollamaClient.ollamaClientCore)
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(projects.ollamaClient.ollamaClientCore)
                implementation(libs.ktor.client.darwin)
            }
        }
        appleMain {}
    }
}

kmmbridge {
    gitHubReleaseArtifacts()
    spm(swiftToolVersion = "5.9", useCustomPackageFile = true, perModuleVariablesBlock = true) {
        iOS { v("15") }
        macOS { v("15") }
    }
}
