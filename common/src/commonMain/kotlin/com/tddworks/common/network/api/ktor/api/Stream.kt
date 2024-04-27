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
    val json = json()
    while (!channel.isClosedForRead) {
        val line = channel.readUTF8Line() ?: continue
        val value: T = when {
            endStreamResponse(line) -> break
            isStreamResponse(line) -> json.decodeFromString(line.removePrefix(STREAM_PREFIX))  // If the response indicates streaming data, decode and emit it.
            isJsonResponse(line) -> json.decodeFromString(line) // Ollama - response is a json object without `data:` prefix
            else -> continue
        }
        emit(value)
    }
}


fun json(): Json {
    return getInstance()
}


fun isStreamResponse(line: String) = line.startsWith(STREAM_PREFIX)

fun endStreamResponse(line: String) = line.startsWith(STREAM_END_TOKEN)

fun isJsonResponse(line: String) = line.startsWith("{") && line.endsWith("}")