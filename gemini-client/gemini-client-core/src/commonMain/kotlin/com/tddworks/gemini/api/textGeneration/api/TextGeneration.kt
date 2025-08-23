package com.tddworks.gemini.api.textGeneration.api

import kotlinx.coroutines.flow.Flow

/** https://ai.google.dev/api/generate-content#v1beta.Candidate */
interface TextGeneration {
    suspend fun generateContent(request: GenerateContentRequest): GenerateContentResponse

    /**
     * data: {"candidates":
     * [{"content": {"parts": [{"text": " understand that AI is a constantly evolving field. New techniques and approaches are continually being developed, pushing the boundaries of what's possible. While AI can achieve impressive feats, it's important to remember that it's a tool, and its capabilities are limited by the data it's trained on and the algorithms"}],"role":
     * "model"}}],"usageMetadata": {"promptTokenCount": 4,"totalTokenCount": 4},"modelVersion":
     * "gemini-1.5-flash"}
     *
     * data: {"candidates":
     * [{"content": {"parts": [{"text": " it uses. It doesn't possess consciousness or genuine understanding in the human sense.\n"}],"role":
     * "model"},"finishReason": "STOP"}],"usageMetadata": {"promptTokenCount":
     * 4,"candidatesTokenCount": 724,"totalTokenCount": 728},"modelVersion": "gemini-1.5-flash"}
     */
    fun streamGenerateContent(request: GenerateContentRequest): Flow<GenerateContentResponse>

    companion object
}
