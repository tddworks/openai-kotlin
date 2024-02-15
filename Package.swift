// swift-tools-version:5.3
import PackageDescription

// BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)
let remoteKotlinUrl = "https://maven.pkg.github.com/tddworks/openai-kotlin/com/tddworks/openai-client-darwin-kmmbridge/0.1.9/openai-client-darwin-kmmbridge-0.1.9.zip"
let remoteKotlinChecksum = "5aadf13605510291df0b18673834159713f40bcddc65f055a72ffb08a2f956ab"
let packageName = "openai-client-darwin"
// END KMMBRIDGE BLOCK

let package = Package(
    name: packageName,
    platforms: [
        .iOS(.v13)
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