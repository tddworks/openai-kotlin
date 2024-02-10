package com.tddworks.openai.api

import com.tddworks.openai.api.chat.ChatCompletionRequest
import com.tddworks.openai.api.chat.ChatMessage
import com.tddworks.openai.api.chat.Model
import com.tddworks.openai.api.chat.vision.ImageUrl
import com.tddworks.openai.api.chat.vision.VisionMessageContent
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class OpenAITest {

    @Test
    fun `should return correct base url`() {
        assertEquals("api.openai.com", OpenAI.BASE_URL)
    }

    @Disabled
    @Test
    fun `should return response`() = runBlocking {
        val openAI = OpenAI("api-key")
        val response = openAI.streamCompletions(
            ChatCompletionRequest(
                messages = listOf(
                    ChatMessage.user("hello")
                )
            )
        ).toList().first().content()
        assertEquals("Hello! How can I assist you today?", response)
    }

    /**
     * Response from OpenAI
     * data: {"id":"chatcmpl-8qdK8cCZwvpBDYU6hqcsd4Mzb4Oa1","object":"chat.completion.chunk","created":1707554908,"model":"gpt-4-1106-vision-preview","choices":[{"delta":{"role":"assistant"},"index":0,"finish_reason":null}]}
     *
     * data: {"id":"chatcmpl-8qdK8cCZwvpBDYU6hqcsd4Mzb4Oa1","object":"chat.completion.chunk","created":1707554908,"model":"gpt-4-1106-vision-preview","choices":[{"delta":{"content":"This"},"index":0,"finish_reason":null}]}
     *
     * data: {"id":"chatcmpl-8qdK8cCZwvpBDYU6hqcsd4Mzb4Oa1","object":"chat.completion.chunk","created":1707554908,"model":"gpt-4-1106-vision-preview","choices":[{"delta":{"content":" image"},"index":0,"finish_reason":null}]}
     *
     * data: {"id":"chatcmpl-8qdK8cCZwvpBDYU6hqcsd4Mzb4Oa1","object":"chat.completion.chunk","created":1707554908,"model":"gpt-4-1106-vision-preview","choices":[{"delta":{"content":" shows"},"index":0,"finish_reason":null}]}
     *
     * data: {"id":"chatcmpl-8qdK8cCZwvpBDYU6hqcsd4Mzb4Oa1","object":"chat.completion.chunk","created":1707554908,"model":"gpt-4-1106-vision-preview","choices":[{"delta":{"content":" a"},"index":0,"finish_reason":null}]}
     *
     * data: {"id":"chatcmpl-8qdK8cCZwvpBDYU6hqcsd4Mzb4Oa1","object":"chat.completion.chunk","created":1707554908,"model":"gpt-4-1106-vision-preview","choices":[{"delta":{"content":" wooden"},"index":0,"finish_reason":null}]}
     *
     * data: {"id":"chatcmpl-8qdK8cCZwvpBDYU6hqcsd4Mzb4Oa1","object":"chat.completion.chunk","created":1707554908,"model":"gpt-4-1106-vision-preview","choices":[{"delta":{"content":" board"},"index":0,"finish_reason":null}]}
     *
     * data: {"id":"chatcmpl-8qdK8cCZwvpBDYU6hqcsd4Mzb4Oa1","object":"chat.completion.chunk","created":1707554908,"model":"gpt-4-1106-vision-preview","choices":[{"delta":{"content":"walk"},"index":0,"finish_reason":null}]}
     *
     * data: {"id":"chatcmpl-8qdK8cCZwvpBDYU6hqcsd4Mzb4Oa1","object":"chat.completion.chunk","created":1707554908,"model":"gpt-4-1106-vision-preview","choices":[{"delta":{"content":" path"},"index":0,"finish_reason":null}]}
     *
     * data: {"id":"chatcmpl-8qdK8cCZwvpBDYU6hqcsd4Mzb4Oa1","object":"chat.completion.chunk","created":1707554908,"model":"gpt-4-1106-vision-preview","choices":[{"delta":{"content":" extending"},"index":0,"finish_reason":null}]}
     *
     * data: {"id":"chatcmpl-8qdK8cCZwvpBDYU6hqcsd4Mzb4Oa1","object":"chat.completion.chunk","created":1707554908,"model":"gpt-4-1106-vision-preview","choices":[{"delta":{"content":" through"},"index":0,"finish_reason":null}]}
     *
     * data: {"id":"chatcmpl-8qdK8cCZwvpBDYU6hqcsd4Mzb4Oa1","object":"chat.completion.chunk","created":1707554908,"model":"gpt-4-1106-vision-preview","choices":[{"delta":{"content":" a"},"index":0,"finish_reason":null}]}
     *
     * data: {"id":"chatcmpl-8qdK8cCZwvpBDYU6hqcsd4Mzb4Oa1","object":"chat.completion.chunk","created":1707554908,"model":"gpt-4-1106-vision-preview","choices":[{"delta":{"content":" lush"},"index":0,"finish_reason":null}]}
     *
     * data: {"id":"chatcmpl-8qdK8cCZwvpBDYU6hqcsd4Mzb4Oa1","object":"chat.completion.chunk","created":1707554908,"model":"gpt-4-1106-vision-preview","choices":[{"delta":{"content":" green"},"index":0,"finish_reason":null}]}
     *
     * data: {"id":"chatcmpl-8qdK8cCZwvpBDYU6hqcsd4Mzb4Oa1","object":"chat.completion.chunk","created":1707554908,"model":"gpt-4-1106-vision-preview","choices":[{"delta":{"content":" grass"},"index":0,"finish_reason":null}]}
     *
     * data: {"id":"chatcmpl-8qdK8cCZwvpBDYU6hqcsd4Mzb4Oa1","object":"chat.completion.chunk","created":1707554908,"model":"gpt-4-1106-vision-preview","choices":[{"delta":{"content":"y"},"index":0,"finish_reason":null}]}
     *
     * data: {"id":"chatcmpl-8qdK8cCZwvpBDYU6hqcsd4Mzb4Oa1","object":"chat.completion.chunk","created":1707554908,"model":"gpt-4-1106-vision-preview","choices":[{"delta":{"content":" area"},"index":0,"finish_reason":null}]}
     *
     * data: {"id":"chatcmpl-8qdK8cCZwvpBDYU6hqcsd4Mzb4Oa1","object":"chat.completion.chunk","created":1707554908,"model":"gpt-4-1106-vision-preview","choices":[{"delta":{},"index":0,"finish_reason":"length"}]}
     *
     * data: [DONE]
     */

    @Disabled
    @Test
    fun `should return vision stream response`() = runBlocking {
        val openAI = OpenAI("api-key")
        val response = openAI.streamCompletions(
            ChatCompletionRequest(
                messages = listOf(
                    ChatMessage.vision(
                        listOf(
                            VisionMessageContent.TextContent(
                                content = "What’s in this image?"
                            ),
                            VisionMessageContent.ImageContent(
                                imageUrl = ImageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Gfp-wisconsin-madison-the-nature-boardwalk.jpg/2560px-Gfp-wisconsin-madison-the-nature-boardwalk.jpg")
                            )
                        )
                    )
                ),
                model = Model.GPT4_VISION_PREVIEW
            )
        ).toList().first().content()
        assertEquals("", response)
    }

    @Disabled
    @Test
    fun `should return vision response`() = runBlocking {
        val openAI = OpenAI("api-key")
        val response = openAI.completions(
            ChatCompletionRequest(
                messages = listOf(
                    ChatMessage.vision(
                        listOf(
                            VisionMessageContent.TextContent(
                                content = "What’s in this image?"
                            ),
                            VisionMessageContent.ImageContent(
                                imageUrl = ImageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Gfp-wisconsin-madison-the-nature-boardwalk.jpg/2560px-Gfp-wisconsin-madison-the-nature-boardwalk.jpg")
                            )
                        )
                    )
                ),
                model = Model.GPT4_VISION_PREVIEW
            )
        )
        assertEquals(
            "ChatCompletion(id=chatcmpl-8qdGJIu6ocz1pv5QaQ7sS3ltLxqqt, created=1707554671, model=gpt-4-1106-vision-preview, choices=[ChatChoice(index=0, message=AssistantMessage(content=This image shows a wooden boardwalk pathway extending through a green, grassy wet, role=ASSISTANT, toolCalls=null), finishReason=FinishReason(value=length))])",
            response
        )
    }
}