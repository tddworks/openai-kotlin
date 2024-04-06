package com.tddworks.anthropic.di

import com.tddworks.anthropic.api.Anthropic
import com.tddworks.anthropic.api.messages.api.AnthropicConfig
import com.tddworks.anthropic.api.messages.api.Messages
import com.tddworks.anthropic.api.messages.api.internal.DefaultMessagesApi
import com.tddworks.anthropic.api.messages.api.internal.JsonLenient
import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.createHttpClient
import com.tddworks.common.network.api.ktor.internal.default
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect fun platformModule(): Module

fun initKoin(module: Module, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule() + module)
    }

fun commonModule() = module {
    singleOf(::createJson)
}

fun anthropicModules(
    baseUrl: () -> String,
    apiKey: () -> String,
    anthropicVersion: () -> String,
) = module {

    single<Anthropic> {
        Anthropic(
            baseUrl = baseUrl,
            apiKey = apiKey,
            anthropicVersion = anthropicVersion
        )
    }

    single<HttpRequester> {
        HttpRequester.default(
            createHttpClient(
                url = baseUrl
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

fun createJson() = JsonLenient