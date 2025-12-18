// swift-tools-version:6.2
import PackageDescription

// BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'OpenAIClient' (do not edit)
let remoteOpenAIClientUrl = "https://api.github.com/repos/tddworks/openai-kotlin/releases/assets/330053145.zip"
let remoteOpenAIClientChecksum = "fca94abb06405cd59bcd554be13d4132d4d5ca435a689d09fd5ea5c3c662cc93"
let openAIClientPackageName = "OpenAIClient"
// END KMMBRIDGE BLOCK FOR 'OpenAIClient'

// BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'AnthropicClient' (do not edit)
let remoteAnthropicClientUrl = "https://api.github.com/repos/tddworks/openai-kotlin/releases/assets/330054122.zip"
let remoteAnthropicClientChecksum = "6f8fc328c7e2ddbd18210204bca66a11e1eb093271152be71ac785dd24cca396"
let anthropicClientPackageName = "AnthropicClient"
// END KMMBRIDGE BLOCK FOR 'AnthropicClient'

// BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'GeminiClient' (do not edit)
let remoteGeminiClientUrl = "https://api.github.com/repos/tddworks/openai-kotlin/releases/assets/330055475.zip"
let remoteGeminiClientChecksum = "0edb298a982adc7a11cdac68e9ee5b01632e8189aca8c2ffa002ca04f99a4059"
let geminiClientPackageName = "GeminiClient"
// END KMMBRIDGE BLOCK FOR 'GeminiClient'

// BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'OllamaClient' (do not edit)
let remoteOllamaClientUrl = "https://api.github.com/repos/tddworks/openai-kotlin/releases/assets/330056394.zip"
let remoteOllamaClientChecksum = "40634a40c992c491f7e39cee8541de57583ac8cf54f4ff03d207ca41e1910601"
let ollamaClientPackageName = "OllamaClient"
// END KMMBRIDGE BLOCK FOR 'OllamaClient'

// BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'OpenAIGateway' (do not edit)
let remoteOpenAIGatewayUrl = "https://api.github.com/repos/tddworks/openai-kotlin/releases/assets/330057180.zip"
let remoteOpenAIGatewayChecksum = "399e3320723cacdff0b881b68866aa88515ad873e7e163e22bb0a0d81bba4bc8"
let openAIGatewayPackageName = "OpenAIGateway"
// END KMMBRIDGE BLOCK FOR 'OpenAIGateway'

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
