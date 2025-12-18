plugins {
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.touchlab.kmmbridge)
    alias(libs.plugins.touchlab.skie)
    `maven-publish`
}

kotlin {
    listOf(macosArm64(), iosArm64(), iosSimulatorArm64()).forEach { macosTarget ->
        macosTarget.binaries.framework {
            baseName = "OpenAIGateway"
            export(projects.openaiGateway.openaiGatewayCore)
            export(projects.openaiClient.openaiClientDarwin)
            export(projects.ollamaClient.ollamaClientDarwin)
            export(projects.anthropicClient.anthropicClientDarwin)
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(projects.openaiGateway.openaiGatewayCore)
                api(projects.openaiClient.openaiClientDarwin)
                api(projects.ollamaClient.ollamaClientDarwin)
                api(projects.anthropicClient.anthropicClientDarwin)
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
