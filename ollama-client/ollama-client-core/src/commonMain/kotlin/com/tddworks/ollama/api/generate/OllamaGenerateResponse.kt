package com.tddworks.ollama.api.generate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * {
 *   "model": "llama3",
 *   "created_at": "2023-08-04T19:22:45.499127Z",
 *   "response": "The sky is blue because it is the color of the sky.",
 *   "done": true,
 *   "context": [1, 2, 3],
 *   "total_duration": 4935886791,
 *   "load_duration": 534986708,
 *   "prompt_eval_count": 26,
 *   "prompt_eval_duration": 107345000,
 *   "eval_count": 237,
 *   "eval_duration": 4289432000
 * }
 */
@Serializable
data class OllamaGenerateResponse(
    @SerialName("model")
    val model: String,
    @SerialName("created_at")
    val createdAt: String,
    /**
     *  empty if the response was streamed, if not streamed, this will contain the full response
     */
    @SerialName("response")
    val response: String,
    @SerialName("done")
    val done: Boolean,

    /**
     * reason for the conversation ending
     * E.g "length"
     */
    @SerialName("done_reason")
    val doneReason: String? = null,
    /**
     *  an encoding of the conversation used in this response, this can be sent in the next request to keep a conversational mem
     */
    @SerialName("context")
    val context: List<Int>? = null,
    /**
     * time spent generating the response
     */
    @SerialName("total_duration")
    val totalDuration: Long? = null,
    /**
     * time spent in nanoseconds loading the model
     */
    @SerialName("load_duration")
    val loadDuration: Long? = null,
    /**
     * number of tokens in the prompt
     */
    @SerialName("prompt_eval_count")
    val promptEvalCount: Int? = null,
    /**
     * time spent in nanoseconds evaluating the prompt
     */
    @SerialName("prompt_eval_duration")
    val promptEvalDuration: Long? = null,
    /**
     * number of tokens in the response
     */
    @SerialName("eval_count")
    val evalCount: Int? = null,
    /**
     * time in nanoseconds spent generating the response
     */
    @SerialName("eval_duration")
    val evalDuration: Long? = null,
)