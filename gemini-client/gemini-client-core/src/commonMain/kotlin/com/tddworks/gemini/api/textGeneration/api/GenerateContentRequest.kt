package com.tddworks.gemini.api.textGeneration.api

import com.tddworks.gemini.api.textGeneration.api.internal.DefaultTextGenerationApi.Companion.GEMINI_API_PATH
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

// curl https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:streamGenerateContent?alt=sse&key=$GOOGLE_API_KEY \
// curl https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$GOOGLE_API_KEY \

/**
 * {
 *       "contents": [
 *         {"role":"user",
 *          "parts":[{
 *            "text": "Hello"}]},
 *         {"role": "model",
 *          "parts":[{
 *            "text": "Great to meet you. What would you like to know?"}]},
 *         {"role":"user",
 *          "parts":[{
 *            "text": "I have two dogs in my house. How many paws are in my house?"}]},
 *       ]
 *     }
 */
@Serializable
data class GenerateContentRequest(
    @SerialName("system_instruction")
    val systemInstruction: Content? = null,
    val contents: List<Content>,
    val generationConfig: GenerationConfig? = null,
    @Transient val model: GeminiModel = GeminiModel.GEMINI_1_5_FLASH,
    @Transient val stream: Boolean = false
) {
    fun toRequestUrl(): String {
        val endpoint = if (stream) {
            "streamGenerateContent"
        } else {
            "generateContent"
        }
        return "$GEMINI_API_PATH/${model.value}:$endpoint"
    }
}