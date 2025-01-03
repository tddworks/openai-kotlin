package com.tddworks.openai.gateway.di

import com.tddworks.anthropic.di.anthropicModules
import com.tddworks.di.commonModule
import com.tddworks.ollama.di.ollamaModules
import com.tddworks.openai.di.openAIModules
import com.tddworks.openai.gateway.api.internal.AnthropicOpenAIProvider
import com.tddworks.openai.gateway.api.internal.OllamaOpenAIProvider
import com.tddworks.openai.gateway.api.OpenAIGateway
import com.tddworks.openai.gateway.api.OpenAIProvider
import com.tddworks.openai.gateway.api.internal.*
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import com.tddworks.gemini.di.GeminiModule.Companion.geminiModules

@ExperimentalSerializationApi
fun initOpenAIGateway(
    openAIConfig: DefaultOpenAIProviderConfig,
    anthropicConfig: AnthropicOpenAIProviderConfig,
    ollamaConfig: OllamaOpenAIProviderConfig,
    geminiConfig: GeminiOpenAIProviderConfig,
    appDeclaration: KoinAppDeclaration = {},
) = startKoin {
    appDeclaration()
    modules(
        commonModule(false),
        openAIModules(openAIConfig.toOpenAIConfig()),
        anthropicModules(anthropicConfig.toAnthropicOpenAIConfig()),
        ollamaModules(ollamaConfig.toOllamaConfig()),
        geminiModules(geminiConfig.toGeminiConfig()),
        openAIProviderConfigsModule(
            openAIConfig,
            anthropicConfig,
            ollamaConfig,
            geminiConfig
        ),
        openAIGatewayModules()
    )
}.koin.get<OpenAIGateway>()


private fun openAIProviderConfigsModule(
    openAIConfig: DefaultOpenAIProviderConfig,
    anthropicConfig: AnthropicOpenAIProviderConfig,
    ollamaConfig: OllamaOpenAIProviderConfig,
    geminiConfig: GeminiOpenAIProviderConfig
) = module {
    single { openAIConfig }
    single { anthropicConfig }
    single { ollamaConfig }
    single { geminiConfig }
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
    single {
        listOf(
            AnthropicOpenAIProvider(config = get()),
            OllamaOpenAIProvider(config = get()),
            GeminiOpenAIProvider(
                config = get(),
                client = get()
            )
        )
    }

    single<OpenAIGateway> { DefaultOpenAIGateway(get()) }
}
