package com.tddworks.openai.api.legacy.completions.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Completion(
    val id: String,
    val choices: List<CompletionChoice>,
    val created: Int,
    val model: String,
    @SerialName("system_fingerprint")
    val systemFingerprint: String?,
    val `object`: String?,
    val usage: Usage?
) {
    companion object {
        fun dummy() = Completion(
            id = "id",
            choices = emptyList(),
            created = 0,
            model = "model",
            systemFingerprint = "systemFingerprint",
            `object` = "object",
            usage = Usage()
        )
    }
}

@Serializable
data class CompletionChoice(
    @SerialName("text")
    val text: String,
    @SerialName("index")
    val index: Int,
    @SerialName("logprobs")
    val logprobs: Map<String, String>?,
    @SerialName("finish_reason")
    val finishReason: String
)

@Serializable
data class Usage(
    @SerialName("prompt_tokens")
    val promptTokens: Int? = null,
    @SerialName("completion_tokens")
    val completionTokens: Int? = null,
    @SerialName("total_tokens")
    val totalTokens: Int? = null,
)
