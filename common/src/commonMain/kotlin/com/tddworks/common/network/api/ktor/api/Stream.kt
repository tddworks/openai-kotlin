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
    while (!channel.isClosedForRead) {
        channel.readUTF8Line()?.let { streamResponse ->
            if (notEndStreamResponse(streamResponse)) {
                emit(json().decodeFromString(streamResponse.removePrefix(STREAM_PREFIX)))
            } else {
                // for like ollama api it's returning json string without prefix "data:"
                emit(json().decodeFromString(streamResponse))
            }
        } ?: break
    }
}

fun json(): Json {
    return getInstance()
}


private fun isStreamResponse(line: String) = line.startsWith(STREAM_PREFIX)

fun notEndStreamResponse(line: String) = line != STREAM_END_TOKEN && isStreamResponse(line)