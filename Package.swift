// swift-tools-version:5.3
import PackageDescription

// BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)
let remoteKotlinUrl = "https://maven.pkg.github.com/tddworks/openai-kotlin/com/tddworks/openai-client-darwin-kmmbridge/0.1.2/openai-client-darwin-kmmbridge-0.1.2.zip"
let remoteKotlinChecksum = "cc5c158b471f43eed49be5705f7025d0cd569761da05df319f2ad4b8919f7266"
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