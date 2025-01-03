package com.tddworks.gemini.di

import com.tddworks.di.commonModule
import com.tddworks.gemini.api.textGeneration.api.GeminiConfig
import com.tddworks.gemini.di.GeminiModule.Companion.geminiModules
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initGemini(
    config: GeminiConfig,
    enableNetworkLogs: Boolean = false,
    appDeclaration: KoinAppDeclaration = {}
) = startKoin {
    appDeclaration()
    modules(
        commonModule(enableNetworkLogs),
        geminiModules(config)
    )
}
