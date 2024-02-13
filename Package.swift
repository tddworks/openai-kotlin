// swift-tools-version:5.3
import PackageDescription

// BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)
let remoteKotlinUrl = "https://maven.pkg.github.com/tddworks/openai-kotlin/com/tddworks/openai-client-darwin-kmmbridge/0.1.8/openai-client-darwin-kmmbridge-0.1.8.zip"
let remoteKotlinChecksum = "249e89b1bd110400be25518ce6406cbd1d6f9581127d1a00ba69344b008d6ba2"
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