package com.tddworks.openai.di

import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.createHttpClient
import com.tddworks.common.network.api.ktor.internal.default
import com.tddworks.di.commonModule
import com.tddworks.openai.api.OpenAI
import com.tddworks.openai.api.OpenAIApi
import com.tddworks.openai.api.OpenAIConfig
import com.tddworks.openai.api.chat.api.Chat
import com.tddworks.openai.api.chat.internal.DefaultChatApi
import com.tddworks.openai.api.images.api.Images
import com.tddworks.openai.api.images.internal.DefaultImagesApi
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


fun initOpenAIKoin(config: OpenAIConfig, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule(false) + openAIModules(config))
    }

fun openAIModules(
    config: OpenAIConfig,
) = module {

    single<OpenAI> {
        OpenAIApi()
    }

    single<HttpRequester>(named("openAIHttpRequester")) {
        HttpRequester.default(
            createHttpClient(
                host = config.baseUrl,
                authToken = config.apiKey,
                // get from commonModule
                json = get(),
            )
        )
    }

    single<Chat> {
        DefaultChatApi(
            requester = get(named("openAIHttpRequester"))
        )
    }

    single<Images> {
        DefaultImagesApi(
            requester = get(named("openAIHttpRequester"))
        )
    }
}