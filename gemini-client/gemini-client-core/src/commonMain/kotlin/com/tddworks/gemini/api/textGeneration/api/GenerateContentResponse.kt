package com.tddworks.gemini.api.textGeneration.api

import kotlinx.serialization.Serializable

@Serializable
data class GenerateContentResponse(
    val candidates: List<Candidate>,
    val usageMetadata: UsageMetadata,
    val modelVersion: String
) {
    companion object {
        fun dummy() = GenerateContentResponse(
            candidates = emptyList(),
            usageMetadata = UsageMetadata(0, 0, 0),
            modelVersion = ""
        )
    }
}

@Serializable
data class Candidate(
    val content: Content,
    val finishReason: String? = null,
    val avgLogprobs: Double? = null
)

@Serializable
data class Content(
    val parts: List<Part>,
    val role: String? = null
)

@Serializable
data class Part(
    val text: String
)

@Serializable
data class UsageMetadata(
    val promptTokenCount: Int,
    val candidatesTokenCount: Int? = null,
    val totalTokenCount: Int
)
