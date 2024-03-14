package com.tddworks.common.network.api.ktor.api

import com.tddworks.common.network.api.ktor.internal.JsonLenient
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.utils.io.*
import kotlinx.coroutines.flow.FlowCollector

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
                emit(JsonLenient.decodeFromString(streamResponse.removePrefix(STREAM_PREFIX)))
            }
        } ?: break
    }
}

private fun isStreamResponse(line: String) = line.startsWith(STREAM_PREFIX)

fun notEndStreamResponse(line: String) = line != STREAM_END_TOKEN && isStreamResponse(line)