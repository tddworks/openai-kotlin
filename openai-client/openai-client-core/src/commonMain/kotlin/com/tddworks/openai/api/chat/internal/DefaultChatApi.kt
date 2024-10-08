package com.tddworks.openai.api.chat.internal

import com.tddworks.openai.api.chat.api.ChatCompletionChunk
import com.tddworks.openai.api.chat.api.Chat
import com.tddworks.openai.api.chat.api.Chat.Companion.CHAT_COMPLETIONS_PATH
import com.tddworks.openai.api.chat.api.ChatCompletion
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.api.performRequest
import com.tddworks.common.network.api.ktor.api.streamRequest
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

/**
 * Default implementation of [Chat].
 * @see [Chat documentation](https://beta.openai.com/docs/api-reference/chat)
 * @property requester The HttpRequester to use for performing HTTP requests.
 */
@OptIn(ExperimentalSerializationApi::class)
internal class DefaultChatApi(
    private val requester: HttpRequester,
    private val chatCompletionPath: String = CHAT_COMPLETIONS_PATH,
    private val extraHeaders: Map<String, String> = mapOf()
) : Chat {
    override suspend fun chatCompletions(request: ChatCompletionRequest): ChatCompletion {
        return requester.performRequest<ChatCompletion> {
            method = HttpMethod.Post
            url(path = chatCompletionPath)
            setBody(request)
            contentType(ContentType.Application.Json)
            headers {
                extraHeaders.forEach { (key, value) -> append(key, value) }
            }
        }
    }

    override fun streamChatCompletions(request: ChatCompletionRequest): Flow<ChatCompletionChunk> {
        return requester.streamRequest<ChatCompletionChunk> {
            method = HttpMethod.Post
            url(path = chatCompletionPath)
            setBody(request.copy(stream = true))
            contentType(ContentType.Application.Json)
            accept(ContentType.Text.EventStream)
            headers {
                append(HttpHeaders.CacheControl, "no-cache")
                append(HttpHeaders.Connection, "keep-alive")
                extraHeaders.forEach { (key, value) -> append(key, value) }
            }
        }
    }
}

fun Chat.Companion.default(
    requester: HttpRequester,
    chatCompletionPath: String = CHAT_COMPLETIONS_PATH,
    extraHeaders: Map<String, String> = mapOf()
): Chat = DefaultChatApi(requester, chatCompletionPath, extraHeaders)