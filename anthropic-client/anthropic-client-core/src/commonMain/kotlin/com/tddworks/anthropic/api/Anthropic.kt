package com.tddworks.anthropic.api

import com.tddworks.anthropic.api.internal.AnthropicApi
import com.tddworks.anthropic.api.messages.api.AnthropicConfig
import com.tddworks.anthropic.api.messages.api.Messages
import com.tddworks.anthropic.api.messages.api.internal.DefaultMessagesApi
import com.tddworks.anthropic.di.initKoin
import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.createHttpClient
import com.tddworks.common.network.api.ktor.internal.default
import org.koin.dsl.module

interface Anthropic : Messages {
    companion object {
        const val BASE_URL = "api.anthropic.com"
    }

    fun apiKey(): String
    fun baseUrl(): String
    fun anthropicVersion(): String
}

fun Anthropic(
    apiKey: () -> String = { "CONFIGURE_ME" },
    baseUrl: () -> String = { Anthropic.BASE_URL },
    anthropicVersion: () -> String = { "2023-06-01" },
): Anthropic {

    initKoin(
        module = module {
            single<HttpRequester> {
                HttpRequester.default(
                    createHttpClient(
                        url = baseUrl()
                    )
                )
            }
            single<Messages> {
                DefaultMessagesApi(
                    anthropicConfig = AnthropicConfig(
                        apiKey = apiKey,
                        anthropicVersion = anthropicVersion

                    ),
                    jsonLenient = get(),
                    requester = get()
                )
            }
        }
    )

    return AnthropicApi(
        apiKey = apiKey(),
        apiURL = baseUrl(),
        anthropicVersion = anthropicVersion()
    )
}