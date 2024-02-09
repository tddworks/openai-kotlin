package com.tddworks.openai.api.internal.network.ktor

import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.utils.io.*
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.serialization.json.Json

private const val STREAM_PREFIX = "data:"
private const val STREAM_END_TOKEN = "$STREAM_PREFIX [DONE]"

/**
 * Get data as [Server-Sent Events](https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events/Using_server-sent_events#Event_stream_format).
 */
internal suspend inline fun <reified T> FlowCollector<T>.streamEventsFrom(response: HttpResponse) {
    val channel: ByteReadChannel = response.body()
    while (!channel.isClosedForRead) {
        channel.readUTF8Line()?.let { streamResponse ->
            if (notEndStreamResponse(streamResponse)) {
                emit(JsonLenient.decodeFromString(streamResponse.removePrefix(STREAM_PREFIX)))
            }
        } ?: break
    }
}

private fun isStreamResponse(line: String) = line.startsWith(STREAM_PREFIX)

private fun notEndStreamResponse(line: String) = line != STREAM_END_TOKEN && isStreamResponse(line)

/**
 * Represents a JSON object that allows for leniency and ignores unknown keys.
 *
 * @property isLenient Removes JSON specification restriction (RFC-4627) and makes parser more liberal to the malformed input. In lenient mode quoted boolean literals, and unquoted string literals are allowed.
 * Its relaxations can be expanded in the future, so that lenient parser becomes even more permissive to invalid value in the input, replacing them with defaults.
 * false by default.
 * @property ignoreUnknownKeys Specifies whether encounters of unknown properties in the input JSON should be ignored instead of throwing SerializationException. false by default..
 */
internal val JsonLenient = Json {
    isLenient = true
    ignoreUnknownKeys = true
}
