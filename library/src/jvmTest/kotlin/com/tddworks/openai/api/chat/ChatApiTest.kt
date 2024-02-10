package com.tddworks.openai.api.chat

import app.cash.turbine.test
import com.snacks.openai.api.chat.*
import com.tddworks.openai.api.chat.internal.DefaultChatApi
import com.tddworks.openai.api.internal.network.ktor.DefaultHttpRequester
import com.tddworks.openai.api.mockHttpClient
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@OptIn(ExperimentalSerializationApi::class)
class ChatApiTest {

//    @Test
//    fun `should return chat completion`() = runBlocking {
//        val request = ChatCompletionRequest.chatCompletionRequest(listOf(ChatMessage("hello")))
//
//        val ktorCompletionsApi = KtorChatApi(
//            openAIHttpClient = DefaultOpenAIHttpClient(
//                httpClient = mockHttpClient(
//                    """
//                    {
//                      "id": "chatcmpl-8Zu4AF8QMK3zFgdzXIPjFS4VkWErX",
//                      "object": "chat.completion",
//                      "created": 1703567690,
//                      "model": "gpt-3.5-turbo-0613",
//                      "choices": [
//                        {
//                          "index": 0,
//                          "message": {
//                            "role": "assistant",
//                            "content": "Hello! How can I assist you today?"
//                          },
//                          "logprobs": null,
//                          "finish_reason": "stop"
//                        }
//                      ],
//                      "usage": {
//                        "prompt_tokens": 8,
//                        "completion_tokens": 9,
//                        "total_tokens": 17
//                      },
//                      "system_fingerprint": null
//                    }
//                """.trimIndent()
//                )
//            )
//        )
//
//        val r = ktorCompletionsApi.chatCompletion(
//            request
//        )
//
//        with(r) {
//            assertEquals("chatcmpl-8Zu4AF8QMK3zFgdzXIPjFS4VkWErX", id)
//            assertEquals(1703567690, created)
//            assertEquals("gpt-3.5-turbo-0613", model)
//            assertEquals(1, choices.size)
//            assertEquals("Hello! How can I assist you today?", choices[0].message.content)
//            assertEquals(0, choices[0].index)
//        }
//    }

    @Test
    fun `should return stream of completions`() = runBlocking {
        val request = ChatCompletionRequest.chatCompletionsRequest(listOf(ChatMessage.UserMessage("hello")))

        val chat = DefaultChatApi(
            requester = DefaultHttpRequester(
                httpClient = mockHttpClient("data: {\"id\":\"chatcmpl-8ZtRSZzsijxilL2lDBN7ERQc0Zi7Q\",\"object\":\"chat.completion.chunk\",\"created\":1703565290,\"model\":\"gpt-3.5-turbo-0613\",\"system_fingerprint\":null,\"choices\":[{\"index\":0,\"delta\":{\"content\":\" there\"},\"logprobs\":null,\"finish_reason\":null}]}")
            )
        )

        chat.streamCompletions(request).test {
            assertEquals(
                ChatCompletionChunk(
                    id = "chatcmpl-8ZtRSZzsijxilL2lDBN7ERQc0Zi7Q",
                    `object` = "chat.completion.chunk",
                    created = 1703565290,
                    model = "gpt-3.5-turbo-0613",
                    choices = listOf(
                        ChatChunk(
                            index = 0,
                            delta = ChatDelta(
                                content = " there"
                            ),
                            finishReason = null
                        )
                    )
                ), awaitItem()
            )
            awaitComplete()
        }
    }

//    @Disabled
//    @Test
//    fun chatCompletion() = runBlocking {
//        val url = System.getenv("OPEN_API_URL") ?: ""
//        val token = System.getenv("OPEN_API_TOKEN") ?: ""
//
//        val ktorCompletionsApi = KtorChatApi(
//            openAIHttpClient = OpenAIHttpClient.default(
//                httpClient = createHttpClient(
//                    apiUrl = { url },
//                    apiToken = { token },
//                    engine = CIO
//                )
//            )
//        )
//
//
//        val r = ktorCompletionsApi.chatCompletion(
//            ChatCompletionRequest.chatCompletionRequest(
//                listOf(ChatMessage("hello")),
//            )
//        )
//
//        assertEquals(1, r.choices.size)
//    }
//
//    @Disabled
//    @Test
//    fun chatCompletions() = runBlocking {
//        val url = System.getenv("OPEN_API_URL") ?: ""
//        val token = System.getenv("OPEN_API_TOKEN") ?: ""
//
//        val ktorCompletionsApi = KtorChatApi(
//            openAIHttpClient = OpenAIHttpClient.default(
//                httpClient = createHttpClient(
//                    apiUrl = { url },
//                    apiToken = { token },
//                    engine = CIO
//                )
//            )
//        )
//
//
//        val r = ktorCompletionsApi.chatCompletions(
//            ChatCompletionRequest.chatCompletionsRequest(
//                listOf(ChatMessage("hello")),
//            )
//        ).toList()
//
//        assertEquals(11, r.size)
//    }
}