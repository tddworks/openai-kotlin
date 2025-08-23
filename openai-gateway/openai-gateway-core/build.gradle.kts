plugins {
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kover)
    `maven-publish`
}

kotlin {
    jvm()
    macosArm64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(projects.geminiClient.geminiClientCore)
            api(projects.openaiClient.openaiClientCore)
            api(projects.anthropicClient.anthropicClientCore)
            api(projects.ollamaClient.ollamaClientCore)
        }

        commonTest.dependencies { implementation(libs.ktor.client.mock) }

        macosMain.dependencies { api(libs.ktor.client.darwin) }

        jvmMain.dependencies { api(libs.ktor.client.cio) }

        jvmTest.dependencies {
            implementation(project.dependencies.platform(libs.junit.bom))
            implementation(libs.bundles.jvm.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.koin.test)
            implementation(libs.koin.test.junit5)
            implementation(libs.app.cash.turbine)
            implementation("com.tngtech.archunit:archunit-junit5:1.4.1")
            implementation("org.reflections:reflections:0.10.2")
            implementation("org.junit.platform:junit-platform-launcher")
        }
    }
}

tasks { named<Test>("jvmTest") { useJUnitPlatform() } }
