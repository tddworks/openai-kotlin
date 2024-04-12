package com.tddworks.anthropic.di

import com.tddworks.anthropic.api.Anthropic
import com.tddworks.anthropic.api.messages.api.AnthropicConfig
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

fun iniAnthropicKoin(config: AnthropicConfig, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule(false) + anthropicModules(config))
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
                url = config.baseUrl,
                json = get(),
            )
        )
    }

    single<Messages> {
        DefaultMessagesApi(
            anthropicConfig = config,
            jsonLenient = get(named("anthropicJson")),
            requester = get(named("anthropicHttpRequester"))
        )
    }
}