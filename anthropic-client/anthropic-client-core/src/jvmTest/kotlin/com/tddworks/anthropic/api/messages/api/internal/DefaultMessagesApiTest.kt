package com.tddworks.anthropic.api.messages.api.internal

import app.cash.turbine.test
import com.tddworks.anthropic.api.messages.api.CreateMessageRequest
import com.tddworks.anthropic.api.messages.api.CreateMessageResponse
import com.tddworks.anthropic.api.messages.api.Message
import com.tddworks.anthropic.api.messages.api.Usage
import com.tddworks.anthropic.api.messages.api.stream.MessageStart
import com.tddworks.anthropic.api.mockHttpClient
import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.DefaultHttpRequester
import com.tddworks.openllm.api.ChatApi
import com.tddworks.openllm.api.ChatRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultMessagesApiTest : KoinTest {
    @JvmField
    @RegisterExtension
    // This extension is used to set the main dispatcher to a test dispatcher
    // launch coroutine eagerly
    // same scheduling behavior as would have in a real app/production
    val testKoinCoroutineExtension = TestKoinCoroutineExtension(UnconfinedTestDispatcher())

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            module {
                single<HttpRequester> {
                    DefaultHttpRequester(
                        httpClient = mockHttpClient("data: {\"type\":\"message_start\",\"message\":{\"id\":\"msg_1nZdL29xx5MUA1yADyHTEsnR8uuvGzszyY\",\"type\":\"message\",\"role\":\"assistant\",\"content\":[],\"model\":\"claude-3-opus-20240229\",\"stop_reason\":null,\"stop_sequence\":null,\"usage\":{\"input_tokens\":25,\"output_tokens\":1}}}")
                    )
                }

                single<Json> { JsonLenient }

                single<ChatApi> { DefaultMessagesApi(get()) }
            })
    }

    private val chatsApi: ChatApi by inject()

    @Test
    fun `should return create message message_start stream response`() = runTest {
        // Given
        val request = CreateMessageRequest.streamRequest(listOf(Message.user(("hello"))))


        chatsApi.chat(request).test {
            assertEquals(
                MessageStart(
                    type = "message_start",
                    message = CreateMessageResponse(
                        id = "msg_1nZdL29xx5MUA1yADyHTEsnR8uuvGzszyY",
                        type = "message",
                        role = "assistant",
                        content = emptyList(),
                        model = "claude-3-opus-20240229",
                        stopReason = null,
                        stopSequence = null,
                        usage = Usage(
                            inputTokens = 25,
                            outputTokens = 1
                        )
                    )
                ), awaitItem()
            )
            awaitComplete()
        }
    }


    @Test
    fun `should return create message response`() = runTest {
        val request = CreateMessageRequest(listOf(Message.user(("hello"))))

        val chat = DefaultMessagesApi(
            requester = DefaultHttpRequester(
                httpClient = mockHttpClient(
                    """
                    {
                      "content": [
                        {
                          "text": "Hi! My name is Claude.",
                          "type": "text"
                        }
                      ],
                      "id": "msg_013Zva2CMHLNnXjNJJKqJ2EF",
                      "model": "claude-3-opus-20240229",
                      "role": "assistant",
                      "stop_reason": "end_turn",
                      "stop_sequence": null,
                      "type": "message",
                      "usage": {
                        "input_tokens": 10,
                        "output_tokens": 25
                      }
                    }
                """.trimIndent()
                )
            )
        )


        val r = chat.chat(request as ChatRequest) as CreateMessageResponse

        with(r) {
            assertEquals("msg_013Zva2CMHLNnXjNJJKqJ2EF", id)
            assertEquals("claude-3-opus-20240229", model)
            assertEquals("assistant", role)
            assertEquals("end_turn", stopReason)
            assertEquals(null, stopSequence)
            assertEquals("message", type)
            assertEquals(10, usage.inputTokens)
            assertEquals(25, usage.outputTokens)
            assertEquals("Hi! My name is Claude.", content[0].text)
            assertEquals("text", content[0].type)
        }
    }
}