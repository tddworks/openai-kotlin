package com.tddworks.common.network.api.ktor.api

import com.tddworks.di.getInstance
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.utils.io.*
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.serialization.json.Json

const val STREAM_PREFIX = "data:"
private const val STREAM_END_TOKEN = "$STREAM_PREFIX [DONE]"

/**
 * Get data as [Server-Sent Events](https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events/Using_server-sent_events#Event_stream_format).
 */
suspend inline fun <reified T> FlowCollector<T>.streamEventsFrom(response: HttpResponse) {
    val channel: ByteReadChannel = response.body()

    // Continue to read until the channel is closed.
    while (!channel.isClosedForRead) {
        channel.readUTF8Line()?.let { streamResponse ->
            if (notEndStreamResponse(streamResponse)) {
                // If the response indicates streaming data, decode and emit it.
                emit(json().decodeFromString<T>(streamResponse.removePrefix(STREAM_PREFIX)))
            } else if (isJson(streamResponse)) {
                // Ollama - response is a json object
                emit(json().decodeFromString<T>(streamResponse))
            }
        } ?: break // If `readUTF8Line()` returns null, exit the loop (end of input).
    }
}


fun json(): Json {
    return getInstance()
}


private fun isStreamResponse(line: String) = line.startsWith(STREAM_PREFIX)

fun notEndStreamResponse(line: String) = line != STREAM_END_TOKEN && isStreamResponse(line)

fun isJson(line: String) = line.startsWith("{") && line.endsWith("}")