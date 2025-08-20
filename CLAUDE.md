# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build and Development Commands

### Building the Project
```bash
./gradlew build                 # Build all modules
./gradlew clean build           # Clean and build all modules
./gradlew assemble             # Assemble outputs without running tests
```

### Running Tests
```bash
./gradlew test                  # Run tests for all platforms
./gradlew jvmTest              # Run JVM tests only
./gradlew macosArm64Test      # Run macOS ARM64 tests
./gradlew iosSimulatorArm64Test # Run iOS simulator tests
./gradlew allTests             # Run tests for all targets with aggregated report
./gradlew check                # Run all verification tasks
```

### Test Coverage
```bash
./gradlew koverHtmlReport      # Generate HTML coverage report for all code
./gradlew koverXmlReport       # Generate XML coverage report
./gradlew koverVerify          # Run coverage verification (min 86% required)
```

### Running Specific Module Tests
```bash
./gradlew :openai-client:openai-client-core:test
./gradlew :anthropic-client:anthropic-client-core:test
./gradlew :ollama-client:ollama-client-core:test
./gradlew :gemini-client:gemini-client-core:test
./gradlew :openai-gateway:openai-gateway-core:test
```

## Project Architecture

This is a Kotlin Multiplatform project providing AI/LLM client implementations for multiple providers. The codebase follows a modular architecture with clear separation of concerns.

### Core Architecture Patterns

1. **Multiplatform Structure**: Each client module has platform-specific implementations:
   - `-core`: Common implementation shared across platforms
   - `-darwin`: Apple platform specific implementations (iOS, macOS)
   - `-cio`: JVM-specific implementations using CIO

2. **Provider Pattern**: The `openai-gateway` module implements a gateway pattern that allows switching between different LLM providers (OpenAI, Anthropic, Ollama, Gemini) using a unified interface.

3. **HTTP Client Abstraction**: The `common` module provides a shared `HttpRequester` interface that abstracts HTTP operations across platforms, using Ktor under the hood.

4. **Dependency Injection**: Uses Koin for dependency injection across the codebase, with platform-specific configurations.

### Module Structure

- **common/**: Shared networking and utility code
  - HTTP client abstraction (`HttpRequester`)
  - Ktor configuration for different platforms
  - JSON serialization utilities

- **openai-client/**: OpenAI API client implementation
  - Chat completions, images, and legacy completions APIs
  - Streaming support for chat completions

- **anthropic-client/**: Anthropic Claude API client
  - Messages API implementation
  - Image support with base64 encoding

- **ollama-client/**: Ollama local LLM client
  - Chat and generate endpoints
  - Local model management

- **gemini-client/**: Google Gemini API client
  - Text generation with multimodal support

- **openai-gateway/**: Unified gateway for all providers
  - Provider abstraction allowing runtime switching
  - Adapter pattern to convert between provider-specific and OpenAI formats
  - Extensions for converting requests/responses between different provider formats

### Key Interfaces

- `OpenAI`: Main interface for OpenAI operations (Chat, Images, Completions)
- `OpenAIGateway`: Gateway interface for multi-provider support
- `HttpRequester`: HTTP client abstraction for cross-platform requests
- `OpenAIProvider`: Provider interface for different LLM services

### Configuration

Each client uses a configuration pattern (e.g., `OpenAIConfig`, `AnthropicConfig`) that requires:
- Base URL (with defaults for each provider)
- API key
- Optional provider-specific settings

### Testing Strategy

The project uses:
- Unit tests with mocked HTTP clients (`MockHttpClient`)
- Integration tests (files ending with `ITest`) for actual API calls
- Platform-specific test configurations
- Kover for code coverage with 86% minimum threshold