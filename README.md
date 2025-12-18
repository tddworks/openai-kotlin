# OpenAI Kotlin

![CI](https://github.com/tddworks/openai-kotlin/actions/workflows/main.yml/badge.svg)
[![codecov](https://codecov.io/gh/tddworks/openai-kotlin/graph/badge.svg?token=ZHqC4RjnCf)](https://codecov.io/gh/tddworks/openai-kotlin)
[![Maven Central](https://img.shields.io/maven-central/v/com.tddworks/openai-client-core?label=Maven%20Central)](https://central.sonatype.com/artifact/com.tddworks/openai-client-core)
[![Kotlin](https://img.shields.io/badge/kotlin-2.2.21-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![KMP](https://img.shields.io/badge/Kotlin-Multiplatform-blue.svg?logo=kotlin)](https://kotlinlang.org/docs/multiplatform.html)
[![Ktor](https://img.shields.io/badge/ktor-3.3.3-blue.svg?logo=ktor)](https://ktor.io)
[![License](https://img.shields.io/github/license/tddworks/openai-kotlin?color=blue)](LICENSE)
[![GitHub Release](https://img.shields.io/github/v/release/tddworks/openai-kotlin?include_prereleases&label=Latest%20Release)](https://github.com/tddworks/openai-kotlin/releases)
[![Issues](https://img.shields.io/github/issues/tddworks/openai-kotlin?color=blue)](https://github.com/tddworks/openai-kotlin/issues)
[![Stars](https://img.shields.io/github/stars/tddworks/openai-kotlin?style=social)](https://github.com/tddworks/openai-kotlin/stargazers)

A comprehensive Kotlin Multiplatform library providing unified access to multiple AI/LLM providers including OpenAI, Anthropic Claude, Google Gemini, and Ollama. Built with modern Kotlin practices and designed for seamless integration across JVM, Android, iOS, and native platforms.

## ✨ Features

- 🔗 **Multi-Provider Support**: OpenAI, Anthropic Claude, Google Gemini, Ollama, and custom providers
- 🌐 **Kotlin Multiplatform**: JVM, Android, iOS, macOS, and other Kotlin/Native targets
- 🚀 **Streaming Support**: Real-time chat completions with Flow-based streaming
- 🔄 **Unified Gateway**: Switch between providers seamlessly with a single interface
- 📱 **Platform-Optimized**: Native HTTP clients for each platform (Ktor CIO, NSURLSession)
- 🎯 **Type-Safe**: Fully typed APIs with comprehensive data classes
- 📦 **Modular Design**: Use only what you need with granular dependencies
- 🧪 **Well-Tested**: Comprehensive test coverage with integration tests

## 🚀 Quick Start

### Basic OpenAI Client

Add the dependency:
```kotlin
implementation("com.tddworks:openai-client-jvm:0.2.3")
```

```kotlin
import com.tddworks.openai.api.OpenAI
import com.tddworks.openai.api.chat.api.*

// Simple - just provide your API key
val openAI = OpenAI.create(apiKey = "your-api-key")

// Or with custom base URL
val openAI = OpenAI.create(apiKey = "your-api-key", baseUrl = "https://custom.api.com")

// Dynamic configuration (values that may change at runtime)
val openAI = OpenAI.create(
    apiKey = { settings.apiKey },
    baseUrl = { settings.baseUrl }
)

// Chat completion
val response = openAI.chatCompletions(
    ChatCompletionRequest(
        messages = listOf(ChatMessage.UserMessage("Hello, world!")),
        model = Model.GPT_4O,
        maxTokens = 1000
    )
)

// Streaming chat completion
openAI.streamChatCompletions(
    ChatCompletionRequest(
        messages = listOf(ChatMessage.UserMessage("Tell me a story")),
        model = Model.GPT_4O
    )
).collect { chunk ->
    print(chunk.choices?.firstOrNull()?.delta?.content ?: "")
}
```

### Multi-Provider Gateway

For applications requiring multiple AI providers:

```kotlin
implementation("com.tddworks:openai-gateway-jvm:0.2.3")
```

```kotlin
import com.tddworks.openai.gateway.api.OpenAIGateway

// Simple - just provide your API keys
val gateway = OpenAIGateway.create(
    openAIKey = "openai-api-key",
    anthropicKey = "anthropic-api-key",
    geminiKey = "gemini-api-key"
)

// Dynamic configuration
val gateway = OpenAIGateway.create(
    openAIKey = { settings.openAIKey },
    anthropicKey = { settings.anthropicKey },
    geminiKey = { settings.geminiKey }
)

// Use any provider with the same interface
val response = gateway.chatCompletions(
    ChatCompletionRequest(
        messages = listOf(ChatMessage.UserMessage("Compare AI models")),
        model = Model("claude-3-sonnet") // or "llama2", "gpt-4", etc.
    )
)
```

## 🍎 Swift / iOS / macOS

### Installation via Swift Package Manager

Add the package to your `Package.swift` or via Xcode:

```swift
dependencies: [
    .package(url: "https://github.com/tddworks/openai-kotlin.git", branch: "main")
]
```

Or add via Xcode: File → Add Package Dependencies → Enter the repository URL.

### Available Frameworks

| Framework | Description |
|-----------|-------------|
| `OpenAIClient` | OpenAI API client |
| `AnthropicClient` | Anthropic Claude API client |
| `GeminiClient` | Google Gemini API client |
| `OllamaClient` | Ollama local LLM client |
| `OpenAIGateway` | Multi-provider unified gateway |

### Swift Usage Examples

#### OpenAI

```swift
import OpenAIClient

// Simple configuration
let client = OpenAICompanion.shared.create(apiKey: "your-api-key")

// With custom base URL
let client = OpenAICompanion.shared.create(apiKey: "your-api-key", baseUrl: "https://custom.api.com")

// Dynamic configuration (values that may change at runtime)
let client = OpenAICompanion.shared.create(
    apiKey: { Settings.shared.apiKey },
    baseUrl: { Settings.shared.baseUrl }
)
```

#### Anthropic Claude

```swift
import AnthropicClient

let client = AnthropicCompanion.shared.create(apiKey: "your-api-key")

// With custom configuration
let client = AnthropicCompanion.shared.create(
    apiKey: "your-api-key",
    baseUrl: "https://api.anthropic.com",
    anthropicVersion: "2023-06-01"
)
```

#### Google Gemini

```swift
import GeminiClient

let client = GeminiCompanion.shared.create(apiKey: "your-api-key")
```

#### Ollama (Local)

```swift
import OllamaClient

// Default localhost:11434
let client = OllamaCompanion.shared.create()

// Custom host
let client = OllamaCompanion.shared.create(baseUrl: "192.168.1.100", port: 11434)
```

#### Multi-Provider Gateway

```swift
import OpenAIGateway

let gateway = OpenAIGatewayCompanion.shared.create(
    openAIKey: "your-openai-key",
    anthropicKey: "your-anthropic-key",
    geminiKey: "your-gemini-key"
)

// Dynamic configuration
let gateway = OpenAIGatewayCompanion.shared.create(
    openAIKey: { Settings.shared.openAIKey },
    anthropicKey: { Settings.shared.anthropicKey },
    geminiKey: { Settings.shared.geminiKey }
)
```

### Swift Chat Example

```swift
import OpenAIClient

let openAI = OpenAI.shared.create(apiKey: "your-api-key")

// Chat completion
Task {
    let response = try await openAI.chatCompletions(
        request: ChatCompletionRequest(
            messages: [ChatMessage.UserMessage(content: "Hello!")],
            model: Model.gpt4o
        )
    )
    print(response.choices?.first?.message?.content ?? "")
}

// Streaming
for try await chunk in openAI.streamChatCompletions(request: request) {
    print(chunk.choices?.first?.delta?.content ?? "", terminator: "")
}
```

## 📦 Installation

### Gradle (Kotlin DSL)

For multiplatform projects:
```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("com.tddworks:openai-client-core:0.2.3")
            implementation("com.tddworks:openai-gateway-core:0.2.3")
        }
    }
}
```

For JVM/Android projects:
```kotlin
dependencies {
    implementation("com.tddworks:openai-client-jvm:0.2.3")
    implementation("com.tddworks:anthropic-client-jvm:0.2.3")
    implementation("com.tddworks:ollama-client-jvm:0.2.3")
    implementation("com.tddworks:gemini-client-jvm:0.2.3")
    implementation("com.tddworks:openai-gateway-jvm:0.2.3")
}
```

### Maven

```xml
<dependency>
    <groupId>com.tddworks</groupId>
    <artifactId>openai-client-jvm</artifactId>
    <version>0.2.3</version>
</dependency>
```

### Available Modules

| Module | Description | Platforms |
|--------|-------------|-----------|
| `openai-client-*` | OpenAI API client (chat, images, completions) | JVM, iOS, macOS |
| `anthropic-client-*` | Anthropic Claude API client | JVM, iOS, macOS |
| `ollama-client-*` | Ollama local LLM client | JVM, iOS, macOS |
| `gemini-client-*` | Google Gemini API client | JVM, iOS, macOS |
| `openai-gateway-*` | Multi-provider gateway | JVM, iOS, macOS |
| `common` | Shared networking utilities | All platforms |

## 💡 Usage Examples

### Image Generation

```kotlin
val images = openAI.images(
    ImageCreate(
        prompt = "A beautiful sunset over mountains",
        size = Size.SIZE_1024x1024,
        quality = Quality.HD,
        n = 1
    )
)
```

### Vision (Image Analysis)

```kotlin
val response = openAI.chatCompletions(
    ChatCompletionRequest(
        messages = listOf(
            ChatMessage.UserMessage(
                content = listOf(
                    VisionMessageContent.TextContent("What's in this image?"),
                    VisionMessageContent.ImageContent(
                        imageUrl = ImageUrl("data:image/jpeg;base64,${base64Image}")
                    )
                )
            )
        ),
        model = Model.GPT_4_VISION,
        maxTokens = 1000
    )
)
```

### Anthropic Claude

```kotlin
import com.tddworks.anthropic.api.Anthropic

val claude = Anthropic.create(apiKey = "your-anthropic-key")

val message = claude.messages(
    CreateMessageRequest(
        messages = listOf(
            Message(
                role = Role.USER,
                content = listOf(ContentMessage.TextContent("Explain quantum computing"))
            )
        ),
        model = AnthropicModel.CLAUDE_3_SONNET,
        maxTokens = 1000
    )
)
```

### Local Ollama

```kotlin
import com.tddworks.ollama.api.Ollama

// Default localhost:11434
val ollama = Ollama.create()

// Or custom host
val ollama = Ollama.create(baseUrl = "192.168.1.100", port = 11434)

val response = ollama.chat(
    OllamaChatRequest(
        model = OllamaModel.LLAMA2.value,
        messages = listOf(
            OllamaChatMessage(
                role = "user",
                content = "What is the capital of France?"
            )
        )
    )
)
```

## 🏗️ Architecture

This library follows a clean, modular architecture:

```
┌─────────────────────┐
│   Applications      │ (Your Kotlin/Java/Swift apps)
├─────────────────────┤
│   OpenAI Gateway    │ (Unified interface for all providers)
├─────────────────────┤
│   Provider Clients  │ (OpenAI, Anthropic, Ollama, Gemini)
├─────────────────────┤
│   Common Networking │ (HTTP abstraction, serialization)
└─────────────────────┘
```

### Core Components

- **HttpRequester**: Cross-platform HTTP client abstraction using Ktor
- **Provider Configs**: Type-safe configuration for each AI provider
- **Streaming Support**: Flow-based streaming for real-time responses
- **Error Handling**: Comprehensive exception hierarchy with detailed error information
- **Dependency Injection**: Koin-based DI for clean separation of concerns

## 🌍 Platform Support

### Supported Platforms

- ✅ **JVM** (Java 8+, Android API 21+)
- ✅ **iOS** (iOS 14+)
- ✅ **macOS** (macOS 11+)
- 🚧 **watchOS** (planned)
- 🚧 **tvOS** (planned)
- 🚧 **Linux** (planned)
- 🚧 **Windows** (planned)

### Platform-Specific Features

| Platform | HTTP Client | Streaming | Local Storage |
|----------|-------------|-----------|---------------|
| JVM      | Ktor CIO    | ✅        | File System  |
| Android  | Ktor CIO    | ✅        | File System  |
| iOS      | NSURLSession| ✅        | UserDefaults |
| macOS    | NSURLSession| ✅        | UserDefaults |

## 🔧 Configuration

### Environment Variables

Set these environment variables or provide them programmatically:

```bash
OPENAI_API_KEY=your-openai-key
ANTHROPIC_API_KEY=your-anthropic-key
GEMINI_API_KEY=your-gemini-key
```

### Configuration Examples

#### OpenAI with Custom Base URL

```kotlin
val openAI = OpenAI.create(
    apiKey = System.getenv("OPENAI_API_KEY"),
    baseUrl = "https://api.openai.com/v1"
)

// Or with dynamic configuration
val openAI = OpenAI.create(
    apiKey = { System.getenv("OPENAI_API_KEY") },
    baseUrl = { settings.baseUrl }
)
```

#### Anthropic with Custom Version

```kotlin
val anthropic = Anthropic.create(
    apiKey = System.getenv("ANTHROPIC_API_KEY"),
    baseUrl = "https://api.anthropic.com",
    anthropicVersion = "2023-06-01"
)
```

## 🧪 Testing

### Unit Tests
```bash
./gradlew test
```

### Integration Tests
```bash
./gradlew integrationTest
```

### Code Coverage
```bash
./gradlew koverHtmlReport
open build/reports/kover/html/index.html
```

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guidelines](CONTRIBUTING.md) for details.

### Development Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/tddworks/openai-kotlin.git
   cd openai-kotlin
   ```

2. **Build the project**:
   ```bash
   ./gradlew build
   ```

3. **Run tests**:
   ```bash
   ./gradlew allTests
   ```

4. **Format code**:
   ```bash
   ./gradlew spotlessApply
   ```

### Code Style

This project uses [Spotless](https://github.com/diffplug/spotless) for code formatting. Please run `./gradlew spotlessApply` before submitting PRs.

## 📄 License

```
Copyright 2024 TDD Works

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## 🙋 Support

- 📖 [Documentation](https://tddworks.github.io/openai-kotlin)
- 💬 [GitHub Discussions](https://github.com/tddworks/openai-kotlin/discussions)
- 🐛 [Issue Tracker](https://github.com/tddworks/openai-kotlin/issues)
- 📧 Email: support@tddworks.com

## 🌟 Acknowledgments

- [OpenAI](https://openai.com) for their powerful APIs
- [Anthropic](https://anthropic.com) for Claude AI
- [Ollama](https://ollama.ai) for local LLM support  
- [Google](https://ai.google.dev) for Gemini API
- [JetBrains](https://jetbrains.com) for Kotlin Multiplatform
- [Ktor](https://ktor.io) for cross-platform HTTP client

---

Made with ❤️ by [TDD Works](https://github.com/tddworks)