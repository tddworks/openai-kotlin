package com.tddworks.openai.gateway.di

import com.tddworks.anthropic.di.anthropicModules
import com.tddworks.di.commonModule
import com.tddworks.di.getInstance
import com.tddworks.ollama.di.ollamaModules
import com.tddworks.openai.di.openAIModules
import com.tddworks.openai.gateway.api.AnthropicOpenAIProvider
import com.tddworks.openai.gateway.api.OllamaOpenAIProvider
import com.tddworks.openai.gateway.api.OpenAIGateway
import com.tddworks.openai.gateway.api.OpenAIProvider
import com.tddworks.openai.gateway.api.internal.*
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

@ExperimentalSerializationApi
fun initOpenAIGateway(
    openAIConfig: DefaultOpenAIProviderConfig,
    anthropicConfig: AnthropicOpenAIProviderConfig,
    ollamaConfig: OllamaOpenAIProviderConfig,
    appDeclaration: KoinAppDeclaration = {},
) = startKoin {
    appDeclaration()
    modules(
        commonModule(false)
                + openAIModules(openAIConfig.toOpenAIConfig())
                + anthropicModules(anthropicConfig.toAnthropicOpenAIConfig())
                + ollamaModules(ollamaConfig.toOllamaConfig())
                + openAIProviderConfigsModule(openAIConfig, anthropicConfig, ollamaConfig)
                + openAIGatewayModules()
    )
}.koin.get<OpenAIGateway>()


private fun openAIProviderConfigsModule(
    openAIConfig: DefaultOpenAIProviderConfig,
    anthropicConfig: AnthropicOpenAIProviderConfig,
    ollamaConfig: OllamaOpenAIProviderConfig
) = module {
    single { openAIConfig }
    single { anthropicConfig }
    single { ollamaConfig }
}


@ExperimentalSerializationApi
fun createOpenAIGateway(providers: List<OpenAIProvider>) = startKoin {
    modules(
        commonModule(false) + openAIGatewayModules(providers)
    )
}.koin.get<OpenAIGateway>()


@OptIn(ExperimentalSerializationApi::class)
fun openAIGatewayModules(providers: List<OpenAIProvider>) = module {
    single { providers }
    single<OpenAIGateway> { DefaultOpenAIGateway(get()) }
}

@ExperimentalSerializationApi
fun openAIGatewayModules() = module {
    single<AnthropicOpenAIProvider> { AnthropicOpenAIProvider(config = get()) }
    single<OllamaOpenAIProvider> { OllamaOpenAIProvider(config = get()) }

    single {
        listOf(
            get<AnthropicOpenAIProvider>(), get<OllamaOpenAIProvider>()
        )
    }

    single<OpenAIGateway> { DefaultOpenAIGateway(get()) }
}
