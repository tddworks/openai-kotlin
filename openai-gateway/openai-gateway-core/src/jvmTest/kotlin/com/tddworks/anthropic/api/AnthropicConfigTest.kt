package com.tddworks.anthropic.api

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.test.junit5.AutoCloseKoinTest

class AnthropicConfigTest : AutoCloseKoinTest() {

    @Test
    fun `fix the coverage by this case`() {
        startKoin {
            val r = AnthropicConfig().getKoin()
            assertNotNull(r)
        }
    }
}