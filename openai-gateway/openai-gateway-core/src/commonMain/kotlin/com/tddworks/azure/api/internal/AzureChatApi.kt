package com.tddworks.azure.api.internal

import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.openai.api.chat.api.Chat
import com.tddworks.openai.api.chat.internal.default

internal class AzureChatApi(
    private val chatCompletionPath: String,
    private val requester: HttpRequester,
    private val extraHeaders: Map<String, String> = mapOf(),
    private val chatApi: Chat = Chat.default(
        requester = requester,
        chatCompletionPath = chatCompletionPath,
        extraHeaders = extraHeaders
    )
) : Chat by chatApi {
    companion object {
        const val BASE_URL = "https://YOUR_RESOURCE_NAME.openai.azure.com"
        const val CHAT_COMPLETIONS = "chat/completions"
    }
}

fun Chat.Companion.azure(
    apiKey: () -> String,
    requester: HttpRequester,
    chatCompletionPath: String = AzureChatApi.CHAT_COMPLETIONS,
): Chat = AzureChatApi(
    requester = requester,
    chatCompletionPath = chatCompletionPath,
    extraHeaders = mapOf("api-key" to apiKey())
)