package com.tddworks.gemini.api.textGeneration.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * { "contents": [{ "parts": [{ "text": "Write a story about a magic backpack." }] }],
 * "safetySettings":
 * [{ "category": "HARM_CATEGORY_DANGEROUS_CONTENT", "threshold": "BLOCK_ONLY_HIGH" }],
 * "generationConfig": { "stopSequences": [ "Title" ], "temperature": 1.0, "response_mime_type":
 * "application/json", "maxOutputTokens": 800, "topP": 0.8, "topK": 10 } }
 */
@Serializable
data class GenerationConfig(
    val temperature: Float? = null,
    @SerialName("response_mime_type") val responseMimeType: String? = null,
)
