package com.tddworks.ollama.di

import com.tddworks.di.commonModule
import com.tddworks.ollama.api.Ollama
import com.tddworks.ollama.api.OllamaConfig
import org.koin.core.context.startKoin
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
        )
    }
}