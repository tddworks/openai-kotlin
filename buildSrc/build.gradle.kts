plugins {
    `kotlin-dsl`
}

repositories {
    google()
    maven("https://plugins.gradle.org/m2")
    mavenLocal()
    mavenCentral()
}

sourceSets.main {
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.gradle.kotlinter)
}

kotlin {
    jvmToolchain {
        check(this is JavaToolchainSpec)
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}