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
            // put your Multiplatform dependencies here
            api(projects.common)
        }

        commonTest.dependencies {
            implementation(libs.ktor.client.mock)
            api(projects.common)
        }

        macosMain.dependencies {
            api(libs.ktor.client.darwin)
        }

        jvmMain.dependencies {
            api(libs.ktor.client.cio)
        }

        jvmTest.dependencies {
            implementation(project.dependencies.platform(libs.junit.bom))
            implementation(libs.bundles.jvm.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.koin.test)
            implementation(libs.koin.test.junit5)
            implementation(libs.app.cash.turbine)
            implementation("com.tngtech.archunit:archunit-junit5:1.1.0")
            implementation("org.reflections:reflections:0.10.2")
            implementation(libs.org.skyscreamer.jsonassert)
        }
    }
}

tasks {
    named<Test>("jvmTest") {
        useJUnitPlatform()
    }
}