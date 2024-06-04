package com.tddworks.di

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import org.koin.dsl.koinApplication
import org.koin.test.check.checkModules
import org.koin.test.junit5.AutoCloseKoinTest
import kotlin.test.assertNotNull

class CommonKoinTest : AutoCloseKoinTest() {
    @Test
    fun `should initialize common koin modules`() {
        koinApplication {
            initKoin { }
        }.checkModules()

        val json = getInstance<Json>()

        assertNotNull(json)
    }
}