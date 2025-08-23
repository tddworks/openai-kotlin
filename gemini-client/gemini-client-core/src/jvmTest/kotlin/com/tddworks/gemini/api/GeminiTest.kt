package com.tddworks.gemini.api

import com.tddworks.di.getInstance
import com.tddworks.gemini.api.textGeneration.api.Gemini
import com.tddworks.gemini.api.textGeneration.api.GeminiConfig
import com.tddworks.gemini.di.initGemini
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.test.junit5.AutoCloseKoinTest

class GeminiTest : AutoCloseKoinTest() {

    @BeforeEach
    fun setUp() {
        initGemini(config = GeminiConfig())
    }

    @Test
    fun `should get gemini default api`() {
        val gemini = getInstance<Gemini>()

        assertNotNull(gemini)
    }
}
