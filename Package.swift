// swift-tools-version:6.2
import PackageDescription

// BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'OpenAIClient' (do not edit)
let remoteOpenAIClientUrl = "https://api.github.com/repos/tddworks/openai-kotlin/releases/assets/330106385.zip"
let remoteOpenAIClientChecksum = "cf581b8a05b2211883952ff848d2734c767543df00c4d86f121e0cc5ffd64d94"
let openAIClientPackageName = "OpenAIClient"
// END KMMBRIDGE BLOCK FOR 'OpenAIClient'

// BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'AnthropicClient' (do not edit)
let remoteAnthropicClientUrl = "https://api.github.com/repos/tddworks/openai-kotlin/releases/assets/330107349.zip"
let remoteAnthropicClientChecksum = "c57ecbb4c12e7d3e5dbb0505fc5ed7880b9dcc68e2c29842e61b2f66fa9e6bcb"
let anthropicClientPackageName = "AnthropicClient"
// END KMMBRIDGE BLOCK FOR 'AnthropicClient'

// BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'GeminiClient' (do not edit)
let remoteGeminiClientUrl = "https://api.github.com/repos/tddworks/openai-kotlin/releases/assets/330108062.zip"
let remoteGeminiClientChecksum = "f2982ec6362b60bc1ea8b33edf15e12ec229c458671215dd9d3bc9a96520e8fe"
let geminiClientPackageName = "GeminiClient"
// END KMMBRIDGE BLOCK FOR 'GeminiClient'

// BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'OllamaClient' (do not edit)
let remoteOllamaClientUrl = "https://api.github.com/repos/tddworks/openai-kotlin/releases/assets/330108600.zip"
let remoteOllamaClientChecksum = "9fac5388fbdbbe316a463f9d63038121d982e689fea8de2a7bf21419532f2e67"
let ollamaClientPackageName = "OllamaClient"
// END KMMBRIDGE BLOCK FOR 'OllamaClient'

// BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'OpenAIGateway' (do not edit)
let remoteOpenAIGatewayUrl = "https://api.github.com/repos/tddworks/openai-kotlin/releases/assets/330109753.zip"
let remoteOpenAIGatewayChecksum = "8931a4d77c7e6a240278059f42c40ae5beac1d7c12edf2ab240c028cbb13dae3"
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
//        .binaryTarget(
//            name: openAIClientPackageName,
//            path: "./openai-client/openai-client-darwin/build/XCFrameworks/release/\(openAIClientPackageName).xcframework"
//        ),
//        .binaryTarget(
//            name: anthropicClientPackageName,
//            path: "./anthropic-client/anthropic-client-darwin/build/XCFrameworks/release/\(anthropicClientPackageName).xcframework"
//        ),
//        .binaryTarget(
//            name: geminiClientPackageName,
//            path: "./gemini-client/gemini-client-darwin/build/XCFrameworks/release/\(geminiClientPackageName).xcframework"
//        ),
//        .binaryTarget(
//            name: ollamaClientPackageName,
//            path: "./ollama-client/ollama-client-darwin/build/XCFrameworks/release/\(ollamaClientPackageName).xcframework"
//        ),
//        .binaryTarget(
//            name: openAIGatewayPackageName,
//            path: "./openai-gateway/openai-gateway-darwin/build/XCFrameworks/release/\(openAIGatewayPackageName).xcframework"
//        ),
        // Remote targets (uncomment when publishing)
         .binaryTarget(name: openAIClientPackageName, url: remoteOpenAIClientUrl, checksum: remoteOpenAIClientChecksum),
         .binaryTarget(name: anthropicClientPackageName, url: remoteAnthropicClientUrl, checksum: remoteAnthropicClientChecksum),
         .binaryTarget(name: geminiClientPackageName, url: remoteGeminiClientUrl, checksum: remoteGeminiClientChecksum),
         .binaryTarget(name: ollamaClientPackageName, url: remoteOllamaClientUrl, checksum: remoteOllamaClientChecksum),
         .binaryTarget(name: openAIGatewayPackageName, url: remoteOpenAIGatewayUrl, checksum: remoteOpenAIGatewayChecksum),
    ]
)
