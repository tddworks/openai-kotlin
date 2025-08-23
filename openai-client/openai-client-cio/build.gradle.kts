plugins {
    alias(libs.plugins.kotlinx.serialization)
    //    alias(libs.plugins.touchlab.kmmbridge)
    //    id("module.publication")
    `maven-publish`
}

kotlin {
    jvm()
    sourceSets { jvmMain { dependencies { api(projects.openaiClient.openaiClientCore) } } }
}
