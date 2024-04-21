// swift-tools-version:5.9
import PackageDescription

// BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)
let remoteKotlinUrl = "https://maven.pkg.github.com/tddworks/openai-kotlin/com/tddworks/openai-gateway-darwin-kmmbridge/0.2.1/openai-gateway-darwin-kmmbridge-0.2.1.zip"
let remoteKotlinChecksum = "d8efca1f526fca7239d49c8969cd7392dcdf9c94e11464257fe563091cafe2ab"
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