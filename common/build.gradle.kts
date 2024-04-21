plugins {
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kover)
    `maven-publish`
}

kotlin {
    jvm()
    macosArm64()
    macosX64()

    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            implementation(libs.kotlinx.coroutines.core)
            api(libs.kotlinx.serialization.json)
            api(libs.bundles.ktor.client)
            // di
            api(libs.koin.core)
        }

        commonTest.dependencies {
            implementation(libs.ktor.client.mock)
        }

        appleMain.dependencies {
            api(libs.ktor.client.darwin)
        }

        jvmMain.dependencies {
            api(libs.ktor.client.okhttp)
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
        }
    }
}

tasks {
    named<Test>("jvmTest") {
        useJUnitPlatform()
    }
}