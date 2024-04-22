plugins {
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.touchlab.kmmbridge)
    alias(libs.plugins.touchlab.skie)
    `maven-publish`
}


kmmbridge {
    /**
     * reference: https://kmmbridge.touchlab.co/docs/artifacts/MAVEN_REPO_ARTIFACTS#github-packages
     * In kmmbridge, notice mavenPublishArtifacts() tells the plugin to push KMMBridge artifacts to a Maven repo. You then need to define a repo. Rather than do everything manually, you can just call addGithubPackagesRepository(), which will add the correct repo given parameters that are passed in from GitHub Actions.
     */
    mavenPublishArtifacts() // <- Publish using a Maven repo
//    spm {
//        swiftToolsVersion = "5.9"
//        platforms {
//            iOS("14")
//            macOS("13")
//            watchOS("7")
//            tvOS("14")
//        }
//    }
}

kotlin {
    listOf(
        macosArm64(),
        macosX64()
    ).forEach { macosTarget ->
        macosTarget.binaries.framework {
            baseName = "anthropic-client-darwin"
            export(projects.anthropicClient.anthropicClientCore)
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(projects.anthropicClient.anthropicClientCore)
                implementation(libs.ktor.client.darwin)
            }
        }
        appleMain {}
    }
}

addGithubPackagesRepository() // <- Add the GitHub Packages repo

kmmbridge {
    /**
     * reference: https://kmmbridge.touchlab.co/docs/artifacts/MAVEN_REPO_ARTIFACTS#github-packages
     * In kmmbridge, notice mavenPublishArtifacts() tells the plugin to push KMMBridge artifacts to a Maven repo. You then need to define a repo. Rather than do everything manually, you can just call addGithubPackagesRepository(), which will add the correct repo given parameters that are passed in from GitHub Actions.
     */
//    mavenPublishArtifacts() // <- Publish using a Maven repo
//    spm(swiftToolVersion = "5.9")
//    spm {
//        swiftToolsVersion = "5.9"
//        platforms {
//            iOS("14")
//            macOS("13")
//            watchOS("7")
//            tvOS("14")
//        }
//    }
}
