package com.tddworks.anthropic.di

import com.tddworks.anthropic.api.Anthropic
import com.tddworks.anthropic.api.AnthropicConfig
import com.tddworks.anthropic.api.messages.api.Messages
import com.tddworks.anthropic.api.messages.api.internal.DefaultMessagesApi
import com.tddworks.anthropic.api.messages.api.internal.JsonLenient
import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.createHttpClient
import com.tddworks.common.network.api.ktor.internal.default
import com.tddworks.di.commonModule
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect fun platformModule(): Module

fun iniAnthropic(
    config: AnthropicConfig,
    appDeclaration: KoinAppDeclaration = {}
): Anthropic {
    return startKoin {
        appDeclaration()
        modules(commonModule(false) + anthropicModules(config))
    }.koin.get<Anthropic>()
}

fun anthropicModules(
    config: AnthropicConfig,
) = module {
    single<Anthropic> {
        Anthropic(
            baseUrl = config.baseUrl,
            apiKey = config.apiKey,
            anthropicVersion = config.anthropicVersion
        )
    }

    single<Json>(named("anthropicJson")) { JsonLenient }

    single<HttpRequester>(named("anthropicHttpRequester")) {
        HttpRequester.default(
            createHttpClient(
                host = config.baseUrl,
                json = get(named("anthropicJson")),
            )
        )
    }

    single<Messages> {
        DefaultMessagesApi(
            anthropicConfig = config,
            requester = get(named("anthropicHttpRequester"))
        )
    }
}