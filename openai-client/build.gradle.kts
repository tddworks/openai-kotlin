kotlin {
    jvm()
    sourceSets {
        commonMain {
            dependencies {
                api(projects.openaiClient.openaiClientCore)
            }
        }
    }
}