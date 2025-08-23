plugins { `maven-publish` }

kotlin {
    jvm()
    sourceSets { commonMain { dependencies { api(projects.geminiClient.geminiClientCore) } } }
}
