package com.tddworks.azure.api

import com.tddworks.azure.api.internal.AzureChatApi
import com.tddworks.azure.api.internal.azure
import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.ClientFeatures
import com.tddworks.common.network.api.ktor.internal.UrlBasedConnectionConfig
import com.tddworks.common.network.api.ktor.internal.createHttpClient
import com.tddworks.common.network.api.ktor.internal.default
import com.tddworks.di.createJson
import com.tddworks.openai.api.OpenAI
import com.tddworks.openai.api.chat.api.Chat
import com.tddworks.openai.api.images.api.Images
import com.tddworks.openai.api.images.internal.default
import com.tddworks.openai.api.legacy.completions.api.Completions
import com.tddworks.openai.api.legacy.completions.api.internal.default
import com.tddworks.openai.gateway.api.OpenAIProviderConfig

data class AzureAIProviderConfig(
    override val apiKey: () -> String,
    override val baseUrl: () -> String = { AzureChatApi.BASE_URL },
    val deploymentId: () -> String,
    val apiVersion: () -> String,
) : OpenAIProviderConfig

fun OpenAIProviderConfig.Companion.azure(
    apiKey: () -> String,
    baseUrl: () -> String,
    deploymentId: () -> String,
    apiVersion: () -> String,
) =
    AzureAIProviderConfig(
        apiKey = apiKey,
        baseUrl = baseUrl,
        deploymentId = deploymentId,
        apiVersion = apiVersion,
    )

/**
 * Authentication Azure OpenAI provides two methods for authentication. You can use either API Keys
 * or Microsoft Entra ID.
 *
 * API Key authentication: For this type of authentication, all API requests must include the API
 * Key in the api-key HTTP header. The Quickstart provides guidance for how to make calls with this
 * type of authentication.
 *
 * Microsoft Entra ID authentication: You can authenticate an API call using a Microsoft Entra
 * token. Authentication tokens are included in a request as the Authorization header. The token
 * provided must be preceded by Bearer, for example Bearer YOUR_AUTH_TOKEN. You can read our how-to
 * guide on authenticating with Microsoft Entra ID.
 */
fun OpenAI.Companion.azure(config: AzureAIProviderConfig): OpenAI {
    val requester =
        HttpRequester.default(
            createHttpClient(
                // https://learn.microsoft.com/en-us/azure/ai-services/openai/reference
                // POST
                // https://YOUR_RESOURCE_NAME.openai.azure.com/openai/deployments/YOUR_DEPLOYMENT_NAME/completions?api-version=2024-06-01
                connectionConfig =
                    UrlBasedConnectionConfig {
                        "${config.baseUrl()}/openai/deployments/${config.deploymentId()}/"
                    },
                features =
                    ClientFeatures(
                        json = createJson(),
                        queryParams = { mapOf("api-version" to config.apiVersion()) },
                    ),
            )
        )
    val chatApi =
        Chat.azure(
            apiKey = config.apiKey,
            requester = requester,
            chatCompletionPath = AzureChatApi.CHAT_COMPLETIONS,
        )

    // TODO implement the rest of the APIs for Azure Images.azure
    val imagesApi = Images.default(requester = requester)
    // TODO implement the rest of the APIs for Azure Completions.azure
    val completionsApi = Completions.default(requester = requester)
    return object : OpenAI, Chat by chatApi, Images by imagesApi, Completions by completionsApi {}
}
