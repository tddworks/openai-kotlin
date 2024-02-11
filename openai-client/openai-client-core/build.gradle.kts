plugins {
//    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kover)
    alias(libs.plugins.touchlab.kmmbridge)
    alias(libs.plugins.touchlab.skie)
    id("module.publication")
}

kotlin {
    jvm()
    macosArm64()
    macosX64()

    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            api(libs.kotlinx.serialization.json)
            api(libs.bundles.ktor.client)
        }

        commonTest.dependencies {
            implementation(libs.ktor.client.mock)
        }

        jvmMain.dependencies {
            api(libs.ktor.client.cio)
        }

        jvmTest.dependencies {
            implementation(project.dependencies.platform(libs.junit.bom))
            implementation(libs.bundles.jvm.test)
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