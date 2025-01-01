package com.tddworks.gemini.di

import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.*
import com.tddworks.di.commonModule
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

//
//fun initGemini(
//    appDeclaration: KoinAppDeclaration = {}
//): HttpRequester {
//    return startKoin {
//        appDeclaration()
//        modules(commonModule(false) + geminiModules())
//    }.koin.get<HttpRequester>()
//}

@Module
@ComponentScan("com.tddworks.gemini")
class GeminiModule