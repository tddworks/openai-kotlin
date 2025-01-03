package com.tddworks.gemini.di

import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.ClientFeatures
import com.tddworks.common.network.api.ktor.internal.UrlBasedConnectionConfig
import com.tddworks.common.network.api.ktor.internal.createHttpClient
import com.tddworks.common.network.api.ktor.internal.default
import com.tddworks.di.createJson
import com.tddworks.gemini.api.textGeneration.api.Gemini
import com.tddworks.gemini.api.textGeneration.api.GeminiConfig
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module
import org.koin.ksp.generated.module

@Module
@ComponentScan("com.tddworks.gemini")
class GeminiModule {
    companion object {
        fun geminiModules(config: GeminiConfig) = module {
            single {
                HttpRequester.default(
                    createHttpClient(
                        connectionConfig = UrlBasedConnectionConfig(config.baseUrl),
                        features = ClientFeatures(
                            json = createJson(),
                            queryParams = mapOf("key" to config.apiKey())
                        )
                    )
                )
            }

            includes(GeminiModule().module)

            single { Gemini.default() }
        }
    }
}

