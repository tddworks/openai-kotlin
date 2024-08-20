package com.tddworks.openai.gateway.di

import com.tddworks.anthropic.api.AnthropicConfig
import com.tddworks.anthropic.di.anthropicModules
import com.tddworks.di.commonModule
import com.tddworks.ollama.api.OllamaConfig
import com.tddworks.ollama.di.ollamaModules
import com.tddworks.openai.api.OpenAIConfig
import com.tddworks.openai.di.openAIModules
import com.tddworks.openai.gateway.api.AnthropicOpenAIProvider
import com.tddworks.openai.gateway.api.OllamaOpenAIProvider
import com.tddworks.openai.gateway.api.OpenAIGateway
import com.tddworks.openai.gateway.api.OpenAIProvider
import com.tddworks.openai.gateway.api.internal.DefaultOpenAIGateway
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

@ExperimentalSerializationApi
fun initOpenAIGateway(
    openAIConfig: OpenAIConfig,
    anthropicConfig: AnthropicConfig,
    ollamaConfig: OllamaConfig,
    appDeclaration: KoinAppDeclaration = {},
) = startKoin {
    appDeclaration()
    modules(
        commonModule(false) +
                anthropicModules(anthropicConfig) +
                openAIModules(openAIConfig) +
                ollamaModules(ollamaConfig) +
                openAIGatewayModules()
    )
}.koin.get<OpenAIGateway>()


@ExperimentalSerializationApi
fun createOpenAIGateway(providers: List<OpenAIProvider>) = startKoin {
    modules(
        commonModule(false) +
                openAIGatewayModules(providers)
    )
}.koin.get<OpenAIGateway>()


@OptIn(ExperimentalSerializationApi::class)
fun openAIGatewayModules(providers: List<OpenAIProvider>) = module {
    single { providers }
    single<OpenAIGateway> { DefaultOpenAIGateway(get()) }
}

@ExperimentalSerializationApi
fun openAIGatewayModules() = module {
    single<AnthropicOpenAIProvider> { AnthropicOpenAIProvider(get()) }
    single<OllamaOpenAIProvider> { OllamaOpenAIProvider(get()) }

    single {
        listOf(
            get<AnthropicOpenAIProvider>(),
            get<OllamaOpenAIProvider>()
        )
    }

    single<OpenAIGateway> { DefaultOpenAIGateway(get()) }
}
