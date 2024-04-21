package com.tddworks.di

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import kotlin.test.assertNotNull

class CommonKoinTest : KoinTest {
    @Test
    fun `should initialize common koin modules`() {
        koinApplication {
            initKoin { }
        }.checkModules()

        val json = getInstance<Json>()

        assertNotNull(json)
    }
}