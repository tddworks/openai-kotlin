![CI](https://github.com/tddworks/openai-kotlin/actions/workflows/main.yml/badge.svg)
[![codecov](https://codecov.io/gh/tddworks/openai-kotlin/graph/badge.svg?token=ZHqC4RjnCf)](https://codecov.io/gh/tddworks/openai-kotlin)
[![official project](http://jb.gg/badges/official.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)

# openai-kotlin powered by kotlin multiplatform

**Getting Started:**

To get started, simply add the following dependency to your Kotlin project:

```kotlin
implementation("com.tddworks:openai-gateway-jvm:0.1.2")
```

**Then, configure the OpenAIGateway with your API keys and settings:**
 - Default values are provided for the baseUrl and apiKey, but you can override them with your own values.
 - OpenAI
   - default baseUrl is `api.openai.com`
 - Anthropic 
   - default baseUrl is `api.anthropic.com`
   - default anthropicVersion is `2023-06-01`

**Example:**
```kotlin
import com.tddworks.anthropic.api.messages.api.AnthropicConfig
import com.tddworks.openai.api.OpenAIConfig
import com.tddworks.openai.gateway.api.OpenAIGateway
import com.tddworks.openai.gateway.di.initOpenAIGatewayKoin

openAIGateway = initOpenAIGatewayKoin(
    OpenAIConfig(
        baseUrl = { "YOUR_OPENAI_BASE_URL" },
        apiKey = { "YOUR_OPENAI_API_KEY" }
    ),
    AnthropicConfig(
        baseUrl = { "YOUR_ANTHROPIC_BASE_URL" },
        apiKey = { "YOUR_ANTHROPIC_API_KEY" },
        anthropicVersion = { "YOUR_ANTHROPIC_VERSION" }
    )
).koin.get<OpenAIGateway>()
```