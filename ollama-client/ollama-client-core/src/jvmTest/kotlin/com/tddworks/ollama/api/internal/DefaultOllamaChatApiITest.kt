package com.tddworks.ollama.api.internal

import com.tddworks.common.network.api.ktor.internal.DefaultHttpRequester
import com.tddworks.common.network.api.ktor.internal.createHttpClient
import com.tddworks.di.initKoin
import com.tddworks.ollama.api.Ollama
import com.tddworks.ollama.api.chat.OllamaChatMessage
import com.tddworks.ollama.api.chat.OllamaChatRequest
import com.tddworks.ollama.api.chat.internal.DefaultOllamaChatApi
import com.tddworks.ollama.api.chat.internal.JsonLenient
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.test.junit5.AutoCloseKoinTest

class DefaultOllamaChatApiITest : AutoCloseKoinTest() {

    @BeforeEach
    fun setUp() {
        initKoin()
    }


    @Test
    fun `should return correct base url`() {
        assertEquals("api.anthropic.com", Ollama.BASE_URL)
    }


    @Test
    fun `should return stream response`() = runTest {
        val ollamaChatApi = DefaultOllamaChatApi(
            requester = DefaultHttpRequester(
                createHttpClient(
                    url = { "localhost" },
                    json = JsonLenient,
                )
            )
        )

        ollamaChatApi.stream(
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

//    @Test
//    fun `should return create response`() = runTest {
//        //Client request(POST https://klaude.asusual.life/v1/messages) invalid: 401 Unauthorized. Text: "{"type":"error","error":{"type":"authentication_error","message":"invalid x-api-key"}}"
//        //Client request(POST https://klaude.asusual.life/v1/messages) invalid: 400 Bad Request. Text: "{"type":"error","error":{"type":"invalid_request_error","message":"anthropic-version: header is required"}}"
//        val anthropic = getInstance<Anthropic>()
//
//        val r = anthropic.create(
//            CreateMessageRequest(
//                messages = listOf(Message.user("hello")),
//                maxTokens = 1024,
//                model = Model.CLAUDE_3_HAIKU
//            )
//        )
//
//        assertNotNull(r.content[0].text)
//    }
}