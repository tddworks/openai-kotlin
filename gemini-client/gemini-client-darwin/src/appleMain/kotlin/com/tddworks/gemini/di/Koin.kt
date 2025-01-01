package com.tddworks.gemini.di

import com.tddworks.di.commonModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.module

fun initGemini(
    enableNetworkLogs: Boolean = false,
    appDeclaration: KoinAppDeclaration = {}
) =
    startKoin {
        appDeclaration()
        modules(
            commonModule(enableNetworkLogs = enableNetworkLogs),
            GeminiModule().module
        )
    }