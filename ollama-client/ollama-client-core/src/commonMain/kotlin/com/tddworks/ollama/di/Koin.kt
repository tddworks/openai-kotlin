package com.tddworks.ollama.di

import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.*
import com.tddworks.di.commonModule
import com.tddworks.ollama.api.Ollama
import com.tddworks.ollama.api.OllamaConfig
import com.tddworks.ollama.api.chat.OllamaChat
import com.tddworks.ollama.api.chat.internal.DefaultOllamaChatApi
import com.tddworks.ollama.api.generate.OllamaGenerate
import com.tddworks.ollama.api.generate.internal.DefaultOllamaGenerateApi
import com.tddworks.ollama.api.json.JsonLenient
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initOllama(config: OllamaConfig, appDeclaration: KoinAppDeclaration = {}): Ollama {
    return startKoin {
        appDeclaration()
        modules(commonModule(false) + ollamaModules(config))
    }.koin.get<Ollama>()
}

fun ollamaModules(
    config: OllamaConfig,
) = module {

    single<Ollama> {
        Ollama.create(ollamaConfig = config)
    }

    single<Json>(named("ollamaJson")) { JsonLenient }

    single<HttpRequester>(named("ollamaHttpRequester")) {
        HttpRequester.default(
            createHttpClient(
                connectionConfig = UrlBasedConnectionConfig(
                    baseUrl = config.baseUrl,
                ),
                features = ClientFeatures(json = get(named("ollamaJson")))
            )
        )
    }

    single<OllamaChat> {
        DefaultOllamaChatApi(
            requester = get(named("ollamaHttpRequester"))
        )
    }

    single<OllamaGenerate> {
        DefaultOllamaGenerateApi(
            requester = get(named("ollamaHttpRequester"))
        )
    }
}