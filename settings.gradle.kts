pluginManagement {
    repositories {
        google()
        mavenLocal()
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

include(":common")

//include(":library")
include(":openai-client")
include(":openai-client:openai-client-core")
include(":openai-client:openai-client-darwin")
//include(":openai-client:openai-client-ios")
include(":openai-client:openai-client-cio")

include(":anthropic-client")
include(":anthropic-client:anthropic-client-core")

include(":openllm-gateway")
include(":openllm-gateway:openllm-gateway-core")

//include(":gemini-client")
