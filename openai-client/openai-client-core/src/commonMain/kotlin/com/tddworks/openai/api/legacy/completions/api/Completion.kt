package com.tddworks.openai.api.legacy.completions.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** https://platform.openai.com/docs/api-reference/completions/object */
@Serializable
data class Completion(
    /** A unique identifier for the completion. */
    val id: String,
    /** The list of completion choices the model generated for the input prompt. */
    val choices: List<CompletionChoice>,
    /** The Unix timestamp (in seconds) of when the completion was created. */
    val created: Int,
    /** The model used for completion. */
    val model: String,
    /**
     * This fingerprint represents the backend configuration that the model runs with.
     *
     * Can be used in conjunction with the seed request parameter to understand when backend changes
     * have been made that might impact determinism.
     */
    @SerialName("system_fingerprint") val systemFingerprint: String? = null,
    /** The object type, which is always "text_completion" */
    val `object`: String? = null,
    /** Usage statistics for the completion request. */
    val usage: Usage? = null,
) {
    companion object {
        fun dummy() =
            Completion(
                id = "id",
                choices = emptyList(),
                created = 0,
                model = "model",
                systemFingerprint = "systemFingerprint",
                `object` = "object",
                usage = Usage(),
            )
    }
}

@Serializable
data class CompletionChoice(
    @SerialName("text") val text: String,
    @SerialName("index") val index: Int,
    @SerialName("logprobs") val logprobs: Map<String, String>? = null,
    @SerialName("finish_reason") val finishReason: String = "",
)

@Serializable
data class Usage(
    @SerialName("prompt_tokens") val promptTokens: Int? = null,
    @SerialName("completion_tokens") val completionTokens: Int? = null,
    @SerialName("total_tokens") val totalTokens: Int? = null,
)
