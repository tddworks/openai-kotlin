package com.tddworks.azure.api

import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.api.performRequest
import com.tddworks.common.network.api.ktor.api.streamRequest
import com.tddworks.common.network.api.ktor.internal.ClientFeatures
import com.tddworks.common.network.api.ktor.internal.UrlBasedConnectionConfig
import com.tddworks.common.network.api.ktor.internal.createHttpClient
import com.tddworks.common.network.api.ktor.internal.default
import com.tddworks.di.createJson
import com.tddworks.openai.api.OpenAI
import com.tddworks.openai.api.chat.api.Chat
import com.tddworks.openai.api.chat.api.Chat.Companion.CHAT_COMPLETIONS_PATH
import com.tddworks.openai.api.chat.api.ChatCompletion
import com.tddworks.openai.api.chat.api.ChatCompletionChunk
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.images.api.Images
import com.tddworks.openai.api.images.internal.DefaultImagesApi
import com.tddworks.openai.api.legacy.completions.api.Completions
import com.tddworks.openai.api.legacy.completions.api.internal.DefaultCompletionsApi
import com.tddworks.openai.gateway.api.OpenAIProviderConfig
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

data class AzureAIProviderConfig(
    override val apiKey: () -> String,
    override val baseUrl: () -> String = { DEFAULT_BASE_URL },
    val deploymentId: () -> String,
    val apiVersion: () -> String,
) : OpenAIProviderConfig {
    companion object {
        const val DEFAULT_BASE_URL = "https://YOUR_RESOURCE_NAME.openai.azure.com"
    }
}

fun OpenAIProviderConfig.Companion.azure(
    apiKey: () -> String,
    baseUrl: () -> String,
    deploymentId: () -> String,
    apiVersion: () -> String
) = AzureAIProviderConfig(
    apiKey = apiKey,
    baseUrl = baseUrl,
    deploymentId = deploymentId,
    apiVersion = apiVersion
)

/**
 * Authentication
 * Azure OpenAI provides two methods for authentication. You can use either API Keys or Microsoft Entra ID.
 *
 * API Key authentication: For this type of authentication, all API requests must include the API Key in the api-key HTTP header. The Quickstart provides guidance for how to make calls with this type of authentication.
 *
 * Microsoft Entra ID authentication: You can authenticate an API call using a Microsoft Entra token. Authentication tokens are included in a request as the Authorization header. The token provided must be preceded by Bearer, for example Bearer YOUR_AUTH_TOKEN. You can read our how-to guide on authenticating with Microsoft Entra ID.
 */
fun OpenAI.Companion.azure(config: AzureAIProviderConfig): OpenAI {
    val requester = HttpRequester.default(
        createHttpClient(
            // https://learn.microsoft.com/en-us/azure/ai-services/openai/reference
            // POST https://YOUR_RESOURCE_NAME.openai.azure.com/openai/deployments/YOUR_DEPLOYMENT_NAME/completions?api-version=2024-06-01
            connectionConfig = UrlBasedConnectionConfig { "${config.baseUrl()}/openai/deployments/${config.deploymentId()}/" },
            features = ClientFeatures(
                json = createJson(),
                queryParams = mapOf("api-version" to config.apiVersion())
            )
        )
    )
    return azure(
        config = config,
        requester = requester,
        chatCompletionPath = "chat/completions"
    )
}

fun azure(
    config: AzureAIProviderConfig,
    requester: HttpRequester,
    chatCompletionPath: String
): OpenAI {
    val chatApi = AzureChatApi(
        config = config,
        requester = requester,
        chatCompletionPath = chatCompletionPath
    )

    val imagesApi = DefaultImagesApi(
        requester = requester
    )

    val completionsApi = DefaultCompletionsApi(
        requester = requester
    )

    return object : OpenAI, Chat by chatApi, Images by imagesApi,
        Completions by completionsApi {}
}

@OptIn(ExperimentalSerializationApi::class)
class AzureChatApi(
    private val config: AzureAIProviderConfig,
    private val requester: HttpRequester,
    private val chatCompletionPath: String = CHAT_COMPLETIONS_PATH
) : Chat {
    override suspend fun chatCompletions(request: ChatCompletionRequest): ChatCompletion {
        return requester.performRequest<ChatCompletion> {
            method = HttpMethod.Post
            url(path = chatCompletionPath)
            setBody(request)
            contentType(ContentType.Application.Json)
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
                append("api-key", config.apiKey())
                append(HttpHeaders.CacheControl, "no-cache")
                append(HttpHeaders.Connection, "keep-alive")
            }
        }
    }

}