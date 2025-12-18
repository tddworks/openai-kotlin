// swift-tools-version:6.2
import PackageDescription

// BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'OpenAIClient' (do not edit)
let remoteOpenAIClientUrl = "https://github.com/tddworks/openai-kotlin/releases/download/0.2.3/OpenAIClient.xcframework.zip"
let remoteOpenAIClientChecksum = "707f67cc87d4bd2fed15816ab1ae5a09f056608ce6002927a191cdd38811a5c6"
let openAIClientPackageName = "OpenAIClient"
// END KMMBRIDGE BLOCK FOR 'OpenAIClient'

// BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'AnthropicClient' (do not edit)
let remoteAnthropicClientUrl = "https://github.com/tddworks/openai-kotlin/releases/download/0.2.3/AnthropicClient.xcframework.zip"
let remoteAnthropicClientChecksum = "26ae52d78bb9875e4fe4bbaac7be7993a653857c8454eb82e60e2ff20b818dcb"
let anthropicClientPackageName = "AnthropicClient"
// END KMMBRIDGE BLOCK FOR 'AnthropicClient'

// BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'GeminiClient' (do not edit)
let remoteGeminiClientUrl = "https://github.com/tddworks/openai-kotlin/releases/download/0.2.3/GeminiClient.xcframework.zip"
let remoteGeminiClientChecksum = "7dfc3a205e79f3d605dc2f1a60748b7691ca98830c07be39bd96f746d8a9a596"
let geminiClientPackageName = "GeminiClient"
// END KMMBRIDGE BLOCK FOR 'GeminiClient'

// BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'OllamaClient' (do not edit)
let remoteOllamaClientUrl = "https://github.com/tddworks/openai-kotlin/releases/download/0.2.3/OllamaClient.xcframework.zip"
let remoteOllamaClientChecksum = "dbc2800796452c73197d9355ee2e099e58d5464347c17da87db53136f56a43e2"
let ollamaClientPackageName = "OllamaClient"
// END KMMBRIDGE BLOCK FOR 'OllamaClient'

// BEGIN KMMBRIDGE VARIABLES BLOCK FOR 'OpenAIGateway' (do not edit)
let remoteOpenAIGatewayUrl = "https://github.com/tddworks/openai-kotlin/releases/download/0.2.3/OpenAIGateway.xcframework.zip"
let remoteOpenAIGatewayChecksum = "89b8c18bbccf5cbc3f2b180343a54e1d2d11166aec2007d134f754a399891da6"
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
