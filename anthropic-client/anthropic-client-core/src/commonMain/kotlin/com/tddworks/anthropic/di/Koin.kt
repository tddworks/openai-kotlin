package com.tddworks.anthropic.di

import com.tddworks.anthropic.api.messages.api.internal.JsonLenient
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect fun platformModule(): Module

fun initKoin(module: Module, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule() + module)
    }

fun commonModule() = module {
    singleOf(::createJson)
}

fun createJson() = JsonLenient