package com.tddworks.ollama.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * {
 *   "model": "llama2",
 *   "created_at": "2023-08-04T08:52:19.385406455-07:00",
 *   "message": {
 *     "role": "assistant",
 *     "content": "The"
 *   },
 *   "done": false
 * }
 */
@Serializable
data class OllamaChatResponse(
    @SerialName("model") val model: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("message") val message: OllamaChatMessage? = null,
    @SerialName("done") val done: Boolean?,
    @SerialName("total_duration") val totalDuration: Long? = null,
    @SerialName("load_duration") val loadDuration: Long? = null,
    @SerialName("prompt_eval_count") val promptEvalCount: Int? = null,
    @SerialName("prompt_eval_duration") val promptEvalDuration: Long? = null,
    @SerialName("eval_count") val evalCount: Int? = null,
    @SerialName("eval_duration") val evalDuration: Long? = null,
)

/**
 * {
 *   "model": "llama2",
 *   "created_at": "2023-08-04T19:22:45.499127Z",
 *   "done": true,
 *   "total_duration": 8113331500,
 *   "load_duration": 6396458,
 *   "prompt_eval_count": 61,
 *   "prompt_eval_duration": 398801000,
 *   "eval_count": 468,
 *   "eval_duration": 7701267000
 * }
 */
@Serializable
data class FinalOllamaChatResponse(
    @SerialName("model") val model: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("done") val done: Boolean?,
    @SerialName("total_duration") val totalDuration: Long?,
    @SerialName("load_duration") val loadDuration: Long?,
    @SerialName("prompt_eval_count") val promptEvalCount: Int?,
    @SerialName("prompt_eval_duration") val promptEvalDuration: Long?,
    @SerialName("eval_count") val evalCount: Int?,
    @SerialName("eval_duration") val evalDuration: Long?,
)