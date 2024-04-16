package com.tddworks.ollama.di

import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.createHttpClient
import com.tddworks.common.network.api.ktor.internal.default
import com.tddworks.di.commonModule
import com.tddworks.ollama.api.Ollama
import com.tddworks.ollama.api.OllamaConfig
import com.tddworks.ollama.api.chat.OllamaChat
import com.tddworks.ollama.api.chat.internal.DefaultOllamaChatApi
import com.tddworks.ollama.api.chat.internal.JsonLenient
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun iniOllamaKoin(config: OllamaConfig, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule(false) + ollamaModules(config))
    }

fun ollamaModules(
    config: OllamaConfig,
) = module {

    single<Ollama> {
        Ollama(
            baseUrl = config.baseUrl,
            port = config.port,
            protocol = config.protocol
        )
    }

    single<Json>(named("ollamaJson")) { JsonLenient }

    single<HttpRequester>(named("ollamaHttpRequester")) {
        HttpRequester.default(
            createHttpClient(
                protocol = config.protocol,
                port = config.port,
                host = config.baseUrl,
                json = get(named("ollamaJson")),
            )
        )
    }

    single<OllamaChat> {
        DefaultOllamaChatApi(
            jsonLenient = get(named("ollamaJson")),
            requester = get(named("ollamaHttpRequester"))
        )
    }
}