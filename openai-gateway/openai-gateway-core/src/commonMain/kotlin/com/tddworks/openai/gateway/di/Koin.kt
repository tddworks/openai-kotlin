package com.tddworks.openai.gateway.di

import com.tddworks.anthropic.api.AnthropicConfig
import com.tddworks.anthropic.di.anthropicModules
import com.tddworks.di.commonModule
import com.tddworks.openai.api.OpenAIConfig
import com.tddworks.openai.di.openAIModules
import com.tddworks.openai.gateway.api.AnthropicOpenAIProvider
import com.tddworks.openai.gateway.api.OpenAIGateway
import com.tddworks.openai.gateway.api.OpenAIProvider
import com.tddworks.openai.gateway.api.internal.DefaultOpenAIGateway
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

@ExperimentalSerializationApi
fun initOpenAIGatewayKoin(
    openAIConfig: OpenAIConfig,
    anthropicConfig: AnthropicConfig,
    appDeclaration: KoinAppDeclaration = {},
) = startKoin {
    appDeclaration()
    modules(
        commonModule(false) +
                anthropicModules(anthropicConfig) +
                openAIModules(openAIConfig) +
                openAIGatewayModules()
    )
}

@ExperimentalSerializationApi
fun openAIGatewayModules() = module {
    single<AnthropicOpenAIProvider> { AnthropicOpenAIProvider(get()) }

    single { listOf<OpenAIProvider>(get<AnthropicOpenAIProvider>()) }

    single<OpenAIGateway> { DefaultOpenAIGateway(get(), get()) }
}
