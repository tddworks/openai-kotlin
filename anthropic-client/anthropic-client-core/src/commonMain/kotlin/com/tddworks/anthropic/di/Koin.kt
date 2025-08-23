package com.tddworks.anthropic.di

import com.tddworks.anthropic.api.Anthropic
import com.tddworks.anthropic.api.AnthropicConfig
import com.tddworks.anthropic.api.messages.api.Messages
import com.tddworks.anthropic.api.messages.api.internal.DefaultMessagesApi
import com.tddworks.anthropic.api.messages.api.internal.JsonLenient
import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.*
import com.tddworks.di.commonModule
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun iniAnthropic(config: AnthropicConfig, appDeclaration: KoinAppDeclaration = {}): Anthropic {
    return startKoin {
            appDeclaration()
            modules(commonModule(false) + anthropicModules(config))
        }
        .koin
        .get<Anthropic>()
}

fun anthropicModules(config: AnthropicConfig) = module {
    single<Anthropic> { Anthropic.create(anthropicConfig = config) }

    single<Json>(named("anthropicJson")) { JsonLenient }

    single<HttpRequester>(named("anthropicHttpRequester")) {
        HttpRequester.default(
            createHttpClient(
                connectionConfig = UrlBasedConnectionConfig(config.baseUrl),
                authConfig = AuthConfig(config.apiKey),
                // get from commonModule
                features = ClientFeatures(json = get(named("anthropicJson"))),
            )
        )
    }

    single<Messages> {
        DefaultMessagesApi(
            anthropicConfig = config,
            requester = get(named("anthropicHttpRequester")),
        )
    }
}
