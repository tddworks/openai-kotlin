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
 * ======== final response ========
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
 *
 * ======= Non-streaming response =======
 *  {
 *    "model": "llama2",
 *    "created_at": "2023-12-12T14:13:43.416799Z",
 *    "message": {
 *      "role": "assistant",
 *      "content": "Hello! How are you today?"
 *    },
 *    "done": true,
 *    "total_duration": 5191566416,
 *    "load_duration": 2154458,
 *    "prompt_eval_count": 26,
 *    "prompt_eval_duration": 383809000,
 *    "eval_count": 298,
 *    "eval_duration": 4799921000
 *  }
 */
@Serializable
data class OllamaChatResponse(
    @SerialName("model") val model: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("message") val message: OllamaChatMessage? = null,
    @SerialName("done") val done: Boolean,
    // Below are the fields that are for final response or non-streaming response
    @SerialName("total_duration") val totalDuration: Long? = null,
    @SerialName("load_duration") val loadDuration: Long? = null,
    @SerialName("prompt_eval_count") val promptEvalCount: Int? = null,
    @SerialName("prompt_eval_duration") val promptEvalDuration: Long? = null,
    @SerialName("eval_count") val evalCount: Int? = null,
    @SerialName("eval_duration") val evalDuration: Long? = null,
)