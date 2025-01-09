plugins {
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.touchlab.kmmbridge)
    alias(libs.plugins.touchlab.skie)
    `maven-publish`
}

kotlin {
    listOf(
        macosArm64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { macosTarget ->
        macosTarget.binaries.framework {
            export(projects.geminiClient.geminiClientCore)
            baseName = "geminiClientDarwin"
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(projects.geminiClient.geminiClientCore)
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
    mavenPublishArtifacts() // <- Publish using a Maven repo
    /**
     * https://github.com/touchlab/KMMBridge/issues/258
     */
    spm(
        swiftToolVersion = "5.9",
//        useCustomPackageFile = true,
//        perModuleVariablesBlock = true
    ) {
        iOS { v("15") }
//        macOS { v("15") }
    }
}