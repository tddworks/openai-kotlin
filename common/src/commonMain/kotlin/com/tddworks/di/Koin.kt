package com.tddworks.di

import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule(enableNetworkLogs = enableNetworkLogs))
    }

//TODO - enableNetworkLogs is not used
fun commonModule(enableNetworkLogs: Boolean) = module {
    singleOf(::createJson)
}

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }


inline fun <reified T> getInstance(): T {
    return object : KoinComponent {
        val value: T by inject()
    }.value
}