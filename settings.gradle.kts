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
include(":openai-client:openai-client-cio")

include(":anthropic-client")
include(":anthropic-client:anthropic-client-core")
include(":anthropic-client:anthropic-client-darwin")

include(":openai-gateway")
include(":openai-gateway:openai-gateway-core")
include(":openai-gateway:openai-gateway-darwin")

include(":ollama-client")
include(":ollama-client:ollama-client-core")
include(":ollama-client:ollama-client-darwin")

//include(":gemini-client")
