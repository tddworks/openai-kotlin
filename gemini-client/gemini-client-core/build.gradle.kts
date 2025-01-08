import com.google.devtools.ksp.gradle.KspTaskMetadata
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kover)
    alias(libs.plugins.ksp)
    `maven-publish`
}

kotlin {
    jvm()
    macosArm64()
    macosX64()

    sourceSets {
        commonMain {
            // https://github.com/google/ksp/issues/963#issuecomment-1894144639
            tasks.withType<KspTaskMetadata> { kotlin.srcDir(destinationDirectory) }

            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            dependencies {
                // put your Multiplatform dependencies here
                api(projects.common)
            }
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

// KSP Tasks
dependencies {
    add("kspCommonMainMetadata", libs.koin.ksp.compiler)
}

// WORKAROUND: ADD this dependsOn("kspCommonMainKotlinMetadata") instead of above dependencies
tasks.withType<KotlinCompile>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}


ksp {
    arg("KOIN_DEFAULT_MODULE", "false")
    // https://insert-koin.io/docs/reference/koin-annotations/start#compile-safety---check-your-koin-config-at-compile-time-since-130
    arg("KOIN_CONFIG_CHECK", "false")
}

tasks {
    named<Test>("jvmTest") {
        useJUnitPlatform()
    }
}