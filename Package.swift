// swift-tools-version:5.3
import PackageDescription

// BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)
let remoteKotlinUrl = "https://maven.pkg.github.com/tddworks/openai-kotlin/com/tddworks/openai-client-darwin-kmmbridge/0.1.1/openai-client-darwin-kmmbridge-0.1.1.zip"
let remoteKotlinChecksum = "eeea4c945fc4cdde8e7d1e0dafd70ef771cd16dc4353aee16cb5b8d63cd7a8d8"
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