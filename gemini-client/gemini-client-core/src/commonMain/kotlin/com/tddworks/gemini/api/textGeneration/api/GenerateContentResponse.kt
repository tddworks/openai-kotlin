package com.tddworks.gemini.api.textGeneration.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal typealias Base64 = String

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

@Serializable(with = PartSerializer::class)
sealed interface Part {

    @Serializable
    data class TextPart(
        val text: String
    ) : Part

    /**
     * https://ai.google.dev/gemini-api/docs/text-generation?lang=rest#generate-text-from-text-and-image
     * @param mimeType The MIME type of the data. Supported MIME types are the following:
     * - image/jpeg
     * - image/png
     * @param data The base64-encoded data.
     */
    @Serializable
    data class InlineDataPart(
        @SerialName("inline_data") val inlineData: InlineData
    ) : Part {
        @Serializable
        data class InlineData(
            @SerialName("mime_type") val mimeType: String,
            val data: Base64
        )
    }
}


@Serializable
data class UsageMetadata(
    val promptTokenCount: Int,
    val candidatesTokenCount: Int? = null,
    val totalTokenCount: Int
)
