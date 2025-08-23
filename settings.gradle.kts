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

plugins { id("de.fayard.refreshVersions") version "0.60.6" }

fun String.isNonStable(): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(this)
    return isStable.not()
}

refreshVersions { rejectVersionIf { candidate.value.isNonStable() } }

include(":common")

// include(":library")
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

include(":gemini-client")

include(":gemini-client:gemini-client-core")

include(":gemini-client:gemini-client-darwin")
