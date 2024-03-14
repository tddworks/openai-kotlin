package com.tddworks.anthropic.api.messages.api.internal

import com.tddworks.anthropic.api.messages.api.CreateMessageRequest
import com.tddworks.anthropic.api.messages.api.CreateMessageResponse
import com.tddworks.anthropic.api.messages.api.Message
import com.tddworks.anthropic.api.mockHttpClient
import com.tddworks.common.network.api.ktor.internal.DefaultHttpRequester
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class DefaultMessagesApiTest {
    @Test
    fun `should return create message response`() = runBlocking {
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


        val r = chat.chat(request) as CreateMessageResponse

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

//    @Test
//    fun `should return stream response of streaming message`() = runBlocking {
//        val request = CreateMessageRequest.streamRequest(listOf(Message.user(("hello"))))
//
//        val chat = DefaultMessagesApi(
//            requester = DefaultHttpRequester(
//                httpClient = mockHttpClient("data: {\"id\":\"chatcmpl-8ZtRSZzsijxilL2lDBN7ERQc0Zi7Q\",\"object\":\"chat.completion.chunk\",\"created\":1703565290,\"model\":\"gpt-3.5-turbo-0613\",\"system_fingerprint\":null,\"choices\":[{\"index\":0,\"delta\":{\"content\":\" there\"},\"logprobs\":null,\"finish_reason\":null}]}")
//            )
//        )
//
//        chat.streamCompletions(request).test {
//            assertEquals(
//                ChatResponseChunk(
//                    id = "chatcmpl-8ZtRSZzsijxilL2lDBN7ERQc0Zi7Q",
//                    `object` = "chat.completion.chunk",
//                    created = 1703565290,
//                    model = "gpt-3.5-turbo-0613",
//                    choices = listOf(
//                        ChatChunk(
//                            index = 0,
//                            delta = ChatDelta(
//                                content = " there"
//                            ),
//                            finishReason = null
//                        )
//                    )
//                ), awaitItem()
//            )
//            awaitComplete()
//        }
//    }
}