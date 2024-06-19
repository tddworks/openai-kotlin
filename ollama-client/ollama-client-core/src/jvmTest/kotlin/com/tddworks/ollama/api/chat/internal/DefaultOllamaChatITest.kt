package com.tddworks.ollama.api.chat.internal

import com.tddworks.di.getInstance
import com.tddworks.ollama.api.Ollama
import com.tddworks.ollama.api.OllamaConfig
import com.tddworks.ollama.api.chat.OllamaChatMessage
import com.tddworks.ollama.api.chat.OllamaChatRequest
import com.tddworks.ollama.di.initOllama
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.koin.test.junit5.AutoCloseKoinTest

@Disabled
class DefaultOllamaChatITest : AutoCloseKoinTest() {

    @BeforeEach
    fun setUp() {
        initOllama(
            config = OllamaConfig(
                protocol = { "http" },
                baseUrl = { "localhost" },
                port = { 11434 }
            )
        )
    }


    @Test
    fun `should return correct base url`() {
        assertEquals("localhost", Ollama.BASE_URL)
    }


    @Test
    fun `should return stream response`() = runTest {
        val ollama = getInstance<Ollama>()

        ollama.stream(
            OllamaChatRequest(
                model = "llama2",
                messages = listOf(
                    OllamaChatMessage(
                        role = "user",
                        content = "hello"
                    )
                )
            )
        ).collect {
            println("stream response: $it")
        }
    }

    @Test
    fun `should return create response`() = runTest {
        val ollama = getInstance<Ollama>()

        val r = ollama.request(
            OllamaChatRequest(
                model = "llama2",
                messages = listOf(
                    OllamaChatMessage(
                        role = "user",
                        content = "hello"
                    )
                )
            )
        )

        println("create response: $r")

        assertNotNull(r.message?.content)
    }
}