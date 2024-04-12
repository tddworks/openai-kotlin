plugins {
    alias(libs.plugins.kover)
    `maven-publish`
}

kotlin {
    jvm()
}

tasks {
    koverHtmlReport {
        dependsOn(":anthropic-client:jvmTest")
    }
}