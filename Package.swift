// swift-tools-version:5.9
import PackageDescription

// BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)
let remoteKotlinUrl = "https://maven.pkg.github.com/tddworks/openai-kotlin/com/tddworks/openai-gateway-darwin-kmmbridge/0.2.0/openai-gateway-darwin-kmmbridge-0.2.0.zip"
let remoteKotlinChecksum = "083c6398bde8ed9549095c87f9ef6af32e5da70ec3a69d8d8af1e534b250972c"
let packageName = "openai-gateway-darwin"
// END KMMBRIDGE BLOCK

let package = Package(
    name: packageName,
    platforms: [
        
    ],
    products: [
        .library(
            name: packageName,
            targets: [packageName]
        ),
    ],
    targets: [
        .binaryTarget(
            name: packageName,
            url: remoteKotlinUrl,
            checksum: remoteKotlinChecksum
        )
        ,
    ]
)