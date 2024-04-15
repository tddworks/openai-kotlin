Here's an example of the Kotlin API definition for the chat completion endpoint:

```kotlin
package com.tddworks.ollama.api.internal

import com.tddworks.ollama.api.internal.model.ChatRequest
import com.tddworks.ollama.api.internal.model.ChatResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatApi {
    @POST("/api/chat")
    suspend fun generateChat(
        @Body request: ChatRequest
    ): ChatResponse
}
```

The `ChatApi` interface defines a single method, `generateChat`, which takes a `ChatRequest` object as an input parameter and returns a `ChatResponse` object.

The `ChatRequest` class would look like this:

```kotlin
data class ChatRequest(
    @field:JsonProperty("model") val model: String,
    @field:JsonProperty("messages") val messages: List<Message>,
    @field:JsonProperty("format") val format: String? = null,
    @field:JsonProperty("options") val options: Map<String, Any>? = null,
    @field:JsonProperty("stream") val stream: Boolean? = null,
    @field:JsonProperty("keep_alive") val keepAlive: String? = null
)

data class Message(
    @field:JsonProperty("role") val role: String,
    @field:JsonProperty("content") val content: String,
    @field:JsonProperty("images") val images: List<String>? = null
)
```

The `ChatResponse` class would look like this:

```kotlin
data class ChatResponse(
    @field:JsonProperty("model") val model: String,
    @field:JsonProperty("created_at") val createdAt: String,
    @field:JsonProperty("message") val message: Message?,
    @field:JsonProperty("done") val done: Boolean?,
    @field:JsonProperty("total_duration") val totalDuration: Long?,
    @field:JsonProperty("load_duration") val loadDuration: Long?,
    @field:JsonProperty("prompt_eval_count") val promptEvalCount: Int?,
    @field:JsonProperty("prompt_eval_duration") val promptEvalDuration: Long?,
    @field:JsonProperty("eval_count") val evalCount: Int?,
    @field:JsonProperty("eval_duration") val evalDuration: Long?
)
```

This API definition assumes that you are using Retrofit for making the HTTP requests. You can then use the `ChatApi` interface in your Kotlin code to generate chat completions:

```kotlin
val chatApi = retrofit.create(ChatApi::class.java)

val request = ChatRequest(
    model = "llama2",
    messages = listOf(
        Message(
            role = "user",
            content = "why is the sky blue?"
        )
    )
)

val response = chatApi.generateChat(request)
```

The `generateChat` function will return a `ChatResponse` object, which you can then process and handle as needed in your application.