// swift-tools-version:6.2
import PackageDescription

// BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'OpenAIClient' (do not edit)
let remoteOpenAIClientUrl = ""
let remoteOpenAIClientChecksum = ""
let openAIClientPackageName = "OpenAIClient"
// END KMMBRIDGE VARIABLES BLOCK FOR 'OpenAIClient'

// BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'AnthropicClient' (do not edit)
let remoteAnthropicClientUrl = ""
let remoteAnthropicClientChecksum = ""
let anthropicClientPackageName = "AnthropicClient"
// END KMMBRIDGE VARIABLES BLOCK FOR 'AnthropicClient'

// BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'GeminiClient' (do not edit)
let remoteGeminiClientUrl = ""
let remoteGeminiClientChecksum = ""
let geminiClientPackageName = "GeminiClient"
// END KMMBRIDGE VARIABLES BLOCK FOR 'GeminiClient'

// BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'OllamaClient' (do not edit)
let remoteOllamaClientUrl = ""
let remoteOllamaClientChecksum = ""
let ollamaClientPackageName = "OllamaClient"
// END KMMBRIDGE VARIABLES BLOCK FOR 'OllamaClient'

// BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'OpenAIGateway' (do not edit)
let remoteOpenAIGatewayUrl = ""
let remoteOpenAIGatewayChecksum = ""
let openAIGatewayPackageName = "OpenAIGateway"
// END KMMBRIDGE VARIABLES BLOCK FOR 'OpenAIGateway'

let package = Package(
    name: "openai-kotlin",
    platforms: [
        .iOS(.v15),
        .macOS(.v15)
    ],
    products: [
        .library(name: openAIClientPackageName, targets: [openAIClientPackageName]),
        .library(name: anthropicClientPackageName, targets: [anthropicClientPackageName]),
        .library(name: geminiClientPackageName, targets: [geminiClientPackageName]),
        .library(name: ollamaClientPackageName, targets: [ollamaClientPackageName]),
        .library(name: openAIGatewayPackageName, targets: [openAIGatewayPackageName]),
    ],
    targets: [
        // Local development targets (using local XCFrameworks)
        .binaryTarget(
            name: openAIClientPackageName,
            path: "./openai-client/openai-client-darwin/build/XCFrameworks/release/\(openAIClientPackageName).xcframework"
        ),
        .binaryTarget(
            name: anthropicClientPackageName,
            path: "./anthropic-client/anthropic-client-darwin/build/XCFrameworks/release/\(anthropicClientPackageName).xcframework"
        ),
        .binaryTarget(
            name: geminiClientPackageName,
            path: "./gemini-client/gemini-client-darwin/build/XCFrameworks/release/\(geminiClientPackageName).xcframework"
        ),
        .binaryTarget(
            name: ollamaClientPackageName,
            path: "./ollama-client/ollama-client-darwin/build/XCFrameworks/release/\(ollamaClientPackageName).xcframework"
        ),
        .binaryTarget(
            name: openAIGatewayPackageName,
            path: "./openai-gateway/openai-gateway-darwin/build/XCFrameworks/release/\(openAIGatewayPackageName).xcframework"
        ),
        // Remote targets (uncomment when publishing)
        // .binaryTarget(name: openAIClientPackageName, url: remoteOpenAIClientUrl, checksum: remoteOpenAIClientChecksum),
        // .binaryTarget(name: anthropicClientPackageName, url: remoteAnthropicClientUrl, checksum: remoteAnthropicClientChecksum),
        // .binaryTarget(name: geminiClientPackageName, url: remoteGeminiClientUrl, checksum: remoteGeminiClientChecksum),
        // .binaryTarget(name: ollamaClientPackageName, url: remoteOllamaClientUrl, checksum: remoteOllamaClientChecksum),
        // .binaryTarget(name: openAIGatewayPackageName, url: remoteOpenAIGatewayUrl, checksum: remoteOpenAIGatewayChecksum),
    ]
)
