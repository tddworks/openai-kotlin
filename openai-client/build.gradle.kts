kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.openaiClient.openaiClientCore)
            }
        }
    }
}