![CI](https://github.com/tddworks/openai-kotlin/actions/workflows/main.yml/badge.svg)
[![codecov](https://codecov.io/gh/tddworks/openai-kotlin/graph/badge.svg?token=ZHqC4RjnCf)](https://codecov.io/gh/tddworks/openai-kotlin)
[![Kotlin](https://img.shields.io/badge/kotlin-2.0.0-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Maven Central](https://img.shields.io/maven-central/v/com.tddworks/openai-client-core/0.2.1)](https://central.sonatype.com/artifact/com.tddworks/openai-client-jvm)


# openai-kotlin powered by kotlin multiplatform

**Getting Started:**

To get started, simply add the following dependency to your Kotlin project:

**OpenAI API**

```kotlin
implementation("com.tddworks:openai-client-jvm:0.2.1")
```
**Then, configure the OpenAI with your API keys and settings:**
 - Default values are provided for the baseUrl, but you can override them with your own values.
 - OpenAI
   - default baseUrl is `api.openai.com`

**Example:**
```kotlin
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.ChatMessage
import com.tddworks.openai.api.chat.api.Model
import com.tddworks.openai.di.initOpenAI

val openAI = initOpenAI(OpenAIConfig(
   baseUrl = { "YOUR_BASE_URL" },
   apiKey = { "YOUR_API_KEY" }
))

// stream completions
openAI.streamChatCompletions(
   ChatCompletionRequest(
      messages = listOf(ChatMessage.UserMessage("hello")),
      maxTokens = 1024,
      model = Model.GPT_3_5_TURBO
   )
).collect {
   println(it)
}

// chat completions
val chatCompletion = openAI.chatCompletions(
   ChatCompletionRequest(
      messages = listOf(ChatMessage.UserMessage("hello")),
      maxTokens = 1024,
      model = Model.GPT_3_5_TURBO
   )
)

// completions(legacy)
val completion = openAI.completions(
   CompletionRequest(
      prompt = "Once upon a time",
      suffix = "The end",
      maxTokens = 10,
      temperature = 0.5
   )
)
```



**OpenAI Gateway**

```kotlin
implementation("com.tddworks:openai-gateway-jvm:0.2.1")
```

**Then, configure the OpenAIGateway with your API keys and settings:**
 - Default values are provided for the baseUrl, but you can override them with your own values.
 - OpenAI
   - default baseUrl is `api.openai.com`
 - Anthropic 
   - default baseUrl is `api.anthropic.com`
   - default anthropicVersion is `2023-06-01`
 - Ollama
   - default baseUrl is `localhost`
   - default protocol is `http`
   - default port is `11434`
 
**Example:**
```kotlin
import com.tddworks.anthropic.api.AnthropicConfig
import com.tddworks.ollama.api.OllamaConfig
import com.tddworks.ollama.api.OllamaModel
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.OpenAIConfig
import com.tddworks.openai.api.chat.api.ChatMessage
import com.tddworks.openai.api.chat.api.Model
import com.tddworks.openai.gateway.api.OpenAIGateway
import com.tddworks.openai.gateway.di.initOpenAIGateway

val openAIGateway = initOpenAIGateway(
   OpenAIConfig(
      baseUrl = { "YOUR_OPENAI_BASE_URL" },
      apiKey = { "YOUR_OPENAI_API_KEY" }
   ),
   AnthropicConfig(
      baseUrl = { "YOUR_ANTHROPIC_BASE_URL" },
      apiKey = { "YOUR_ANTHROPIC_API_KEY" },
      anthropicVersion = { "YOUR_ANTHROPIC_VERSION" }
   ),
   OllamaConfig(
      baseUrl = { "YOUR_OLLAMA_BASE_URL" },
      protocol = { "YOUR_OLLAMA_PROTOCOL" },
      port = { "YOUR_OLLAMA_PORT" }
   )
)

// stream completions
openAIGateway.streamChatCompletions(
   ChatCompletionRequest(
      messages = listOf(ChatMessage.UserMessage("hello")),
      maxTokens = 1024,
      model = Model(OllamaModel.LLAMA2.value)
   )
).collect {
   println(it)
}

// chat completions
val chatCompletion = openAIGateway.chatCompletions(
   ChatCompletionRequest(
      messages = listOf(ChatMessage.UserMessage("hello")),
      maxTokens = 1024,
      model = Model(Model.GPT_3_5_TURBO.value)
   )
)

// completions(legacy)
val completion = openAIGateway.completions(
   CompletionRequest(
        prompt = "Once upon a time",
         suffix = "The end",
         maxTokens = 10,
         temperature = 0.5
   )
)

```