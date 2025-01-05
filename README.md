![CI](https://github.com/tddworks/openai-kotlin/actions/workflows/main.yml/badge.svg)
[![codecov](https://codecov.io/gh/tddworks/openai-kotlin/graph/badge.svg?token=ZHqC4RjnCf)](https://codecov.io/gh/tddworks/openai-kotlin)
[![Kotlin](https://img.shields.io/badge/kotlin-2.0.0-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Maven Central](https://img.shields.io/maven-central/v/com.tddworks/openai-client-core/0.2.2)](https://central.sonatype.com/artifact/com.tddworks/openai-client-jvm)


# openai-kotlin powered by kotlin multiplatform

**Getting Started:**

To get started, simply add the following dependency to your Kotlin project:

**OpenAI API**

```kotlin
implementation("com.tddworks:openai-client-jvm:0.2.2")
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
implementation("com.tddworks:openai-gateway-jvm:0.2.2")
```

**Then, configure the OpenAIGateway with your API keys and settings:**
 - Default values are provided for the baseUrl, but you can override them with your own values.
 - OpenAI
   - default baseUrl is `https://api.openai.com`
 - Anthropic 
   - default baseUrl is `https://api.anthropic.com`
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


initOpenAIGateway(
   DefaultOpenAIProviderConfig(
      baseUrl = { "YOUR_OPENAI_BASE_URL" }, // Replace with the base URL for the OpenAI API
      apiKey = { "YOUR_OPENAI_API_KEY" } // Replace with your OpenAI API key
   ),
   AnthropicOpenAIProviderConfig(
      baseUrl = { "YOUR_ANTHROPIC_BASE_URL" }, // Replace with the base URL for the Anthropic service
      apiKey = { "YOUR_ANTHROPIC_API_KEY" }, // Replace with your Anthropic API key
      anthropicVersion = { "ANTHROPIC_API_VERSION" } // Replace with the version of Anthropic API you want to use
   ),
   OllamaOpenAIProviderConfig(
      protocol = { "PROTOCOL" }, // Replace with the protocol (e.g., 'http' or 'https')
      baseUrl = { "YOUR_OLLAMA_BASE_URL" }, // Replace with the base URL for the Ollama service
      port = { "PORT_NUMBER" } // Replace with the port number if required
   ),
   GeminiOpenAIProviderConfig(
      baseUrl = { "YOUR_GEMINI_BASE_URL" }, // Replace with the base URL for the Gemini service
      apiKey = { "YOUR_GEMINI_API_KEY" } // Replace with your Gemini API key
   )
).apply {
   addProvider(
      DefaultOpenAIProvider(
         config = DefaultOpenAIProviderConfig(
            baseUrl = { "YOUR_MOONSHOT_BASE_URL" }, // Replace with the base URL for the Moonshot service
            apiKey = { "YOUR_MOONSHOT_API_KEY" } // Replace with your Moonshot API key
         ),
         models = moonshotmodels.map { // Replace with the models you wish to use with the Moonshot service
            OpenAIModel(it.name) // Define the models you wish to use with the Moonshot service
         }
      )
   )

   addProvider(
      DefaultOpenAIProvider(
         config = DefaultOpenAIProviderConfig(
            baseUrl = { "YOUR_DEEPSEEK_BASE_URL" }, // Replace with the base URL for the Deepseek service
            apiKey = { "YOUR_DEEPSEEK_API_KEY" } // Replace with your Deepseek API key
         ),
         models = deepseekModels.map { // Replace with the models you wish to use with the Deepseek service
            OpenAIModel(it.name) // Define the models you wish to use with the Deepseek service
         }
      )
   )
}.also {
   Logger.d("OpenAI Gateway initialized") // Log the initialization of the OpenAI Gateway
}


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