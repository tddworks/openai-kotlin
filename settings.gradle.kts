pluginManagement {
//    includeBuild("convention-plugins")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "openai-kotlin"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

//include(":library")
//include(":openai-client")
include(":openai-client:openai-client-core")
include(":openai-client:openai-client-darwin")
//include(":openai-client:openai-client-ios")
include(":openai-client:openai-client-cio")
