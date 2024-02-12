plugins {
//    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kover)
    alias(libs.plugins.touchlab.kmmbridge)
    alias(libs.plugins.touchlab.skie)
//    id("module.publication")
}

kotlin {
    listOf(
        macosArm64(),
        macosX64()
    ).forEach { macosTarget ->
        macosTarget.binaries.framework {
            baseName = "openai-client-darwin"
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.openaiClient.openaiClientCore)
                implementation(libs.ktor.client.darwin)
            }
        }
    }
}

addGithubPackagesRepository() // <- Add the GitHub Packages repo

kmmbridge {
    /**
     * reference: https://kmmbridge.touchlab.co/docs/artifacts/MAVEN_REPO_ARTIFACTS#github-packages
     * In kmmbridge, notice mavenPublishArtifacts() tells the plugin to push KMMBridge artifacts to a Maven repo. You then need to define a repo. Rather than do everything manually, you can just call addGithubPackagesRepository(), which will add the correct repo given parameters that are passed in from GitHub Actions.
     */
    mavenPublishArtifacts() // <- Publish using a Maven repo
    spm(project.projectDir.path)
//    spm {
//        swiftToolsVersion = "5.9"
//        platforms {
//            iOS { v("14") }
//            macOS { v("13") }
//            watchOS { v("7") }
//            tvOS { v("14") }
//        }
//    }
}
