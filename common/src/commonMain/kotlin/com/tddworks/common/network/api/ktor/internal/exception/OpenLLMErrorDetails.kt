package com.tddworks.common.network.api.ktor.internal.exception

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an error response from the OpenAI API.
 *
 * @param detail information about the error that occurred.
 */
@Serializable
data class OpenAIError(
    @SerialName("error") val detail: OpenAIErrorDetails?,
)

/**
 * Represents an error object returned by the OpenAI API.
 * {
 *   "code": null,
 *   "type": "server_error",
 *   "param": null,
 *   "message": "That model is currently overloaded with other requests. You can retry your request, or contact us through our help center at help.openai.com if the error persists. (Please include the request ID c58c33110e4907638de58bec34af86e5 in your message.)"
 * }
 * @param code error code returned by the OpenAI API.
 * @param message human-readable error message describing the error that occurred.
 * @param param the parameter that caused the error, if applicable.
 * @param type the type of error that occurred.
 */
@Serializable
data class OpenAIErrorDetails(
    @SerialName("code") val code: String?,
    @SerialName("message") val message: String?,
    @SerialName("param") val param: String?,
    @SerialName("type") val type: String?,
)
