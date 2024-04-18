![CI](https://github.com/tddworks/openai-kotlin/actions/workflows/main.yml/badge.svg)
[![codecov](https://codecov.io/gh/tddworks/openai-kotlin/graph/badge.svg?token=ZHqC4RjnCf)](https://codecov.io/gh/tddworks/openai-kotlin)

# openai-kotlin powered by kotlin multiplatform

**Getting Started:**

To get started, simply add the following dependency to your Kotlin project:

```kotlin
implementation("com.tddworks:openai-gateway-jvm:0.1.2")
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
import com.tddworks.anthropic.api.messages.api.AnthropicConfig
import com.tddworks.openai.api.OpenAIConfig
import com.tddworks.openai.gateway.api.OpenAIGateway
import com.tddworks.openai.gateway.di.initOpenAIGatewayKoin

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
openAIGateway.streamCompletions(
   OpenAIChatCompletionRequest(
      messages = listOf(ChatMessage.UserMessage("hello")),
      maxTokens = 1024,
      model = OpenAIModel(OllamaModel.LLAMA2.value)
   )
).collect {
   println(it)
}

// chat completions
val chatCompletion = gateway.completions(
   OpenAIChatCompletionRequest(
      messages = listOf(ChatMessage.UserMessage("hello")),
      maxTokens = 1024,
      model = OpenAIModel(Model.GPT_3_5_TURBO.value)
   )
)
```