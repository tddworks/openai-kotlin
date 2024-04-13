plugins {
    alias(libs.plugins.kover)
    `maven-publish`
}

kotlin {
    jvm()
    sourceSets {
        commonMain {
            dependencies {
                api(projects.openaiClient.openaiClientCore)
            }
        }
    }
}

tasks {
    koverHtmlReport {
        dependsOn(":anthropic-client:jvmTest")
    }
    koverVerify {
        dependsOn(":anthropic-client:jvmTest")
    }
}